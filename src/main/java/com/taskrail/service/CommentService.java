package com.taskrail.service;

import com.taskrail.dto.CommentRequestDto;
import com.taskrail.entity.Card;
import com.taskrail.entity.Comment;
import com.taskrail.entity.User;
import com.taskrail.repository.CardRepository;
import com.taskrail.repository.CommentRepository;
import com.taskrail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;


    public void createComment(Long cardId, CommentRequestDto requestDto, User user) {
        Card card = cardRepository.findById(cardId).orElseThrow(
                ()->new NullPointerException("해당 카드가 존재하지 않습니다.")
        );
        Comment comment = new Comment(requestDto,card,user);
        commentRepository.save(comment);
    }
    @Transactional
    public void updateComment(Long commentId, CommentRequestDto requestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 카드가 존재하지 않습니다.")
        );
        if(!comment.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("작성자만 수정 가능합니다.");
        }
        comment.update(requestDto);

    }
    public void deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("해당 카드가 존재하지 않습니다.")
        );
        if(!comment.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("작성자만 삭제 가능합니다.");
        }
        commentRepository.delete(comment);
    }
}
