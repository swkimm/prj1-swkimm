package com.example.demo.service;

import java.io.*;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.multipart.*;

import com.example.demo.domain.*;
import com.example.demo.mapper.*;

@Service
@Transactional(rollbackFor = Exception.class)
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
public boolean modify(Board board, MultipartFile[] addFiles, List<String> removeFileNames) throws Exception {
		
		// FileName 테이블 삭제
		if (removeFileNames != null && !removeFileNames.isEmpty()) {
			for (String fileName : removeFileNames) {
				// 하드디스크에서 삭제
				String path = "/Users//ksw/Desktop/study/upload//" + board.getId() + "//" + fileName;
				File file = new File(path);
				if (file.exists()) {
					file.delete();
				}
				// 테이블에서 삭제
				mapper.deleteFileNameByBoardIdAndFileName(board.getId(), fileName);
			}
		}
		
		// 새 파일 추가 
		for (MultipartFile newFile : addFiles) {
			if(newFile.getSize() > 0) {
				// 테이블에 파일명 추가
				mapper.insertFileName(board.getId(), newFile.getOriginalFilename());
				String fileName = newFile.getOriginalFilename();
				String folder = "/Users//ksw/Desktop/study/upload//" + board.getId();
				String path =  folder + "//" + fileName;
				
				// 디렉토리 없으면 만들기
				File dir = new File(folder);
				if(!dir.exists()) {
					dir.mkdirs();
				}
				
				// 파일을 하드디스크에 저장
				File file = new File(path);
				newFile.transferTo(file);
			}
		}
		
		// 게시물(Board) 테이블 수정
		int cnt = mapper.update(board);
		
		// 파일 업로드
		
		
		return cnt == 1;
	}

	// 삭제 (remove)
	public boolean remove(Integer id) {
		// 파일명 조회
		List<String> fileNames = mapper.selectFileNamesByBoardId(id);
		System.out.println(fileNames);
		
		// FileName 테이블의 데이터 지우기
		mapper.deleteFileNameByBoardId(id);
		
		// 하드디스크의 파일 지우기
		for(String fileName : fileNames) {
			String path = "/Users/ksw/Desktop/study/upload//" + id + "//" + fileName;
			File file = new File(fileName);
			if(file.exists()) {
				file.delete();
			}
		}
		
		// 게시물 테이블의 데이터 지우기
		int cnt = mapper.deleteById(id);
		return cnt == 1;
	}

	// 삽입  (add), 파일 로드
//	@Transactional(rollbackFor = Exception.class)
	public boolean addBoard(Board board, MultipartFile[] files) throws Exception {
		
		// 게시물 insert 
		int cnt = mapper.insert(board);
		
		for (MultipartFile file : files) {
			if (file.getSize() > 0) {
				System.out.println(file.getOriginalFilename());
				System.out.println(file.getSize());
				// 파일 저장 (파일 시스템에)
				String folder = "/Users/ksw/Desktop/study/upload//" + board.getId();
				File targetFolder = new File(folder);
				if(!targetFolder.exists()) {
					targetFolder.mkdir();
				}				
				
				String path = folder + "//" + file.getOriginalFilename();
				File target = new File(path);
				file.transferTo(target);
					
				// db에 관련 정보 저장(insert)
				mapper.insertFileName(board.getId(), file.getOriginalFilename());
			}
		}
		
		return cnt == 1;
		
		
	}

	public Map<String, Object> listBoard(Integer page, String search, String type) {
		// 페이지당 행의 수
		Integer rowPerPage = 10;
		
		// 쿼리 LIMIT 절에 사용할 시작 인덱스
		Integer startIndex = (page - 1) * rowPerPage;
		
		// 페이지네이션이 필요한 정보
		// 전체 레코드 수
		Integer numOfRecords = mapper.countAll(search, type);
		// 마지막 페이지 번호
		Integer lastPageNum = (numOfRecords - 1) / rowPerPage + 1;
		
		// 페이지네이션 왼쪽 번호 
		Integer leftPageNum = page - 5;
		// 1보다 작을 수 없음
		leftPageNum = Math.max(leftPageNum, 1);
		
		// 페이지네이션 오른쪽 번호
		Integer rightPageNum = leftPageNum + 9;
		// 마지막 페이지 보다 클 수 없음
		rightPageNum = Math.min(rightPageNum, lastPageNum);

		// 파일명 호출
		
		
		Map<String, Object> pageInfo = new HashMap<>();
		pageInfo.put("rightPageNum", rightPageNum);
		pageInfo.put("leftPageNum", leftPageNum);
		pageInfo.put("lastPageNum", lastPageNum);
		pageInfo.put("currentPageNum", page);
		pageInfo.put("numOfRecords", numOfRecords);
		
		// 게시물 목록 보이기
		// 검색 기능 추가
		List<Board> list = mapper.selectAllPaging(startIndex, rowPerPage, search, type);
		return Map.of("pageInfo", pageInfo, "boardList", list);
	}
}
