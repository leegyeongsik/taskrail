package com.taskrail.taskrail.service;

import com.taskrail.taskrail.dto.CardResponseDto;
import com.taskrail.taskrail.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardService {
    private final CardRepository cardRepository;
    public List<CardResponseDto> getCards(Long columnId) {
        return cardRepository.findById(columnId).stream().map(CardResponseDto::new).toList();
    }
}
