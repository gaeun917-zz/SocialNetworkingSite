<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8"/>
    <title>로그인</title>
    <link rel="Stylesheet" href="/team5/styles/default.css"/>
    <link rel="Stylesheet" href="/team5/styles/input.css"/>
</head>


<body>
<div id="pageContainer">
    <div id="inputcontent">
        <br/><br/>
        <div id="inputmain">
            <div class="inputsubtitle">Login</div>
            <script type="alert"> </script>

            <form action="login.action" method="post">
                <%
                    String returnUrl = request.getParameter("returnurl");
                    if (returnUrl == null) returnUrl = "";
                %>
                <input type="hidden" name="returnurl" value="<%= returnUrl %>"/>

                <c:choose>
                    <c:when test="${not empty loginuser }">
                        ${loginuser.memberId }
                        <a href="#">로그아웃</a>
                    </c:when>

                    <c:otherwise>
                        <table>
                            <tr>
                                <th>ID</th>
                                <td>
                                    <input type="text" name="memberId" style="width:280px"/>
                                </td>
                            </tr>
                            <tr>
                                <th>Password</th>
                                <td>
                                    <input type="password" name="passwd" style="width:280px"/>
                                </td>
                            </tr>
                        </table>
                    </c:otherwise>
                </c:choose>
                <div class="buttons">
                    <input type="submit" value="로그인" style="height:25px"/>
                    <input type="button" value="취소" style="height:25px"
                           onclick="location.href='/demoweb/';"/>
                </div>
            </form>


        </div>
    </div>
</div>
</body>
</html>