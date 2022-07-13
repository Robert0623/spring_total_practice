package com.myportfolio.web.dao;

import com.myportfolio.web.domain.BoardDto;

import java.util.List;
import java.util.Map;

public interface BoardDao {
    int count() throws Exception;

    int insert(BoardDto boardDto) throws Exception;

    BoardDto select(Integer bno) throws Exception;

    int update(BoardDto boardDto) throws Exception;

    int delete(Integer bno, String writer) throws Exception;

    List<BoardDto> selectAll() throws Exception;

    int deleteAll() throws Exception;

    int increaseViewCnt(Integer bno) throws Exception;

    List<BoardDto> selectPage(Map map) throws Exception;
}
