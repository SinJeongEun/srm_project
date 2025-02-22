<%-- 작성자 : 신정은
	작성날짜 : 2023-02-20 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/WEB-INF/views/fragments/header.jsp"%>
<link rel="stylesheet" type="text/css"
	href="/resources/css/pagination.css">
<link rel="stylesheet" type="text/css"
	href="/resources/js/pagination.js">
<link rel="stylesheet" type="text/css"
	href="/resources/css/srButton.css">
<script type="text/javascript">
	function toUpdate() {
		console.log("눌림 ~~");
		$("#update").css("display", "block");
		$("#postDetail").css("display","none");
	}
</script>
</head>
<body>
	<div id="pcoded" class="pcoded">
		<div class="pcoded-overlay-box"></div>
		<div class="pcoded-container navbar-wrapper">
			<%@include file="/WEB-INF/views/fragments/top.jsp"%>
			<div class="pcoded-main-container">
				<div class="pcoded-wrapper">
					<%@include file="/WEB-INF/views/fragments/sidebar.jsp"%>
					<div class="pcoded-content">
						<%@include file="/WEB-INF/views/fragments/pageHeader.jsp"%>
						<div class="pcoded-inner-content">
							<div class="main-body">
								<div id="pageWrapper" class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body button-page">
										<div class="row">
											<!-- bootstrap modal start -->
											<div class="col-sm-5">
												<!-- Notification card start -->
												<div class="card">
													<div class="card-header">
														<h5 class="card-header-text">문의사항</h5>
													</div>
													<table class="table table-hover">
														<thead>
															<tr>
																<th class="col-1">글 번호</th>
																<th class="col-6">제목</th>
																<th class="col-3">작성자</th>
																<th class="col-2">답변상태</th>
															</tr>
														</thead>
														<tbody>
															<tr>
																<th scope="row" style="text-align: center;">1</th>
																<td>사이드바 색상 문의드립니다.</td>
																<td>홍길동</td>
																<td>미답변</td>
															</tr>
															<tr>
																<th scope="row" style="text-align: center;">2</th>
																<td>오티아이 워크샵 공지</td>
																<td>이순신</td>
																<td>답변완료</td>
															</tr>
															<tr>
																<th scope="row" style="text-align: center;">1</th>
																<td>오티아이 회식 공지</td>
																<td>강감찬</td>
																<td>미답변</td>
															</tr>
														</tbody>
													</table>
													<!-- 페이징 처리 -->
													<%@ include file="/WEB-INF/views/fragments/pagination.jsp"%>
												</div>
												<!-- Notification card end -->
											</div>
											<div class="col-sm-7" id="postDetail">
												<!-- 상세 보기 ------------------------------------------------------ -->
												<div class="card">
													<div class="card-header">
														<h5>문의사항</h5>
													</div>
													<div class="card-block">
														<form enctype="multipart/form-data">
															<div class="form-group row">
																<div class="col-sm-2 font-weight-bold">글번호</div>
																<div class="col-sm-1">37</div>
																<div class="col-sm-2 font-weight-bold text-right">작성자</div>
																<div class="col-sm-3">신정은(관리자)</div>
																<div class="col-sm-2 font-weight-bold text-right">답변상태</div>
																<div class="col-sm-2">답변완료</div>
															</div>
															<div class="form-group row">
																<div class="col-sm-2 font-weight-bold">제목</div>
																<div class="col-sm-6">제목a</div>
																<div class="col-sm-2 font-weight-bold text-right">작성일자</div>
																<div class="col-sm-2">2023-02-14</div>
															</div>
															<div class="form-group row">
																<p class="col-sm-2 font-weight-bold">내용</p>
																<div class="col-sm-10">
																	<textarea rows="20" cols="5" class="form-control"
																		style="border: none; background-color: #DFDEDE"
																		readonly></textarea>
																</div>
															</div>
															<div class="form-group row">
																<p class="col-sm-2 font-weight-bold">첨부파일</p>
																<div class="col-sm-5">
																	<img class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;"> <img
																		class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;"> <img
																		class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;"> <img
																		class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;"> <img
																		class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;">
																</div>
																<div class="col-sm-5">
																	<a href="#" class="mr-3">2023-02-07_공지내용.pdf</a> <a
																		href="#" class="mr-3">2023-02-07_공지내용.pdf</a>
																</div>
															</div>
														</form>
														<div class="d-flex justify-content-center">
															<button onclick="toUpdate()"
																class="btn btn-inverse btn-round waves-effect waves-light mr-4">수정</button>
															<form action="#">
																<button
																	class="btn btn-inverse btn-round waves-effect waves-light">삭제</button>
															</form>
														</div>
													</div>
												</div>
												<!-- Input Alignment card end -->
											</div>
											<!------------------- 수정 버튼 누르면 바뀌는 화면 ----------------------------->
											<div class="col-sm-7" id="update" style="display: none;">
												<div class="card">
													<div class="card-header">
														<h5>문의사항</h5>
													</div>
													<div class="card-block">
														<form enctype="multipart/form-data">
															<div class="form-group row">
																<div class="col-sm-2 font-weight-bold">글번호</div>
																<div class="col-sm-1">37</div>
																<div class="col-sm-2 font-weight-bold text-right">작성자</div>
																<div class="col-sm-3">신정은(관리자)</div>
																<div class="col-sm-2 font-weight-bold text-right">답변상태</div>
																<div class="col-sm-2">답변완료</div>
															</div>
															<div class="form-group row">
																<div class="col-sm-2 font-weight-bold">제목</div>
																<div class="col-sm-6"><input type="text" class="form-control" value="수정중이에여~~"></div>
																<div class="col-sm-2 font-weight-bold text-right">작성일자</div>
																<div class="col-sm-2">2023-02-14</div>
															</div>
															<div class="form-group row">
																<p class="col-sm-2 font-weight-bold">내용</p>
																<div class="col-sm-10">
																	<textarea rows="20" cols="5" class="form-control"
																		style="border: none; background-color: #e9ecef"></textarea>
																</div>
															</div>
															<div class="form-group row">
																<p class="col-sm-2 font-weight-bold">첨부파일</p>
																<div class="col-sm-5">
																	<img class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;"> <img
																		class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;"> <img
																		class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;"> <img
																		class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;"> <img
																		class="mr-2" src="/resources/oti_images/user.png"
																		style="height: 100px; align-content: center;">
																</div>
																<div class="col-sm-5">
																	<a href="#" class="mr-3">2023-02-07_공지내용.pdf</a> <a
																		href="#" class="mr-3">2023-02-07_공지내용.pdf</a>
																</div>
															</div>
														</form>
														<div class="d-flex justify-content-center">
															<button class="btn btn-inverse btn-round waves-effect waves-light mr-4">저장</button>
														</div>
													</div>
												</div>
												<!-- Input Alignment card end -->
											</div>
											<!-- Bootstrap modal end -->
										</div>
										<div class="d-flex justify-content-end" data-toggle="modal" data-target="#writeQna">
											<img class="rounded newPost"
												src="/resources/oti_images/newPost.png">
										</div>
									</div>
									<!-- Page body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="/WEB-INF/views/board/qna-write.jsp"/> 
	<%@include file="/WEB-INF/views/fragments/bottom.jsp"%>
</body>
</html>