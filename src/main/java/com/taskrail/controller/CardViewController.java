package com.taskrail.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/view")
public class CardViewController {

    @GetMapping("/cards/{cardId}")
    public String getCardOne(@PathVariable String cardId, Model model){

        model.addAttribute("cardId", cardId);
        return "card-detail";
    }
}
