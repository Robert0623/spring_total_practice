package com.myportfolio.web.service;

import com.myportfolio.web.domain.BoardDto;

import java.util.List;
import java.util.Map;

public interface BoardService {
    int getCount() throws Exception;

    int write(BoardDto boardDto) throws Exception;

    //다시 보기 - 뷰카운트 증가해켜야 함
    BoardDto read(Integer bno) throws Exception;

    int modify(BoardDto boardDto) throws Exception;

    int remove(Integer bno, String writer) throws Exception;

    List<BoardDto> getList() throws Exception;

    int removeAll() throws Exception;

    int getViewCnt(Integer bno) throws Exception;

    List<BoardDto> getPage(Map map) throws Exception;
}
