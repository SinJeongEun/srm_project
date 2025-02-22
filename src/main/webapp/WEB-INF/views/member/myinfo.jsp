<%-- 작성자 : 신정은
	작성날짜 : 2023-02-20 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<%@include file="/WEB-INF/views/fragments/header.jsp"%>
<script
	src="${pageContext.request.contextPath}/resources/js/kakaoAddress.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/image.js"></script>

</head>
<style>
.box {
	width: 200px;
	border: 1px solid #77aaff;
	box-sizing: border-box;
	border-radius: 10px;
	padding: 12px 13px;
	font-family: 'Roboto';
	font-style: normal;
	font-weight: 400;
	font-size: 14px;
	line-height: 16px;
}

.box:focus {
	border: 1px solid #9B51E0;
	box-sizing: border-box;
	border-radius: 10px;
	outline: 3px solid #77aaff;
	border-radius: 10px;
}

#chooseFile {
	visibility: hidden;
}

label>img {
	margin-top: 50px;
	border-radius: 50%;
}
</style>
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
						<div class="pcoded-inner-content pt-4">
							<div class="main-body">
								<div id="pageWrapper" class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body">
										<!-- *********** -->
										<div class="card">
											<div class="card-header">
												<h5>내정보 관리</h5>
											</div>
											<div class="row">
												<div class="col-5"
													style="justify-content: center; text-align: center;">
													<div class="mb-2">
														<div class="container">
															<div class="image-upload" id="image-upload">
																<form method="post" enctype="multipart/form-data"
																	id="profile">
																	<div class="button">
																		<label for="chooseFile" id="newImg"> <c:if
																				test="${member.fileData eq null}">
																				<img id="defaultImage"
																					src="/resources/oti_images/user.png"
																					style="height: 400px; align-content: center; margin-top: 50px;">
																			</c:if> <c:if test="${member.fileData ne null}">
																				<img id="defaultImage" src="${member.fileData}"
																					style="height: 400px; align-content: center; margin-top: 50px;">
																			</c:if>
																		</label>
																	</div>
																	<input type="file" id="chooseFile" name="chooseFile"
																		accept="image/*"
																		onchange="loadFile(this, '${member.memberId}')">
																</form>
															</div>
															<span style="color: gray" id="addImg">프로필 사진을
																등록해주세요.</span>
														</div>
													</div>
												</div>
												<div class="col-7 card-block">
													<form class="form-material" enctype="multipart/form-data"
														id="myinfo" action="<c:url value='/member/myinfo'/>"
														method="post">
														<div class="form-group row">
															<p class="col-sm-2 font-weight-bold">이름</p>
															<div class="col-sm-10">${member.flnm}</div>
														</div>
														<div class="form-group row">
															<p class="col-sm-2 font-weight-bold">아이디</p>
															<div class="col-sm-10">${member.memberId}</div>
														</div>
														<input name="memberId" type="hidden"
															value="${member.memberId}" />

														<div class="form-group form-default">
															<input type="password" name="pswd" class="form-control"
																required="" style="width: 50%" value="${member.pswd}">
															<span class="form-bar"></span> <label class="float-label">비밀번호</label>
														</div>



														<div class="form-group form-default">
															<input type="text" name="telNo" class="form-control"
																required="" style="width: 50%" value="${member.telNo }">
															<span class="form-bar"></span> <label class="float-label">전화번호</label>
														</div>

														<div class="form-group form-default">
															<input type="text" name="eml" class="form-control"
																required="" style="width: 50%" value="${member.eml}">
															<span class="form-bar"></span> <label class="float-label">이메일
																(exa@gmail.com)</label>
														</div>

														<div class="form-group form-default">
															<input type="text" name="addr" id="address_kakao"
																class="form-control" required="" style="width: 50%"
																value="${member.addr}"> <span class="form-bar"></span>
															<label class="float-label">주소</label>
														</div>

														<c:if test="${member.memberType eq 'ROLE_CLIENT'}">
															<div class="form-group form-default"
																style="display: flex;">
																<input type="text" name="instNm" class="form-control"
																	required="" style="width: 50%"
																	value="${member.institution.instNm}"> <label
																	class="float-label">기관 소속</label>

																<div class="btn-group dropright">
																	<a href="<c:url value='/institution/add'/>"
																		type="button"
																		class="btn waves-effect waves-light hor-grd btn-grd-inverse ml-2">
																		기관 등록 </a>
																</div>
															</div>
														</c:if>
														<c:if test="${member.memberType ne 'ROLE_CLIENT'}">
															<div class="form-group form-default"
																style="display: flex;">
																<p class="col-sm-2 font-weight-bold">부서</p>
																<div class="col-sm-10">${member.department.deptNm}</div>
															</div>

															<div class="form-group form-default"
																style="display: flex;">
																<p class="col-sm-2 font-weight-bold">직급</p>
																<div class="col-sm-10">${member.jobGrade.jbgdNm}</div>
															</div>
														</c:if>
													</form>
													<div class="d-flex">
														<button type="submit" form="myinfo"
															class="btn btn-inverse btn-round waves-effect waves-light"
															style="margin-left: 160px;">저장</button>
													</div>
												</div>
											</div>
										</div>


										<!-- *********** -->
									</div>
									<!-- Page-body end -->
								</div>
								<div id="styleSelector"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/WEB-INF/views/fragments/bottom.jsp"%>
</body>
</html>