package yoga.moon.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import yoga.moon.model.Subscription;
import yoga.moon.repository.SubscriptionRepository;

import java.util.NoSuchElementException;

@Controller
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @GetMapping
    public String getAllSubscriptions(Model model) {
        model.addAttribute("subscriptions", subscriptionRepository.findAll());
        return "subscriptions"; // Thymeleaf template: subscriptions.html
    }

    @GetMapping("/{id}")
    public String getSubscription(@PathVariable Long id, Model model) {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Subscription not found with ID: " + id));
        model.addAttribute("subscription", subscription);
        return "subscription-detail"; // subscription-detail.html
    }

    @GetMapping("/form")
    public String showSubscriptionForm(Model model) {
        model.addAttribute("subscription", new Subscription());
        return "subscription-form"; // subscription-form.html
    }

    @PostMapping
    public String saveSubscription(@Valid @ModelAttribute Subscription subscription,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "subscription-form";
        }
        subscriptionRepository.save(subscription);
        return "redirect:/subscriptions";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNotFound(Model model, NoSuchElementException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "error"; // error.html
    }
}
