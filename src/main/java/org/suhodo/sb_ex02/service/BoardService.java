package org.suhodo.sb_ex02.service;

import org.suhodo.sb_ex02.domain.Board;
import org.suhodo.sb_ex02.dto.BoardDTO;
import org.suhodo.sb_ex02.dto.BoardListAllDTO;
import org.suhodo.sb_ex02.dto.PageRequestDTO;
import org.suhodo.sb_ex02.dto.PageResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface BoardService {
    Long register(BoardDTO boardDTO);
    PageResponseDTO<BoardDTO> list(PageRequestDTO pageRequestDTO);
    PageResponseDTO<BoardListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

    default Board dtoToEntity(BoardDTO boardDTO) {
        Board board = Board.builder()
                .bno(boardDTO.getBno())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .writer(boardDTO.getWriter())
                .build();

        if(boardDTO.getFileNames() != null){
            boardDTO.getFileNames().forEach(fileName -> {
                String[] arr =fileName.split("_");
                board.addImage(arr[0], arr[1]);
            });
        }
        return board;
    }

    default BoardDTO entityToDto(Board board) {
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .writer(board.getWriter())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .build();

        List<String> fileNames = board.getImageSet().stream().sorted().map(
                boardImage -> {
                    return boardImage.getUuid() + "_" + boardImage.getFileName();
                }
        ).collect(Collectors.toList());

        boardDTO.setFileNames(fileNames);

        return boardDTO;

    }
}
