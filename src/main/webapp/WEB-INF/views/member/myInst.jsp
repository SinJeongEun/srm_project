<%-- 작성자 : 신정은
	작성날짜 : 2023-02-20 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="/WEB-INF/views/fragments/header.jsp"%>
<script src="/resources/js/kakaoAddress.js"></script>
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
						<div class="pcoded-inner-content pt-4">
							<div class="main-body">
								<div id="pageWrapper" class="page-wrapper">
									<!-- Page-body start -->
									<div class="page-body row d-flex justify-content-center">
										<div class="card col-7">
											<div class="card-header">
												<h5>나의 기관 </h5>
											</div>
											<div class="col-8 card-block">
												<form method="post" action="<c:url value='/institution/update'/>" id="myInstForm" class="form-material">
													<div class="form-group form-default">
													<input type="hidden" value="${inst.instCd}" name="instCd">
														<input value="${inst.instNm}"type="text" name="InstNm"
															class="form-control" required style="width: 50%">
														<span class="form-bar"></span> <label class="float-label">기업명</label>
													</div>
													
													<div class="form-group form-default">
														<input value="${inst.instTelno}" type="text" name="InstTelno"
															class="form-control" required="" style="width: 50%">
														<span class="form-bar"></span> <label class="float-label">대표
															번호</label>
													</div>

													<div class="form-group form-default">
														<input type="text" name="InstAddr" id="address_kakao"
															class="form-control" required style="width: 50%"
															value="${inst.instAddr}"> 
															<input value="${inst.instDetailAddr}" class="form-control"
															type="text" name="InstDetailAddr" placeholder="상세주소"
															style="width: 70%"> <span class="form-bar"></span>
														<label class="float-label">주소</label>
													</div>
												</form>
											</div>
										</div>
									</div>
									<div class="d-flex justify-content-center">
										<button type="submit" form="myInstForm"
											class="btn waves-effect waves-light hor-grd btn-grd-primary ">수정</button>
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
	<%@include file="/WEB-INF/views/fragments/bottom.jsp"%>
</body>
</html>