<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>스프링부터 게시판</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

	<my:navBar current="list" />

	<my:alert></my:alert>

	<div class="container-lg">
		<h1>게시물 목록</h1>
		<!-- table.table>thead>tr>th*4^^tbody -->
		<p>총 게시물 : ${pageInfo.numOfRecords}</p>
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>제목</th>
					<th><i class="fa-solid fa-thumbs-up"></i></th>
					<th>작성자</th>
					<th>조회수</th>
					<th>작성일시</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${boardList }" var="board">
					<tr>
						<td>${board.id }</td>
						<td><a href="/id/${board.id }"> ${board.title } </a> <c:if test="${board.fileCount > 0 }">
								<span class="badge text-bg-info">
								 <i class="fa-solid fa-images"></i> 
								 	${board.fileCount }
								</span>
							</c:if></td>
						<td>${board.likeCount }</td>
						<td>${board.writer }</td>
						<td>${board.hit }</td>
						<td>${board.inserted }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<div class="container-lg">
		<div class="row">
			<nav aria-label="Page navigation example">
				<ul class="pagination justify-content-center">

					<!-- 처음 페이지로 이동 -->
					<c:if test="${pageInfo.currentPageNum gt 1 }">
						<my:pageItem pageNum="1">
							<i class="fa-solid fa-angles-left"></i>
						</my:pageItem>
					</c:if>

					<!-- 이전 버튼 -->
					<c:if test="${pageInfo.currentPageNum gt 1 }">
						<my:pageItem pageNum="${pageInfo.currentPageNum - 1 }">
							<i class="fa-solid fa-angle-left"></i>
						</my:pageItem>
					</c:if>

					<c:forEach begin="${pageInfo.leftPageNum }" end="${pageInfo.rightPageNum }" var="pageNum">
						<my:pageItem pageNum="${pageNum }">
							${pageNum }
						</my:pageItem>
					</c:forEach>

					<!-- 다음 버튼 -->
					<c:if test="${pageInfo.currentPageNum lt pageInfo.lastPageNum }">
						<%-- 페이지 번호 : ${pageInfo.currentPageNum + 1 } --%>
						<my:pageItem pageNum="${pageInfo.currentPageNum + 1 }">
							<i class="fa-solid fa-angle-right"></i>
						</my:pageItem>
					</c:if>
					<!-- 마지막 페이지로 이동 -->
					<!-- 맨 앞으로 -->
					<c:if test="${pageInfo.currentPageNum lt pageInfo.lastPageNum }">
						<my:pageItem pageNum="${pageInfo.lastPageNum }">
							<i class="fa-solid fa-angles-right"></i>
						</my:pageItem>
					</c:if>

				</ul>
			</nav>
		</div>
	</div>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
</body>
</html>













