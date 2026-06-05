package org.alexmiclea.reptopetrol.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.alexmiclea.reptopetrol.model.authentication.Token;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "app_user")
@Builder
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String firstname;
    private String lastname;
    private String email;

    @ToString.Exclude
    private String password;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    private List<Token> tokens;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
