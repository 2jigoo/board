package org.zerock.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.board.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    
    @Modifying // JPQL로 UPDATE, DELETE 수행 시 필요
    @Query("DELETE FROM Reply r WHERE r.board.bno = :bno")
    void deleteByBno(@Param("bno") Long bno);

}
