package org.zerock.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.board.entity.Board;
import org.zerock.board.entity.Member;

@SpringBootTest
public class BoardRepositoryTest {
    
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void test_A_게시판_글_작성() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder().email("user" + i + "@aaa.com").build();

            Board board = Board.builder()
                            .title("Title..." + i)
                            .content("Content..." + i)
                            .writer(member)
                            .build();

            boardRepository.save(board);
        });
    }

    @Test
    @Transactional
    public void test_B_게시글_조회() {
        Optional<Board> result = boardRepository.findById(100L);
        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());

        // from board b left outer join member m on b.writer_email = m.email
        // 자동으로 조인 되어 조회 됨
    }

    @Test
    public void test_C_게시글_작성자_포함_조회() {
        Object result = boardRepository.getBoardWithWriter(100L);
        Object[] arr = (Object[]) result;

        System.out.println("----------------------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void test_D_게시글_댓글_포함_조회() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void test_E_게시글_목록_작성자_댓글갯수_포함_조회() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(row -> {
            System.out.println(Arrays.toString(row));
        });
    }

    @Test
    public void test_F_게시글_작성자_댓글갯수_포함_글번호로_조회() {
        Object result = boardRepository.getBoardByBno(100L);
        Object[] arr = (Object[]) result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1() {
        boardRepository.search1();
    }
 }
