<%@ page import="com.team5.dto.Board" %>
<%@ page import="com.team5.dto.Member" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8"/>
    <title>글쓰기</title>
    <link rel="Stylesheet" href="/demoweb/styles/default.css"/>
    <link rel="Stylesheet" href="/demoweb/styles/input2.css"/>
</head>


<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"/>
    <div id="pageContainer">
        <div style="padding-top:25px;text-align:center">
            <div id="inputcontent">
                <div id="inputmain">
                    <div class="inputsubtitle">회원 정보 수정</div>


                    <form action="update.action" method="post">
                        <input type='hidden' name="memberid" value="${ loginuser.memberId }"/>
                        <table>
                            <tr>
                                <th>작성자</th>
                                <td>
                                    ${ loginuser.memberId }
                                </td>
                            </tr>
                            <tr>
                                <th>비밀번호</th>
                                <td>
                                    <input type="text" name="passwd" style="width:300px"
                                           value='${ member.memberPasswd }'/>
                                </td>
                            </tr>
                            <tr>
                                <th>주소</th>
                                <td>
                                    <input type="text" name="address" style="width:300px"
                                           value='${ member.memberAddress }'/>
                                </td>
                            </tr>
                            <tr>
                                <th>이메일</th>
                                <td>
                                    <input type="text" name="email" style="width:300px"
                                           value='${ member.memberEmail }'/>
                                </td>
                            </tr>
                            <tr>
                                <th>사용자 타입</th>
                                <td>
                                    ${ member.memberType }
                                </td>
                            </tr>
                        </table>

                        <div class="buttons">
                            <input type="submit" value="등록" style="height: 25px"/>
                            <input type="button" value="취소" style="height: 25px"
                                   onclick="location.href='/team5/member/memberdetail/memberinfo.action?' +
                                           'memberid=${loginuser.memberId}';"/>
                        </div>
                    </form>


                </div>
            </div>
        </div>
    </div>
</body>
</html>