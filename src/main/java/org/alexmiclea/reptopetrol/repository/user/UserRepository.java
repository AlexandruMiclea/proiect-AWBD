package org.alexmiclea.reptopetrol.repository.user;

import org.alexmiclea.reptopetrol.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
