package org.alexmiclea.reptopetrol.controller.authentication;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.model.user.Role;
import org.alexmiclea.reptopetrol.model.user.User;
import org.alexmiclea.reptopetrol.service.authentication.AuthenticationService;
import org.alexmiclea.reptopetrol.service.monitoring.CRUDHistoryService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j

@Controller
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final CRUDHistoryService crudHistoryService;
    private final AuthenticationService authenticationService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String getUsers(Model model) {
        log.debug("GET /users called");

        // add call to history service
        crudHistoryService.add("GET /users", User.class.getName(), "");

        model.addAttribute("users", authenticationService.getAllUsers());
        model.addAttribute("roles", List.of(Role.values()));

        return "users/users";
    }

    @PutMapping("/user/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String updateUser(@PathVariable("email") String email, @RequestParam Role role, Model model) {
        log.debug("PUT /user called with email {}", email);

        // add call to history service
        crudHistoryService.add("PUT /user", User.class.getName(), email);

        authenticationService.updateUserRole(email, role);

        return "redirect:/api/users/users";
    }
}
