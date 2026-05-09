package org.alexmiclea.reptopetrol.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UserAuthenticationDto {

    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 2, max = 128)
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, max = 128, message = "Password should be longer than 8 characters")
    private String password;
}
