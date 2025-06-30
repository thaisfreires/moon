package yoga.moon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import yoga.moon.dto.AuthRequest;
import yoga.moon.dto.AuthResponse;
import yoga.moon.dto.UserLoginForm;
import yoga.moon.dto.UserRegistrationRequest;
import yoga.moon.dto.UserRegistrationResponse;
import yoga.moon.model.User;
import yoga.moon.services.UserService;
import yoga.moon.util.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authManager;
    private JwtUtil jwtUtil;
    private UserService userService;

    public AuthController(AuthenticationManager authManager, JwtUtil jwtUtil, UserService userService) {
        this.authManager = authManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegistrationRequest request) {
        try {
            UserRegistrationResponse newUser = userService.registerUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    
    @PostMapping("/login")
    public ResponseEntity login(UserLoginForm form, Model model, @RequestBody AuthRequest request) {
    try {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));

        String email = auth.getName();
        User user = new User(email,"");
        String token = jwtUtil.createToken(user);
        AuthResponse loginReponse = new AuthResponse(email,token);
        return ResponseEntity.ok(loginReponse);    

    }catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
