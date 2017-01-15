<%@page import="com.team5.dto.Board"%>
<%@page import="com.team5.dto.Member"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>


<html>
<head>
<meta charset="utf-8" />
<title>글쓰기</title>
</head>
<body>
	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<div id="pageContainer">

		<div style="padding-top: 25px; text-align: center">
			<div id="inputcontent">
				<div id="inputmain">
				<div class="row">
				<span class="glyphicon glyphicon-search btn-lg" aria-hidden="true"></span>
					<div class="inputsubtitle"><h4>회원 정보</h4></div>
					 
					<form action="edit.action" method="post">
					<input type="hidden" name="memberid" value=${ loginuser.memberId } />
						<table>
						 <div class="col-md-offset-2">
						 <div class="col-md-4"></div>
							<tr>
								<th><h4>지역</h4></th>
								<td><h4>${ memberinfo.location }</h4></td>
							</tr>
							
							<tr>
								<th><h4>고등학교</h4></th>
								<td><h4>${ memberinfo.highschool }</h4></td>
							</tr>
							<tr>
								<th><h4>대학교</h4></th>
								<td><h4>${ memberinfo.university }</h4></td>
							</tr>
							<tr>
								<th><h4>전화번호</h4></th>
								<td><h4>${ memberinfo.phone }</h4></td>
							</tr>
							<tr>
								<th><h4>가입날짜</h4></th>
								<td><h4>${ memberinfo.createDate }</h4></td>
							</tr>
						</table>
						<div class="buttons">
							<input type="submit" class="btn btn-primary" value="편집" style="height: 25px" /> <input class="btn btn-primary"
								type="button" value="취소" style="height: 25px"
								onclick="location.href='/team5/memberdetail/detail.action?memberid=${loginuser.memberId}';" />
						</div>
					</form>
					</div>
					</div>
				</div>
			</div>
		</div>
		</div>
	</div>

</body>
</html>