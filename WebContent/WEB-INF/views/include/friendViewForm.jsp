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
		<h4>${loginuser.name}와
			친구인 사이
			<!-- 자신에게 요청한 친구 목록 -->
		</h4>
	</div>

	<br>


	<div class="row">
		<c:forEach var="friendInfos" items="${friendInfos}">
			<c:forEach var="friends" items="${friends}">



				<c:if test="${friends.memberId == friendInfos.memberId}">
					<div class="col-md-offset-2">
						<div class="col-md-4">
							<span class="glyphicon glyphicon-user btn-lg" aria-hidden="true"></span>
							${friends.name}<br>
							<input type="button" class="btn btn-primary" value="개인페이지로 이동"
								onclick="location.href='/team5/memberdetail/detail.action?memberid=${friends.memberId}'"> <br>
							<br>
							
						</div>
					</div>
				</c:if>
			</c:forEach>
		</c:forEach>
</div>

		<div class="row">
			<c:forEach var="friendInfos2" items="${friendInfos2}">
				<c:forEach var="friends2" items="${friends2}">
					<c:if test="${friends2.memberId == friendInfos2.memberId}">
						<div class="col-md-offset-2">
							<div class="col-md-4">
								<span class="glyphicon glyphicon-user btn-lg" aria-hidden="true"></span>
								${friends2.name}<br>
								<input type="button" class="btn btn-primary" value="개인페이지로 이동"
									onclick="location.href='/team5/memberdetail/detail.action?memberid=${friends2.memberId}'"> <br>
								<br>

							</div>
						</div>
					</c:if>
				</c:forEach>
			</c:forEach>
		</div>
</body>
</html>