package org.alexmiclea.reptopetrol.service.management;

import org.alexmiclea.reptopetrol.dto.management.creation.EmployeeCreationDto;
import org.alexmiclea.reptopetrol.dto.management.retrieval.EmployeeRetrievalDto;
import org.alexmiclea.reptopetrol.mapper.creation.EmployeeCreationMapper;
import org.alexmiclea.reptopetrol.mapper.retrieval.EmployeeRetrievalMapper;
import org.alexmiclea.reptopetrol.model.management.Employee;
import org.alexmiclea.reptopetrol.model.management.EmployeeRole;
import org.alexmiclea.reptopetrol.repository.management.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test-unit")
class EmployeeServiceTest {

    @Mock private EmployeeRepository employeeRepository;
    @Mock private EmployeeCreationMapper employeeCreationMapper;
    @Mock private EmployeeRetrievalMapper employeeRetrievalMapper;

    @InjectMocks private EmployeeService employeeService;

    @Test
    void getAll_returnsMappedList() {
        Employee employee = new Employee();
        EmployeeRetrievalDto dto = EmployeeRetrievalDto.builder().firstName("Ion").lastName("Popescu").build();
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeRetrievalMapper.toEmployeeDtos(List.of(employee))).thenReturn(List.of(dto));

        List<EmployeeRetrievalDto> result = employeeService.getAll();

        assertThat(result).containsExactly(dto);
    }

    @Test
    void getEmployeeById_whenExists_returnsDto() {
        UUID id = UUID.randomUUID();
        Employee employee = new Employee();
        EmployeeRetrievalDto dto = EmployeeRetrievalDto.builder().id(id).firstName("Ion").lastName("Popescu").build();
        when(employeeRepository.existsById(id)).thenReturn(true);
        when(employeeRepository.findById(id)).thenReturn(Optional.of(employee));
        when(employeeRetrievalMapper.toEmployeeDto(employee)).thenReturn(dto);

        Optional<EmployeeRetrievalDto> result = employeeService.getEmployeeById(id);

        assertThat(result).contains(dto);
    }

    @Test
    void getEmployeeById_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(employeeRepository.existsById(id)).thenReturn(false);

        Optional<EmployeeRetrievalDto> result = employeeService.getEmployeeById(id);

        assertThat(result).isEmpty();
    }

    @Test
    void addEmployee_mapsAndSaves() {
        Instant hire = Instant.now();
        EmployeeCreationDto dto = EmployeeCreationDto.builder()
                .firstName("Ion").lastName("Popescu").identificationNumber("123456")
                .wage(3000).role(EmployeeRole.CASHIER).dateOfHire(hire).build();
        Employee employee = new Employee();
        when(employeeCreationMapper.toEmployee(dto)).thenReturn(employee);

        employeeService.addEmployee(dto);

        verify(employeeRepository).save(employee);
    }

    @Test
    void updateEmployee_getsReferenceAndSaves() {
        UUID id = UUID.randomUUID();
        Instant hire = Instant.now();
        EmployeeCreationDto dto = EmployeeCreationDto.builder()
                .firstName("Maria").lastName("Ionescu").identificationNumber("654321")
                .wage(3500).role(EmployeeRole.ATTENDANT).dateOfHire(hire).build();
        Employee existing = new Employee();
        when(employeeRepository.getReferenceById(id)).thenReturn(existing);

        employeeService.updateEmployee(dto, id);

        assertThat(existing.getFirstName()).isEqualTo("Maria");
        assertThat(existing.getLastName()).isEqualTo("Ionescu");
        assertThat(existing.getIdentificationNumber()).isEqualTo("654321");
        assertThat(existing.getWage()).isEqualTo(3500);
        assertThat(existing.getRole()).isEqualTo(EmployeeRole.ATTENDANT);
        assertThat(existing.getDateOfHire()).isEqualTo(hire);
        verify(employeeRepository).save(existing);
    }

    @Test
    void deleteEmployee_whenExists_deletesAndReturnsId() {
        UUID id = UUID.randomUUID();
        when(employeeRepository.existsById(id)).thenReturn(true);

        Optional<UUID> result = employeeService.deleteEmployee(id);

        assertThat(result).contains(id);
        verify(employeeRepository).deleteById(id);
    }

    @Test
    void deleteEmployee_whenNotExists_returnsEmpty() {
        UUID id = UUID.randomUUID();
        when(employeeRepository.existsById(id)).thenReturn(false);

        Optional<UUID> result = employeeService.deleteEmployee(id);

        assertThat(result).isEmpty();
        verify(employeeRepository, never()).deleteById(any());
    }
}
