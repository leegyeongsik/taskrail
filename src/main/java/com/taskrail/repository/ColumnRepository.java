package com.taskrail.repository;

import com.taskrail.entity.Columns;
import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColumnRepository extends JpaRepository<Columns, Long> {

  Columns findByPosAndBoard_BoardId(int pos, Long id);

  @Query("SELECT i.pos FROM Columns AS i WHERE i.pos = (SELECT MAX(pos) FROM Columns WHERE board.boardId=?1)")
  Integer findItemWithLargestPos(Long board_id);
}
