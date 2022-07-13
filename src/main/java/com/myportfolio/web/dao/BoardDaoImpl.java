package com.myportfolio.web.dao;

import com.myportfolio.web.domain.BoardDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BoardDaoImpl implements BoardDao {
    @Autowired
    SqlSession session;

    String namespace = "com.myportfolio.web.dao.BoardMapper.";

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace + "count");
    }
    @Override
    public int insert(BoardDto boardDto) throws Exception {
        return session.insert(namespace + "insert", boardDto);
    }
    @Override
    public BoardDto select(Integer bno) throws Exception {
        return session.selectOne(namespace + "select", bno);
    }
    @Override
    public int update(BoardDto boardDto) throws Exception {
        return session.update(namespace + "update", boardDto);
    }
    @Override
    public int delete(Integer bno, String writer) throws Exception {
        Map map = new HashMap();
        map.put("bno", bno);
        map.put("writer", writer);
        return session.delete(namespace + "delete", map);
    }
    @Override
    public List<BoardDto> selectAll() throws Exception {
        return session.selectList(namespace + "selectAll");
    }
    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace + "deleteAll");
    }
    @Override
    public int increaseViewCnt(Integer bno) throws Exception {
        return session.update(namespace + "increaseViewCnt", bno);
    }

    @Override
    public List<BoardDto> selectPage(Map map) throws Exception {
        return session.selectList(namespace + "selectPage", map);
    }

    //searchResultCnt, searchSelectPage 작성 해야 함.
}
