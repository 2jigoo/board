package org.zerock.board.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.dto.BoardDTO;
import org.zerock.board.dto.PageRequestDTO;
import org.zerock.board.dto.PageResultDTO;
import org.zerock.board.repository.BoardRepository;

@SpringBootTest
public class BoardServiceTest {
    
    @Autowired
    private BoardService boardService;

    @Test
    public void test_A_게시물_작성() {
        BoardDTO dto = BoardDTO.builder()
                            .title("Test Title")
                            .content("Test Content")
                            .writerEmail("user55@aaa.com")
                            .build();
        Long bno = boardService.register(dto);
    }

    @Test
    public void test_B_게시판_목록_조회() {
        PageRequestDTO pageRequestDTO = new PageRequestDTO();

        PageResultDTO<BoardDTO, Object[]> result = boardService.getList(pageRequestDTO);

        for(BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Test
    public void test_C_게시물_조회() {
        Long bno = 1L;
        BoardDTO boardDTO = boardService.get(bno);
        System.out.println(boardDTO);
    }

    @Test
    @Transactional
    public void test_D_게시물_댓글과_함께_삭제() {
        Long bno = 1L;
        boardService.removeWithReplies(bno);
    }


    @Test
    public void test_E_게시물_수정() {
        BoardDTO boardDTO = BoardDTO.builder()
                    .bno(2L)
                    .title("제목 수정")
                    .content("내용 수정")
                    .build();
        boardService.modify(boardDTO);
    }
}
