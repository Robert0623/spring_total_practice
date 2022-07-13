package com.myportfolio.web.dao;

import com.myportfolio.web.domain.BoardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/root-context.xml"})
public class BoardDaoImplTest {
    @Autowired
    BoardDao boardDao;

    @Test
    public void count() {
    }

    @Test
    public void insert() {
    }

    @Test
    public void select() throws Exception {
        assertTrue(boardDao!=null);
        System.out.println("boardDao = " + boardDao);
        BoardDto boardDto = boardDao.select(211);
        System.out.println("boardDto = " + boardDto);
        assertTrue(boardDto.getBno().equals(211));
    }

    @Test
    public void update() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void selectAll() {
    }

    @Test
    public void deleteAll() {
    }

    @Test
    public void increaseViewCnt() {
    }
}