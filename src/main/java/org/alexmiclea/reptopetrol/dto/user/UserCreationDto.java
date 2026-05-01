package org.alexmiclea.reptopetrol.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class UserCreationDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confirmPassword;
}
