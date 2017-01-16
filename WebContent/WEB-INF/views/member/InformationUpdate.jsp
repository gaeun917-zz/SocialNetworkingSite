<%@page import="com.team5.dto.Board" %>
<%@page import="com.team5.dto.Member" %>
<%@page import="com.team5.dto.MemberInfo" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8"/>
    <title>글쓰기</title>
    <link rel="Stylesheet" href="/team5/styles/default.css"/>
    <link rel="Stylesheet" href="/team5/styles/input2.css"/>
</head>

<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"/>
<div id="pageContainer">
    <div style="padding-top: 25px; text-align: center">
        <div id="inputcontent">
            <div id="inputmain">
                <div class="inputsubtitle">회원 정보 수정</div>


                <form action="update.action?memberid=${ loginuser.memberId }" method="post">
                    <table>
                        <tr>
                            <th>지역</th>
                            <td>
                                <input type="text" id="location" name="location"
                                       style="width:300px;height:25px" value='${ memberinfo.location }'/>
                            </td>
                        </tr>
                        <tr>
                            <th>고등학교</th>
                            <td>
                                <input type="text" id="highschool" name="highschool"
                                       style="width:300px;height:25px" value='${ memberinfo.highschool }'/>
                            </td>
                        </tr>
                        <tr>
                            <th>대학교</th>
                            <td>
                                <input type="text" id="university" name="university"
                                       style="width:300px;height:25px" value='${ memberinfo.university }'/>
                            </td>
                        </tr>
                        <tr>
                            <th>전화번호</th>
                            <td>
                                <input type="text" id="phone" name="phone" style="width:300px;height:25px"
                                       value='${ memberinfo.phone }'/></td>
                        </tr>
                    </table>

                    <div class="buttons">
                        <input type="submit" value="등록" style="height: 25px"/>
                        <input type="button" value="취소" style="height: 25px"
                                onclick="location.href='detail.action?${loginuser.memberId}';"/>
                    </div>

                </form>


            </div>
        </div>
    </div>
</div>
</body>
</html>