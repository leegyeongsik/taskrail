package com.taskrail.repository;


import com.taskrail.entity.BoardRole;
import com.taskrail.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRoleRepository extends JpaRepository<BoardRole, Long> {
    Optional<BoardRole> findByUser_idAndBoard_boardId(Long id, Long boardId);
}
