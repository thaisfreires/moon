package yoga.moon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import yoga.moon.model.YogaClass;
import yoga.moon.repository.YogaClassRepository;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/classes")
public class YogaClassController {

    @Autowired
    private YogaClassRepository yogaClassRepository;

    @GetMapping
    public String getClasses(Model model) {
        model.addAttribute("classes", yogaClassRepository.findAll());
        return "classes"; // template Thymeleaf
    }

    @GetMapping("/{id}")
    public String classDetail(@PathVariable Long id, Model model) {
        YogaClass yogaClass = yogaClassRepository.findById(id).orElse(null);
        model.addAttribute("yogaClass", yogaClass);
        return "class-detail";
    }
    @PostMapping("/classes")
    public String saveClass(@Valid @ModelAttribute YogaClass yogaClass, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "class-form";
        }
        yogaClassRepository.save(yogaClass);
        return "redirect:/classes";
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
