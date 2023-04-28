package com.example.demo.service;

import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
public class BoardService {
	
	// mapper에 일을 시킴 
	@Autowired
	private BoardMapper mapper;
	
	// 전체 리스트 조회
	public List<Board> listBoard() {
		List<Board> list = mapper.selectAll();
		return list;
	}

	// 1개 아이디 상세 정보 조회
	public Board getBoard(Integer id) {
		return mapper.selectById(id);
	}

	// 수정 (Update) 
	public boolean modify(Board board) {
		int cnt = mapper.update(board);
		return cnt == 1;
	}

	// 삭제 (remove)
	public boolean remove(Integer id) {
		int cnt = mapper.deleteById(id);
		return cnt == 1;
	}

	// 삽입  (add)
	public boolean addBoard(Board board) {
		int cnt = mapper.insert(board);
		return cnt == 1;
	}

	public List<Board> listBoard(Integer page) {
		Integer startIndex = (page - 1) * 15;
		// 1. 게시물 목록 보이기
		return mapper.selectAllPaging(startIndex);
		// 2. 페이지네이션이 필요한 정보
		
	}
}
