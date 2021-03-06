package org.zerock.board.repository.search;

import java.util.List;

import com.querydsl.jpa.JPQLQuery;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.QBoard;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }
    
    @Override
    public Board search1() {
        log.info("search1...");

        QBoard board = QBoard.board;

        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.select(board).where(board.bno.eq(1L));

        log.info("----------------------------------------------");
        log.info(jpqlQuery);
        log.info("----------------------------------------------");

        List<Board> result = jpqlQuery.fetch();

        return null;
    }
}
