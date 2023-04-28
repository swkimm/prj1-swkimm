<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Insert title here</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" integrity="sha512-iecdLmaskl7CVkqkXNQ/ZH/XLlvWZOJyj7Yy7tcenmpD1ypASozpmT/E0iPtmFIB46ZmdtAc9eNBvH0H/ZpiBw==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>

	<my:navBar current="list" />

	<my:alert />

	<div class="container-lg">
		<h1>게시물 목록</h1>
		<!-- table.table>thead>tr>th*4^^tbody -->
		<table class="table">
			<thead>
				<tr>
					<th>#</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일시</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${boardList }" var="board">
					<tr>
						<td>${board.id }</td>
						<td><a href="/id/${board.id }"> ${board.title } </a></td>
						<td>${board.writer }</td>
						<td>${board.inserted }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

	<!-- Pagination -->
	<div class="container-lg">
		<div class="row">
			<nav aria-label="Page navigation example ">
				<ul class="pagination justify-content-center">
					
					<!-- 처음 페이지로 이동 버튼 -->
					<!-- 현재 페이지가 1일 경우 처음, 이전 버튼 없애기 -->
					<c:if test="${pageInfo.currentPage > 1 }">
						<c:url value="/list" var="pageLink">
								<c:param name="page" value="1" />
						</c:url> 
						<li class="page-item">
							<a class="page-link" href="${pageLink }"><i class="fa-solid fa-angles-left"></i></a>
						</li>
						
						<!-- 이전 버튼 -->
						<c:url value="/list" var="pageLink">
							<c:param name="page" value="${pageInfo.currentPage -1}"></c:param>
						</c:url>
						<li class="page-item"><a class="page-link" href="${pageLink }"><i class="fa-solid fa-angle-left"></i></i></a></li>
					</c:if>
					
					<!-- 페이징 처리 -->
					<c:forEach begin="${pageInfo.leftPageNum }" end="${pageInfo.rightPageNum }" var="pageNum">
						<c:url value="/list" var="pageLink">
							<c:param name="page" value="${pageNum }"></c:param>
						</c:url>
						<li class="page-item"><a class="page-link ${pageNum eq pageInfo.currentPage ? 'active' : '' }" href="${pageLink }">${pageNum }</a></li>
					</c:forEach>
					
					<!-- 현재 페이지가 마지막 페이지일 경우 다음, 마지막 버튼 없애기 -->
					<c:if test="${pageInfo.currentPage < pageInfo.lastPageNumber}">
						<!-- 다음 버튼 -->
						<c:url value="/list" var="pageLink">
							<c:param name="page" value="${pageInfo.currentPage +1}"></c:param>
						</c:url>
						<li class="page-item"><a class="page-link" href="${pageLink }"><i class="fa-solid fa-angle-right"></i></a></li>
						
						<!-- 마지막 페이지로 이동 버튼 -->
						<c:url value="/list" var="pageLink">
							<c:param name="page" value="${pageInfo.lastPageNumber }" />
						</c:url>
						<li class="page-item">
							<a class="page-link" href="${pageLink }"><i class="fa-solid fa-angles-right"></i></a>
						</li>
					</c:if>
				</ul>
			</nav>
		</div>
	</div>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.4/jquery.min.js" integrity="sha512-pumBsjNRGGqkPzKHndZMaAG+bir374sORyzM3uulLV14lN5LyykqNk8eEeUlUkB3U0M4FApyaHraT65ihJhDpQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
	
</body>
</html>













