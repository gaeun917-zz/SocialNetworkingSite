<%@page import="com.team5.dto.Member"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="/team5/styles/css/bootstrap.min.css" rel="stylesheet" />
<link href="/team5/styles/default.css" rel="stylesheet" />

<script src="http://code.jquery.com/jquery.js"></script>
<script src="/team5/styles/js/bootstrap.min.js"></script>


<script type="text/javascript">
	var proxy;//XMLH 객체 참조변수	
	//count=0;
	//------------------------------------------------
	//여기부터 자동 완성 기능 구현
	//문서에서 자동완성박스 이외의 영역을 클릭했을 때 
	//호출될 메서드 정의
	document.onclick = function(event) {
		var d = document.getElementById('divAutoCom')
		if (d) {
			document.body.removeChild(d);//화면에서 제거
		}
	}

	function doAutoComplete() {
		//사용자가 입력한 아이디 읽기
		var id = document.getElementById("search");

		if (id.value.length == 0) {
			var d = document.getElementById('divAutoCom')
			if (d) {
				document.body.removeChild(d);//화면에서 제거
			}
			return;
		}

		//읽은 아이디가 1문자 이상인 경우에			
		//XMLHttpRequest 객체를 사용해서 비동기 요청 전송
		proxy = new XMLHttpRequest();
		proxy.open("GET", "/team5/getsugg	estions.action?id=" + id.value, true);
		proxy.onreadystatechange = showSuggestions;
		proxy.send(null);
		//수신 결과 alert로 출력		
	}
	function showSuggestions() {
		if (proxy.readyState == 4) {
			if (proxy.status != 200) {
				//alert('error ' + proxy.status);
				return;
			}
			var result = proxy.responseText;
			//alert(result);
			showResult(result);
		}
	}

	var divList = null;
	function showResult(data) {
		//이미 표시되고 있는 자동완성 박스가 있으면 제거
		var d = document.getElementById('divAutoCom');
		if (d) {
			document.body.removeChild(d);//화면에서 제거
		}

		if (data.length == 0)
			return;
		var nameArray = data.split(";");

		//외부 박스 만들기
		divList = document.createElement("div");
		divList.id = "divAutoCom";
		divList.style.border = "1px	black solid";
		divList.style.backgroundColor = "white";
		divList.style.width = "230px";
		divList.style.position = "absolute";//다른 마크업 위에 표시		
		divList.style.left = "50px";
		document.body.appendChild(divList);

		//내부 박스 만들기
		var item;
		for (var i = 0; i < nameArray.length; i++) {
			item = document.createElement("div");
			item.style.paddingLeft = "5px";
			item.style.paddingTop = item.style.paddingBottom = "2px";
			item.style.width = "97%";
			item.innerHTML = nameArray[i];//데이터 지정
			divList.appendChild(item);

			//마우스 이벤트 등록
			item.onmousedown = function(oEvent) {
				oEvent = oEvent || window.event;
				oSrcElement = oEvent.target || oEvent.srcElement;
				document.getElementById("search").value = oSrcElement.innerHTML;
				divList.style.display = "none";
			};
			item.onmouseover = function(oEvent) {
				oEvent = oEvent || window.event;
				oSrcElement = oEvent.target || oEvent.srcElement;
				oSrcElement.style.backgroundColor = "#efefef";
			};
			item.onmouseout = function(oEvent) {
				oEvent = oEvent || window.event;
				oSrcElement = oEvent.target || oEvent.srcElement;
				oSrcElement.style.backgroundColor = "";
			};
		}

		//외부 박스의 위치 지정
		divList.style.left = getLeft() + "px";
		divList.style.top = getTop() + "px";
	}

	function getTop() {
		var t = document.getElementById("search");

		var topPos = 2 + t.offsetHeight;
		while (t.tagName.toLowerCase() != "body"
				&& t.tagName.toLowerCase() != "html") {
			topPos += t.offsetTop;//offsetTop : 상위 요소와의 거리
			t = t.offsetParent; //상위 요소를 현재 요소에 대입
		}
		return topPos;
	}

	function getLeft() {
		var t = document.getElementById("search");

		var leftPos = 0;
		while (t.tagName.toLowerCase() != "body"
				&& t.tagName.toLowerCase() != "html") {
			leftPos += t.offsetLeft;//t와 t좌측 요소 사이의 거리
			t = t.offsetParent;//t의 좌측 요소
		}
		return leftPos;
	}
	//=============================아래는 검색 기능===================================================//

	function dofriendSearch() {
		var search = document.getElementById("search");
		location.href = '/team5/friend/friendSearch.action?search='
				+ search.value;
	}

	/* function loginCheck(){
	 var passwd=document.getElementById("passwd");	

	 if(passwd.value.length==0){
	 alret("비밀번호를 써주세요");
	 }	

	 }
	 */

	//=================================아래는 댓글 알림기능===============================================//
	function doCommentConfirm() {

		/* var createDiv=document.createElement("div");
		createDiv.innerHTML="가 댓글을 등록했습니다.";	 
		table.td.appendChild(createDiv); */

		//var table=document.getElementById("table");
		setTimeout("doCommentConfirm()", 2000);

		//XMLHttpRequest 객체를 사용해서 비동기 요청 전송
		proxy = new XMLHttpRequest();
		proxy.open("GET", "/team5/commentConfirm.action", true);
		proxy.onreadystatechange = showComment;
		proxy.send(null);
		//수신 결과 alert로 출력		

	}

	function showComment() {
		if (proxy.readyState == 4) {
			if (proxy.status != 200) {
				//alert('error ' + proxy.status);
				return;
			}
			var result = proxy.responseText;
			//alert(result);
			showCommentResult(result);
		}
	}

	function showCommentResult(data) {
		//이미 표시되고 있는 자동완성 박스가 있으면 제거
		var d = document.getElementById('divComment');
		if (d) {
			document.body.removeChild(d);//화면에서 제거
		}

		if (data.length == 0)
			return;
		var nameArray = data.split(";");

		//외부 박스 만들기
		divList = document.createElement("div");
		divList.id = "divComment";
		divList.style.border = "1px	black solid";
		divList.style.backgroundColor = "white";
		divList.style.width = "230px";
		divList.style.position = "absolute";//다른 마크업 위에 표시		
		document.body.appendChild(divList);

		//내부 박스 만들기
		var item;
		for (var i = 0; i < nameArray.length; i++) {
			item = document.createElement("div");
			item.style.paddingLeft = "5px";
			item.style.paddingTop = item.style.paddingBottom = "2px";
			item.style.width = "97%";
			item.innerHTML = nameArray[i];//데이터 지정
			divList.appendChild(item);
		}

		//외부 박스의 위치 지정
		/* divList.style.left = getLeft() + "px";
		divList.style.top =	getTop() + "px"; */

		divList.style.left = "1200px";
		divList.style.top = "100px";

		//count=count+1;
	}
</script>


<div id="header">
	<div class="title">
		<h1>
			<a href="#">fakebook</a>
		</h1>
	</div>
	<c:if test="${not empty sessionScope.loginuser }">

	<!-- <img src="/team5/styles/image/fb.jpg"> -->

	<nav class="navbar navbar-right navbar-fixed-top">
		<div class="containter">
			<div class="btn-group">

				<input type="text" id="search" placeholder="지역/학교/이름 입력"
					name="search" style="width: 150px; color: #010802"
					onkeyup="doAutoComplete();" /> <input type="button"
					class="btn btn-primary" style="color: #010802" value="검색"
					onclick="dofriendSearch();" /> <input type="button"
					class="btn btn-primary" id="memberEmail"
					value="${ loginuser.email }"
					onclick="location.href='/team5/memberdetail/detail.action?memberid=${ loginuser.memberId }'">

				<input type="button" class="btn btn-primary" id="logout"
					value="로그아웃" onclick="location.href='/team5/account/logout.action'">

				<input type="button" class="btn btn-primary" id="friendReceiveForm"
					value="친구요청"
					onclick="location.href='/team5/friend/friendReceiveForm.action'">

				<input type="button" class="btn btn-primary" id="alarm"
					value="메세지알림" onclick="location.href='#'"> <input
					type="button" class="btn btn-primary" id="home" value="타임라인"
					onclick="location.href='/team5/board/list.action'"> <input
					type="button" class="btn btn-primary" id="friend" value="친구"
					onclick="location.href='/team5/friend/friendViewForm.action'">

			</div>
</c:if>

			<!--  //////////////////////////아래부터는 로그인 안했을 경우///////////////////////////////////////// -->

			<c:if test="${empty sessionScope.loginuser}">
				<div class="links">
					<form class="form-inline" action="/team5/login.action"
						method="post" align="right">
						<div class="form-group">
							<label for="inputEmail">이메일 </label> <input type="email"
								class="form-control" id="inputEmail" name="email"
								style="width: 250px" placeholder="Email"> <label
								for="inputPassword">비밀번호 </label> <input type="password"
								class="form-control" id="passwd" name="passwd"
								placeholder="Password" style="width: 180px; color: #010802" />
							<input type="submit" id="login" name="login "
								class="btn btn-primary" value="로그인">
						</div>



					</form>
				</div>
			</c:if>


		</div>

	</nav>
</div>

<body onload="doCommentConfirm();">

	<%-- <table border="1" id="table" float="right">
		           		<c:forEach var="comment" items="${applicationScope.comment}"><!-- 친구가 댓글, 좋아요을 눌렀다면 알림목록  -->
		            		<tr>
		            			<td id="td">
		            				 ${comment.writer}님이 좋아요를 눌렀습니다.=========<br>
		            			</td>	 	
		            	 	</tr>           		
		            	</c:forEach>
		            	
		            	</table> --%>
	<div id="commentConfirm" float="right"></div>
</body>

