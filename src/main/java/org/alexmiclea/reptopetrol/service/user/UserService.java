package org.alexmiclea.reptopetrol.service.user;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.model.user.User;
import org.alexmiclea.reptopetrol.repository.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

//    public User getUser() {}

}
