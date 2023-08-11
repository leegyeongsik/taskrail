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

    @GetMapping("/{columnId}/cards")
    public String createCard(){
        return "card-create";
    }

    @GetMapping("/boards/{boardId}/cards/{cardId}")
    public String getCardOne(@PathVariable String boardId, @PathVariable String cardId, Model model){

        model.addAttribute("cardId", cardId);
        model.addAttribute("boardId", boardId);
        return "card-detail";
    }

    @GetMapping("/cards/{cardId}/update")
    public String updateCard(@PathVariable String cardId, Model model){

        model.addAttribute("cardId", cardId);
        return "card-update";
    }
}
