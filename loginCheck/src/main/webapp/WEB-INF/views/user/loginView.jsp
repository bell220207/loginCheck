<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<script src="${pageContext.request.contextPath }/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/acctManageApi.js"></script>
</head>
<body>
	<p>로그인</p>
	<form action="Login" name="loginForm" method="post">
		<label for="id">아이디</label>
		<input type="text" name="tfUserId" id="tfUserId"> <br/>
		<div>
			<label for="pwd">비밀번호</label>
			<input type="password" name="tfUserPass" id="tfUserPass" >
			<div><span>${msg}</span></div>
		</div>
		<div><button type="button" id="loginBtn">로그인하기</button></div>
    </form>
</body>
</html>