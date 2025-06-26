package yoga.moon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.moon.model.User;
import java.moon.repository.UserRepository;
import java.util.NoSuchElementException;
import javax.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
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
    @PostMapping
    public String saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model){
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
}