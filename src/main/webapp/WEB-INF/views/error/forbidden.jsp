<%-- 작성자: 신정은 / 작성 날짜: 230311 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

<title>403 고객용 에러페이지</title>

<!-- Google font -->
<link href="https://fonts.googleapis.com/css?family=Kanit:200"
	rel="stylesheet">

<!-- Font Awesome Icon -->
<link type="text/css" rel="stylesheet" href="css/font-awesome.min.css" />

<!-- Custom stlylesheet -->
<link type="text/css" rel="stylesheet" href="css/style.css" />

<style type="text/css">
* {
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}

body {
	padding: 0;
	margin: 0;
	background: linear-gradient(135deg, #360940 10%, #F05F57 100%);
}

#notfound {
	position: relative;
	height: 100vh;
}

#notfound .notfound {
	position: absolute;
	left: 50%;
	top: 50%;
	-webkit-transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
}

.notfound {
	max-width: 767px;
	width: 100%;
	line-height: 1.4;
	text-align: center;
	padding: 15px;
}

.notfound .notfound-404 {
	position: relative;
	height: 220px;
}

.notfound .notfound-404 h1 {
	font-family: 'Kanit', sans-serif;
	position: absolute;
	left: 50%;
	top: 50%;
	-webkit-transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
	transform: translate(-50%, -50%);
	font-size: 186px;
	font-weight: 200;
	margin: 0px;
	background: linear-gradient(130deg, #ffa34f, #ff6f68);
	color: transparent;
	-webkit-background-clip: text;
	background-clip: text;
	text-transform: uppercase;
}

.notfound h2 {
	font-family: 'Kanit', sans-serif;
	font-size: 33px;
	font-weight: 200;
	text-transform: uppercase;
	margin-top: 0px;
	margin-bottom: 25px;
	letter-spacing: 3px;
}

.notfound p {
	font-family: 'Kanit', sans-serif;
	font-size: 16px;
	font-weight: 200;
	margin-top: 0px;
	margin-bottom: 25px;
}

.notfound a {
	font-family: 'Kanit', sans-serif;
	color: #ffa34f;
	font-weight: 200;
	text-decoration: none;
	border-bottom: 1px dashed #fff;
	border-radius: 2px;
}

.notfound-social>a {
	display: inline-block;
	height: 40px;
	line-height: 40px;
	width: 40px;
	font-size: 14px;
	color: #92344B;
	border: 1px solid #92344B;
	border-radius: 50%;
	margin: 3px;
	-webkit-transition: 0.2s all;
	transition: 0.2s all;
}

.notfound-social>a:hover {
	color: #F05F57;
	background-color: #ff6f68;
	border-color: #ff6f68;
}

@media only screen and (max-width: 480px) {
	.notfound .notfound-404 {
		position: relative;
		height: 168px;
	}
	.notfound .notfound-404 h1 {
		font-size: 142px;
	}
	.notfound h2 {
		font-size: 22px;
	}
}
</style>
</head>

<body>
	<div id="notfound">
		<div class="notfound">
			<div class="notfound-404">
				<h1>403</h1>
			</div>
			<h2 style="color: white">접근 권한이 없습니다.</h2>
			<p style="color: white">불편을 끼쳐드려 죄송합니다. 자세한 사항은 관리자에게 문의 바랍니다.</p>
			<p>
				<a href="${pageContext.request.contextPath}/myportal">메인홈으로 돌아가기</a>
			</p>
		</div>
	</div>
</body>
</html>