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
			SELECT 
				b.id,
				b.title,
				b.body,
				b.inserted,
				b.writer,
				f.fileName
			FROM Board b LEFT JOIN FileName f ON b.id = f.boardId
			WHERE b.id = #{id}
			""")
	@ResultMap("boardResultMap")
	Board selectById(Integer id);

	@Update("""
			UPDATE Board
			SET
				title = #{title},
				body = #{body}
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

	// 동적 SQL bind 문 사용하여 search 기능 구현
	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT
				b.id,
				b.title,
				b.writer,
				b.hit,
				b.inserted,
				COUNT(f.id) fileCount
			FROM Board b LEFT JOIN FileName f ON b.id = f.boardId
			<where>
				<if test="(type eq 'all') or (type eq 'title')">
					title LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'body')">
				OR body LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'writer')">
				OR writer LIKE #{pattern}
				</if>
			</where>
			GROUP BY b.id
			ORDER BY b.id DESC
			LIMIT #{startIndex}, #{rowPerPage}
			</script>
			""")
	// 복잡하지 않은 조인은 ResultMap 사용 안해도됨
//	@ResultMap("BoardFileResult")
	List<Board> selectAllPaging(Integer startIndex, Integer rowPerPage, String search, String type);

	@Select("""
			<script>
			<bind name="pattern" value="'%' + search + '%'" />
			SELECT COUNT(*) FROM Board
			<where>
				<if test="(type eq 'all') or (type eq 'title')">
					title LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'body')">
				OR body LIKE #{pattern}
				</if>
				<if test="(type eq 'all') or (type eq 'writer')">
				OR writer LIKE #{pattern}
				</if>
			</where>
			</script>
			""")
	Integer countAll(String search, String type);

	@Insert("""
			INSERT INTO FileName (boardId, fileName)
			VALUES (#{boardId}, #{fileName}) 
			""")
//	@Options(useGeneratedKeys = true, keyProperty = "id")
	Integer insertFileName(Integer boardId, String fileName);

	
	@Select("""
			SELECT fileName FROM FileName 
			WHERE boardId = ${boardId}
			
			""")
	List<String> selectFileNamesByBoardId(Integer boardId);

	
	@Delete("""
			DELETE FROM FileName 
			WHERE boardId = ${boardId};
			""")
	void deleteFileNameByBoardId(Integer boardId);

	
	@Delete("""
			DELETE FROM FileName
			WHERE 	boardId = #{boardId} 
				AND fileName = #{fileName}
			""")

	void deleteFileNameByBoardIdAndFileName(Integer boardId, String fileName);

	@Select("""
			SELECT id
			FROM Board
			WHERE writer = #{writer}
			""")
	List<Integer> selectBoardIdByWriter(String writer);

	@Update("""
			UPDATE Board
			SET hit=hit+1
			WHERE id=#{id};
			""")
	void hitPlus(Integer id);

}












