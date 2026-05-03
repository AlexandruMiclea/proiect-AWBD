package org.alexmiclea.reptopetrol.dto.user;

import lombok.*;

@Data
@Getter
@Setter
@RequiredArgsConstructor
public class UserAuthenticationDto {
    private String email;
    private String password;
}
