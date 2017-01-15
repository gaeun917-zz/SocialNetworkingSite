<%@page import="com.team5.dto.Member" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>Home</title>
    <link rel="Stylesheet" href="/team5/styles/default.css"/>
</head>

<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"/>
    <div id="pageContainer">
         <form action="/team5/memberinfoinsert.action" method="post">
            <%
                Member member = (Member) request.getAttribute("member");
            %>
             <input type="hidden" id="memberid" name="memberid" value="<%= member.getMemberId() %>"/>
                <table>
                    <tr>
                        <th>고등학교</th>
                        <td><input type="text" id="highschool"
                                   name="highschool" style="width: 280px"/>
                        </td>
                    </tr>
                    <tr>
                        <th>대학교</th>
                        <td><input type="text" id="university"
                                   name="university" style="width: 280px"/>
                        </td>
                    </tr>
                    <tr>
                        <th>지역</th>
                        <td><input type="text" id="location"
                                   name="location" style="width: 280px"/>
                        </td>
                    </tr>
                    <tr>
                        <th>연락처</th>
                        <td><input type="text" id="phone"
                                   name="phone" style="width: 280px"/>
                        </td>
                    </tr>
                </table>

             <div class="buttons">
                <input type="submit" value="완료" style="height: 25px"/>
             </div>
         </form>
    </div>


    <div id="inputcontent">
        <br/> <br/>
        <div id="inputmain">
            <div class="inputsubtitle">로그인정보</div>

            <form action="login.action" method="post">
                <%
                    String returnUrl = request.getParameter("returnurl");
                    if (returnUrl == null)
                        returnUrl = "";
                %>
                <input type="hidden" name="returnurl" value="<%=returnUrl%>"/>
            </form>
        </div>
    </div>
</body>
</html>
