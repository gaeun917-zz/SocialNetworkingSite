<%@ page import="com.team5.dto.*" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>


<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8"/>
    <title></title>
    <link rel="Stylesheet" href="/team5/styles/default.css"/>
</head>

<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"/>
<div id="pageContainer">
    <div id="content">
        <br/><br/>

        <div style='text-align: center'>
            [&nbsp; <a href="register.action">사용자 등록</a> &nbsp;]
        </div>

        <br/><br/>

        <%-- 여기에 서블릿에서 조회한 데이터를 출력 --%>
        <table border="1" align="center" width="700px">
            <tr style="height: 30px; background-color: orange; text-align: center; padding: 5px">
                <td>아이디</td>
                <td>이메일</td>
                <td>사용자구분</td>
                <td>활성화여부</td>
                <td>등록일자</td>
            </tr>

            <c:forEach var="member" items="${ members }">
                <tr style="height: 30px;">
                    <td style="padding: 5px">
                        <a href='detail.action?
                            memberid=${ member.memberId }'> ${ member.memberId }
                        </a>
                    </td>
                    <td style="padding: 5px">${ member.email }</td>
                    <td style="padding: 5px">${ member.userType }</td>
                    <td style="padding: 5px">${ member.regDate }</td>
                </tr>
            </c:forEach>

        </table>
    </div>
</div>
</body>
</html>









