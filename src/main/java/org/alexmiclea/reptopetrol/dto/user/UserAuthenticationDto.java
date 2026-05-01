package org.alexmiclea.reptopetrol.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class UserAuthenticationDto {
    private String email;
    private String password;
}
