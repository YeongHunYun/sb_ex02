package org.suhodo.sb_ex02.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.suhodo.sb_ex02.domain.Board;
import org.suhodo.sb_ex02.dto.BoardListAllDTO;
import org.suhodo.sb_ex02.dto.BoardListReplyCountDTO;

public interface BoardSearch {
    Page<Board> search1(Pageable pageable);

    Page<Board> searchAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithReplyCount(String[] types, String keyword, Pageable pageable);

//    Page<BoardListReplyCountDTO> searchWithAll(String[] types, String keyword, Pageable pageable);

    Page<BoardListAllDTO> searchWithAll(String[] types, String keyword, Pageable pageable);

}