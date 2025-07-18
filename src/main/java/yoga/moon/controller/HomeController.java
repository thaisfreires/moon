package yoga.moon.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public String home(Model model) {
        List<String>categories = List.of("Hatha", "Vinyasa", "Yin", "Kundalini", "Ashtanga", "Restorative");
        model.addAttribute("categories", categories);
        return "index";
    }
}
