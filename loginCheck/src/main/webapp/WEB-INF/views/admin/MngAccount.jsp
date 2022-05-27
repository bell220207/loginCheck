<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계정 잠금 관리자 페이지</title>
<style>
    table{
        width:40%;
        border: 1px solid #222;
        border-collapse: collapse;
    }
    thead{
        background: #eee;
    }
    th, td {
        border: 1px solid #ccc;
        padding: 5px;
        font-size: 0.8em;
        text-align: center;
    }
</style>
<script src="${pageContext.request.contextPath}/resources/js/jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/acctManageApi.js"></script>
</head>
<body onload="gridTest()"> <!-- 임시로 걸어둔 것 -->

	<header>
		<div class="navbar">
			<p> 로그인 유효시간: <span id="timer"></span>
		</div>
	</header>

	<div class="container">
		<div class="heading">
			<div><h3>잠금 계정 목록</h3></div>
		</div>
		<div class="body">
			<table class="table">
				<thead>
					<tr>
						<th>번호</th>
						<th>이름</th>
						<th>아이디</th>
						<th>계정상태</th>
						<th>계정잠금</th>
					</tr>
				</thead>
				<tbody>
					 <!-- results 라는 List<UserVO>가 넘어오는 것 -->
					 <c:forEach var="vo" items="${results}" varStatus="status">
		    			<tr>
		    				<td id="num${status.count}" name="num" class="num">${status.count}</td> <!-- 번호 찍기 -->
						    <td id="name${status.count}" name="usr_nm" class="usr_nm">${vo.usr_nm}</td> <!-- 프로퍼티를 소문자로 해주어야 오류가 나지 않음...-->
			    			<td id="id${status.count}" name="usr_id" class="usr_id">${vo.usr_id}</td>
			    			<c:choose>
			    				<c:when test="${vo.usr_lck eq 'Y'}">
			    					<td id="lck${status.count}" name="usr_lck" class="usr_lck">잠금</td>
			    				</c:when>
			    				<c:otherwise>
			    					<td id="lck${status.count}" name="usr_lck" class="usr_lck">미잠금</td>
			    				</c:otherwise>
			    			</c:choose>	
			    			<td><input type="checkbox" id="unLockbox${status.count}" name="box" class="box" value="Lock"></td>
		    			</tr>
		    		</c:forEach>
	    		</tbody>
			</table>
		</div> <!-- body -->
	</div> <!-- container -->
</body>
</html>