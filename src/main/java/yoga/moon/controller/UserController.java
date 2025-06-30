package yoga.moon.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import yoga.moon.model.User;
import yoga.moon.repository.UserRepository;
import yoga.moon.services.UserService;

import yoga.moon.util.JwtUtil;

import java.util.NoSuchElementException;
import org.springframework.validation.BindingResult;


@Controller
@RequestMapping("/auth")
public class UserController {

    private JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;
    private UserService service;
    private UserRepository userRepository;

    public UserController(JwtUtil jwtUtil,AuthenticationManager authenticationManager,UserService service,UserRepository userRepository){
        this.authenticationManager=authenticationManager;
        this.jwtUtil=jwtUtil;
        this.service=service;
        this.userRepository=userRepository;
    }    
    @GetMapping
    public String getUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "users"; // template Thymeleaf
    }
    @GetMapping("/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElse(null);
        model.addAttribute("user", user);
        return "user-detail";
    }
    @PostMapping("/addNewUser")
    public String saveUser(@RequestBody User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "user-form";
        }
        userRepository.save(user);
        return "redirect:/users";
    }
    @ControllerAdvice
    public class GlobalErrorHandler {
        @ExceptionHandler(NoSuchElementException.class)
        public String classNotFound(Model model, NoSuchElementException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    // @PostMapping("/generateToken")
    // public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
    //     Authentication authentication = authenticationManager.authenticate(
    //         new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
    //     );
    //     if (authentication.isAuthenticated()) {
    //         return jwtUtil.createToken();
    //     } else {
    //         throw new UsernameNotFoundException("Invalid user request!");
    //     }
    // }
}