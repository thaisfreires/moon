package yoga.moon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import yoga.moon.dto.AuthRequest;
import yoga.moon.dto.AuthResponse;
import yoga.moon.dto.UserLoginForm;
import yoga.moon.util.JwtUtil;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody AuthRequest request) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails.getUsername());
        return ResponseEntity.ok(new AuthResponse(token));
    }
    @PostMapping(value = "/auth", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String loginForm(UserLoginForm form, Model model) {
    try {
        Authentication auth = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword()));

        // Issue JWT, store it in session, or redirect somewhere
        String token = jwtUtil.generateToken(form.getUsername());
        model.addAttribute("token", token);
        return "redirect:/"; 

    } catch (AuthenticationException e) {
        return "redirect:/login?error";
    }
}

}
