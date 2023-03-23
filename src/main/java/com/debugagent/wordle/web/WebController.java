package com.debugagent.wordle.web;

import com.debugagent.wordle.service.WordleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class WebController {
    private final WordleService wordleService;

    private static List<String> attempts = new ArrayList<>();

    @GetMapping("/guessMVC")
    public String guess(Model model) {
        return "WordleGuess";
    }

    @PostMapping("/guessMVC")
    public String submitGuess(String guess, Model model) {
        String error = wordleService.validate(guess);
        if(error != null) {
            model.addAttribute("errorMessage", error);
        } else {
            model.addAttribute("errorMessage", "");
            attempts.add(guess);
        }
        List<WebResult[]> results = attempts.stream()
                .map(str -> WebResult.create(wordleService.calculateResults(str), str))
                .collect(Collectors.toList());
        model.addAttribute("entries", results);

        return "WordleGuess";
    }
}
