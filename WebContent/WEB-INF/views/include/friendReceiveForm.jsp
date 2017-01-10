<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/header.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<br>
	<br>
	<div class="col-md-offset-2 btn-lg">
		<h4>${loginuser.name}님의친구요청 목록</h4>
	</div>
	<!-- 자신에게 요청한 친구 목록 -->


	<div class="row">
	<c:if test="${not empty friends}">
		<c:forEach var="friendInfos" items="${friendInfos}">
			<c:forEach var="friends" items="${friends}">
				<c:if test="${friends.memberId == friendInfos.memberId}">
					<div class="col-md-offset-2">
						<div class="col-md-4">


							<span class="glyphicon glyphicon-plus btn-lg" aria-hidden="true"></span>
							<span class="glyphicon glyphicon-plus btn-lg" aria-hidden="true"></span>
							<br> 
							${friends.name}님의 정보<br> 지역 :	${friendInfos.location }
													<br> 대학교 : ${friendInfos.university }
													<br> 고등학교 : ${friendInfos.highschool }
													<br> <input type="button"class="btn btn-primary" value="추가"onclick="location.href='/team5/friend/friendReceive.action?memberId=${friends.memberId}'"><br><br><br><br>
						</div>
					</div>
					
				</c:if>
			</c:forEach>
		</c:forEach>
	</c:if>
	<div class="col-md-offset-2">
						<div class="col-md-4">
	<br><br><br>
	<span class="glyphicon glyphicon-user btn-lg" aria-hidden="true"></span>
	<span class="glyphicon glyphicon-remove btn-lg" aria-hidden="true"></span>
	<c:if test="${empty friends }"><h4>요청한 친구가 없습니다.</h4></c:if>
	</div></div>	
	</div>




</body>
</html>