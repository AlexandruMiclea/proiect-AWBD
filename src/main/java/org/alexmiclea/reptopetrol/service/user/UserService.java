package org.alexmiclea.reptopetrol.service.user;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.user.TokenResponseDto;
import org.alexmiclea.reptopetrol.dto.user.UserCreationDto;
import org.alexmiclea.reptopetrol.model.user.User;
import org.alexmiclea.reptopetrol.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    public User getUser() {}

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(UUID uuid) {
        if (userRepository.existsById(uuid)) {
            return Optional.of(userRepository.findById(uuid).orElseThrow());
        } else {
            return Optional.empty();
        }
    }

    // TODO refactor
    public Optional<TokenResponseDto> addUser(UserCreationDto userCreationDto) {

        User user = User.builder()
            .id(UUID.randomUUID())
            .firstname(userCreationDto.getFirstName())
            .lastname(userCreationDto.getLastName())
//            .username(userCreationDto)
            .email(userCreationDto.getEmail())
            .password(userCreationDto.getPassword())
            .build();

        userRepository.save(user);

        // TODO return the optional of token
        return Optional.empty();
    }

    public Optional<UUID> deleteUser(UUID uuid) {
        if (userRepository.existsById(uuid)) {
            userRepository.deleteById(uuid);
            return Optional.of(uuid);
        } else {
            return Optional.empty();
        }
    }

}
