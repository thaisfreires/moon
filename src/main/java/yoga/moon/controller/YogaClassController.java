package yoga.moon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import yoga.moon.model.YogaClass;
import yoga.moon.repository.CategoryRepository;
import yoga.moon.repository.YogaClassRepository;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/classes")
public class YogaClassController {

    private final CategoryRepository categoryRepository;

    @Autowired
    private YogaClassRepository yogaClassRepository;

    YogaClassController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public String getClasses(Model model) {
        model.addAttribute("classes", yogaClassRepository.findAll());
        return "classes"; // template Thymeleaf
    }
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("yogaClass", new YogaClass());
        model.addAttribute("categories", categoryRepository.findAll());
        return "class-form";
    }

    @GetMapping("/{id}")
    public String classDetail(@PathVariable Long id, Model model) {
        YogaClass yogaClass = yogaClassRepository.findById(id).orElse(null);
        model.addAttribute("yogaClass", yogaClass);
        return "class-detail";
    }
    @PostMapping("/save")
    public String saveClass(@Valid @ModelAttribute YogaClass yogaClass, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("categories", categoryRepository.findAll());
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
    @GetMapping("/delete/{id}")
    public String deleteClass(@PathVariable Long id) {
        yogaClassRepository.deleteById(id);
        return "redirect:/classes";
    }
}
