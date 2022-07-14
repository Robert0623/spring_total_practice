package com.myportfolio.web.service;

import com.myportfolio.web.dao.BoardDao;
import com.myportfolio.web.domain.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

//Tx을 쓰지 않아서 try-catch로 처리할게 없음.
//Controller로 전부 예외를 떠넘김.
@Service
public class BoardServiceImpl implements BoardService {
    @Autowired
    BoardDao boardDao;

    @Override
    public int getCount() throws Exception {
        return boardDao.count();
    }
    @Override
    public int write(BoardDto boardDto) throws Exception {
        return boardDao.insert(boardDto);
//        throw new Exception("test");
    }
    //다시 보기 - 뷰카운트 증가해켜야 함
    @Override
    public BoardDto read(Integer bno) throws Exception {
        boardDao.increaseViewCnt(bno);
        return boardDao.select(bno);
    }
    @Override
    public int modify(BoardDto boardDto) throws Exception {
        return boardDao.update(boardDto);
    }
    @Override
    public int remove(Integer bno, String writer) throws Exception {
        return boardDao.delete(bno, writer);
    }
    @Override
    public List<BoardDto> getList() throws Exception {
        return boardDao.selectAll();
    }
    @Override
    public int removeAll() throws Exception {
        return boardDao.deleteAll();
    }
    @Override
    public int getViewCnt(Integer bno) throws Exception {
        return boardDao.increaseViewCnt(bno);
    }
    @Override
    public List<BoardDto> getPage(Map map) throws Exception {
        return boardDao.selectPage(map);
    }
    //getSearchResultCnt, getSearchResultPage 작성해야 함

}
