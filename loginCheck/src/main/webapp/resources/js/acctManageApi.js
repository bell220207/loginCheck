var sessionTime = 1800; // 초 단위, 1분. (30분은 1800초)
var objCheckSession;  // setInterval 함수 담을 변수
var remainMinute, remainSecond, remainMinSec;
let click;

$(function(){
	click = document.querySelector('body');
	console.log("도큐먼트 온로드");
	
	objCheckSession = setInterval(fnCheckSession, 1000);
	setTimeout(() => { clearInterval(objCheckSession); 
					   alert('로그인 유효시간이 만료되었습니다. 재로그인 해주세요.'); 
				//	   window.location.href="172.16.34.142:23627/"; 
				//	   고객사 메인화면
					 }, 1800000); // 1800*1000
	
	
	
	$('.box').click(function(){
		var tr = $(this).parent().parent();
		var td = tr.children();
		
		var id = td.eq(2).text();		// 클릭한 행의 아이디 '컬럼' 값
		var box = $(this).attr("id");	// 클릭한 체크박스의 아이디 속성값	
		
		var lck_value = td.eq(3).text();  // 미잠금 or 잠금
		var lck_id = td.eq(3).attr("id"); // lck1 ...
	
	    $.ajax({
			type:"post",
			url: "/MngAccount",
	//		contentType:"application/json; charset=UTF-8",
    //		data : JSON.stringify({"id":id}),
    		data : ({"id":id}),
			success: function(data){
						
						alert("변경성공");
						if(data.usr_lck == 'N'){
							$("#"+lck_id).html("미잠금");
							
						}else if(data.usr_lck == 'Y'){
							$("#"+lck_id).html("잠금");
						}
					 },
			error: function(error, textStatus){
					alert("연결실패");
					console.log("error:", error);
					console.log("textStatus:", textStatus);}
		});
	});
	
	// ================================================================	
	
	$("#loginBtn").click(function(){
    	
		if( $("#tfUserId").val()=='' ){
			alert("아이디를 입력해주세요.");
			return;
		}
		
		if($("#tfUserPass").val()==''){
			alert("비밀번호를 입력해주세요.");
			return;
		}else{
			// 당사 로그인 페이지 버튼에, 로그인 로직 실행 전 ajax 요청 넣기
			var input_pwd = $("#tfUserPass").val();
		//	console.log("c: "+c);
			
		//	var d = encodeURIComponent(c);
		//	console.log("d: "+d);
			
		//	var e = encodeURIComponent(d);
		//	console.log("e: "+e);
			
			
	    	$.ajax({
				type : "POST",
				url : "/loginCheckApi",
			//	contentType:"application/json; charset=UTF-8",
				data : ({"id":$("#tfUserId").val(),
						 "pwd":input_pwd
					   }),
				dataType : "json",
				success : function(jRes) {
						console.log("통신 성공");
						var result = jRes.resData.result
						var id = result.id;
						var pwd = result.pwd;
						var name = result.name;
						var count = result.count;
						var lck = result.lck;
						var msg = result.msg;
						var response = result.response;
						
						alert(msg);

				},
				error:function(error){
					console.log("놉");
					console.log("error"+error);
				}
			});	// ajax-bell 끝


		} // if-else 끝
	}); // 클릭 메서드 끝
	

}); // 온로드 끝

function fnCheckSession(){
	sessionTime = sessionTime - 1;    //초단위
    remainMinute = parseInt((sessionTime / 60) % 60);    //남은 분
    remainSecond = parseInt(sessionTime % 60);    //남은 초
    
    remainMinSec = " 00:"+Lpad(remainMinute, 2) + ":" + Lpad(remainSecond, 2);
    $("#timer").html(remainMinSec);
	
	click.onclick = function(){
    	sessionTime = 1800;
	}
	
	click.onkeydown = function(){
    	sessionTime = 1800;
	}
}

Lpad = function(str, len) {
    str = str + "";
    while (str.length < len){
         str = "0" + str;
    }
    return str;
}

function gridTest(){
	// alert("onload gridTest 함수 작동");

}