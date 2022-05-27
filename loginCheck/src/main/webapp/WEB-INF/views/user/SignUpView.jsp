<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="${pageContext.request.contextPath }/resources/js/jquery.js"></script>
</head>
<body>
	<p>회원가입 페이지</p>
	<form action="SignUp" name="joinForm" method="post"> 

		<div class="name">
			<label for="name">이름</label>
			<input type="text" name="usr_nm" id="usr_nm">
		</div>

		<div class="id">
			<label for="id">아이디</label>
			<input type="text" name="usr_id" id="usr_id"> <br/>
		</div>
		
		<div class="pw">
			<label for="pwd">비밀번호</label>
			<input type="password" name="pwd_no_enc_cntn" id="pwd_no_enc_cntn" placeholder="조건쓰기" >
		</div>
		
		<span id="msgPw" ></span>
		<div><button type="button" id="joinBtn">가입하기</button></div>
	</form>
	<script>
	/*비밀번호 형식 검사 스크립트*/
        var pw = document.getElementById("pwd_no_enc_cntn");
        pw.onkeyup = function(){
            var regex = /^[A-Za-z0-9+]{8,16}$/;
            
            if(regex.test(document.getElementById("pwd_no_enc_cntn").value )) {
                document.getElementById("pwd_no_enc_cntn").style.borderColor = "green";
                document.getElementById("msgPw").innerHTML = "";
                
			 // $("#pwd").attr("readonly",true);
                
            } else {
                document.getElementById("pwd_no_enc_cntn").style.borderColor = "red";
                document.getElementById("msgPw").innerHTML = "조건()에 맞지 않습니다";
            }
        }
    </script>
    
    <script>
	    $("#joinBtn").click(function(){
    		if ($("#usr_nm").val()==''){
    			alert("이름은 필수입니다.");
    			return;
    			
    		}else if( $("#usr_id").val()=='' ){
    			alert("아이디는 필수입니다.");
    			return;
    			
    		}else if( $("#pwd_no_enc_cntn").val()=='' ){
    			alert("비밀번호는 필수 입니다.");
    			return;
    			
    		}else if( $("#msgPw").html() != '' ){
    			alert("조건()에 맞는 비밀번호가 아닙니다");
    			return; 
    			
    		}else{
    			alert("가입성공");
    			document.joinForm.submit();
    		} 		
    	});
	</script>
</body>
</html>