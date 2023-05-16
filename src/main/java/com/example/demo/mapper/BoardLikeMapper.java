package com.example.demo.mapper;

import org.apache.ibatis.annotations.*;

import com.example.demo.domain.*;

@Mapper
public interface BoardLikeMapper {

	@Insert("""
			INSERT INTO BoardLike
			VALUES(#{boardId}, #{memberId})
			
			""")
	int insert(Like like);

	
	@Delete("""
			DELETE FROM BoardLike
			WHERE boardId = #{boardId}
				AND memberId = #{memberId}
			""")
	Integer delete(Like like);
	
	
}
