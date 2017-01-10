<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Home</title>

<link rel="Stylesheet" href="/c8/styles/default.css" />
</head>
<body>
	
	
		

			<form action="/team5/modifyServlet.action" method="post">
				<table>
					
					<tr>
						<th>비밀번호</th>
						<td><input type="password" id="passwd" name="passwd"
							style="width: 280px" /></td>
					</tr>

					<tr>
						<th>고등학교</th>
						<td><input type="text" id="highschool" name="highschool"
							style="width: 280px" /></td>
					</tr>
					
					<tr>
						<th>대학교</th>
						<td><input type="text" id="university" name="university"
							style="width: 280px" /></td>
					</tr>
					
					<tr>
						<th>지역</th>
						<td><input type="text" id="location" name="location"
							style="width: 280px" /></td>
					</tr>
					
					<tr>
						<th>연락처</th>
						<td><input type="text" id="phone" name="phone"
							style="width: 280px" /></td>
					</tr>

				</table>

				<div class="buttons">
					<input type="submit" value="수정하기" style="height: 25px" />

				</div>



			</form>
</body>
</html>


