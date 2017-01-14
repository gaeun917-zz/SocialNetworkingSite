<%@page import="com.team5.dto.Member" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

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
    <div style="padding-top:25px;text-align:center">
        <div id="inputcontent">
            <div id="inputmain">
                <div class="inputsubtitle">게시판 글 쓰기</div>


                <form action="write.action" method="post">
                    <table>
                        <tr>
                            <th>제목</th>
                            <td>
                                <input type="text" name="title" style="width:480px"/>
                            </td>
                        </tr>
                        <tr>
                            <th>작성자</th>
                            <td>
                                <%= ((Member) session.getAttribute("loginuser")).getMemberId() %>
                            </td>
                        </tr>
                        <tr>
                            <th>첨부파일</th>
                            <td>
                                <input type="file" name="file1" style="height: 25px"/>
                            </td>
                        </tr>
                        <tr>
                            <th>내용</th>
                            <td>
		                    <textarea name="content" cols="76" rows="15"></textarea>
                            </td>
                        </tr>
                    </table>


                    <div class="buttons">
                        <!-- 아래 a 링크는 input type='submit' 버튼을 누르는 효과 발생 -->
                        <a href="javascript:
                                    document.forms[0].submit();">글쓰기</a> &nbsp;&nbsp;
                        <a href="/demoweb/board/list.action">목록보기</a>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>