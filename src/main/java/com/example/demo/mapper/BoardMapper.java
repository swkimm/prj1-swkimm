package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardMapper {

	@Select("""
			SELECT 
				id,
				title,
				writer,
				inserted
			FROM Board
			ORDER BY id DESC
			""")
	List<Board> selectAll();

	@Select("""
			SELECT *
			FROM Board
			WHERE id = #{id}
			""")
	Board selectById(Integer id);

	@Update("""
			UPDATE Board
			SET 
				title = #{title},
				body = #{body},
				writer = #{writer}
			WHERE
				id = #{id}
			""")
	int update(Board board);

	@Delete("""
			DELETE FROM Board
			WHERE id = #{id}
			""")
	int deleteById(Integer id);
	
	@Insert("""
			INSERT INTO Board (title, body, writer)
			VALUES (#{title}, #{body}, #{writer})
			""")
	// 자동 생성된 키를 사용하여 데이터베이스에 새로운 레코드를 삽입할 때 사용
	// keyProperty 속성은 자동 생성된 키를 매핑할 java 객체의 프로퍼티를 지정
	@Options(useGeneratedKeys = true, keyProperty = "id")
	int insert(Board board);

	
	@Select("""
			SELECT 
				id,
				title,
				writer,
				inserted
			FROM Board
			ORDER BY id DESC
			LIMIT #{startIndex}, 15
			""")
	List<Board> selectAllPaging(Integer startIndex);
	
}
