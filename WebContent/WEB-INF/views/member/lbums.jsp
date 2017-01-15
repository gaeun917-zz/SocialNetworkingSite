<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8"/>
    <title>Insert title here</title>
    <link rel="Stylesheet" href="/team5/styles/default.css"/>
</head>

<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<div id="pageContainer">
    <div id="inputcontent">
        <br/><br/>
        <div id="inputmain">
            <div class="inputsubtitle">회원 사진첩</div>

            <table>
                <c:forEach var="board" items="${ boards }" varStatus="status">
                    <c:forEach var="uploadfiles" items="${ uploadfiles }" varStatus="status">
                        <c:choose>
                            <c:when test="${ board.boardNo eq uploadfiles.boardNo }">
                                <a href="/team5/board/board.action?boardNo=${board.boardNo}">
                                    <img style='width: 50px; height: 50px'
                                         src='/team5/upload/${ uploadfiles.savedFileName}'/>
                                </a>
                            </c:when>
                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:forEach>
            </table>

            <div class="buttons">
                <a href="/team5/memberdetail/detail.action?
                         memberid=${ loginuser.memberId }">목록</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>