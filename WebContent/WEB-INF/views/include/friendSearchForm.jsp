<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>

<div class="col-md-offset-2 btn-lg">
    <h4>학교/지역 : ${param.search}</h4>
</div>
<div class="col-md-offset-2 btn-lg">
    <h4>알수도 있는 친구</h4>
</div>

<div class="row">
    <c:forEach var="member" items="${searchByMember}">
        <div class="col-md-offset-2">
            <div class="col-md-4">
                <span class="glyphicon glyphicon-user btn-lg"></span>
                    ${member.name}<br>
                <input type="button" value="친구요청" class="btn btn-primary"
                       onclick="location.href='/team5/friend/friendSend.action?memberId=${member.memberId}'">
                <br><br>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>