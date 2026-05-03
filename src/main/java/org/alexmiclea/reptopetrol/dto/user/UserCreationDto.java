package org.alexmiclea.reptopetrol.dto.user;

import lombok.*;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UserCreationDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
