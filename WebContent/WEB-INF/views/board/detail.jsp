<%@page import="com.team5.dto.Board" %>
<%@ page language="java" contentType="text/html;charset=utf-8"
         pageEncoding="utf-8" session="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>글쓰기</title>
</head>

<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<div id="pageContainer">
    <div style="padding-top: 25px; text-align: center">
        <div id="inputcontent">
            <div id="inputmain">
                <div class="inputsubtitle">게시판 글 내용</div>
                <%-- Board dto에서 데이터 데이터를 가져옴 --%>
                <%
                    Board b = (Board) request.getAttribute("board");
                %>
                <table>
                    <tr>
                        <th>제목</th>
                        <td>${board.title}</td>
                    </tr>
                    <tr>
                        <th>작성자</th>
                        <td>${board.writer}</td>
                    </tr>
                    <tr>
                        <th>작성일</th>
                        <td>${board.regDate}</td>
                    </tr>
                    <tr>
                        <th>조회수</th>
                        <td>${board.readCount}</td>
                    </tr>
                    <tr>
                        <th>내용</th>
                        <td style="height: 200px; vertical-align: top">${fn:replace(board.content, rn, br)}</td>
                        <c:set var="rn" value=""/>
                        <c:set var="br" value="<br>"/>
                    </tr>
                </table>

                <div class="buttons">
                    <script type="text/javascript">
                        function doDelete(boardNo) {
                            var yes = confirm(boardNo + '번 글을 삭제할까요?');
                            if (yes) {
                                location.href = 'delete.action?'
                                    + 'boardno=' + boardNo;
                            }
                        }
                    </script>

                    <!-- memberId는 session(loginuser)에 들어 있음   -->
                    <c:choose>
                        <c:when test="${loginuser.memberId == board.writer}">
                            <a href='javascript:
                                     doDelete(${board.boardNo})'>삭제</a>&nbsp;&nbsp;
                            <a href='edit.action?
                                     boardno=${board.boardNo}&pageno=${requestScope.pageNo}'>수정</a>&nbsp;&nbsp;
                        </c:when>

                        <c:otherwise>
                            <a href='replyform.action?
                                     boardno=${board.boardNo}&pageno=${requestScope.pageNo}'>댓글쓰기</a>&nbsp;&nbsp;
                            <a href='list.action?
                                     pageno=${requestScope.pageNo}'>리스트보기</a>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
        <br/><br/>

        <!------------------ comment 쓰기 영역 시작 -------------------->

        <form id="commentform" action="writecomment.action" method="post">
            <input type="hidden" name="boardno" value="${board.boardNo}"/>
            <input type="hidden" name="pageno" value="${requestScope.pageNo}"/>
            <!-- getAttribute:서버에서 사용하는 데이터 getParameter: input 받기 -->
            <table style="width: 300px; border: solid 1px; margin: 0 auto">
                <tr>
                    <td style="width: 550px">
                        <textarea name="content" style="width: 550px" rows="3">Comment는 여기에 써주세요!</textarea>
                    </td>
                    <td style="width: 50px; vertical-align: middle">
                        <a href="javascript:
                                     document.getElementById('commentform').submit();">댓글등록</a>
                    </td>
                </tr>
            </table>
        </form>

        <!------------------- no previous comment  ------------------------->

        <c:choose>
            <c:when test="${ empty board.comments }">
                <h4 id="nodata">작성된 댓글이 없습니다.</h4>
            </c:when>
            <!-------------- comment 표시 영역 ------------------------>
            <c:otherwise>
                <table id="detailTable">
                    <c:forEach var="bcomment" items="${board.comments}">
                        <tr>
                            <td id="detailTable_td">
                                <!-- 코멘트 뷰에 아이디로 식별값을 넣음 -->
                                <div id='commentview ${bocomment.commentNo}'>
                                        ${bcomment.writer} &nbsp;&nbsp; [ ${bcomment.regDate}]
                                    <br/><br/>
                                    <span>${bcomment.content}</span>
                                    <br/><br/>

                                    <c:choose>
                                        <c:when test="${ loginuser.memberId == bcomment.memberId }">
                                            <%--var에 style= display toggle할 수 있게 value 저장 --%>
                                            <c:set var="display" value="${ block }"/>
                                        </c:when>

                                        <c:otherwise>
                                            <c:set var="display" value="${ none }"/>
                                        </c:otherwise>
                                    </c:choose>

                                    <div style="display: ${display}">
                                        <a href="javascript:
                                                 toggleCommentStatus(${bcomment.commentNo}, true);">편집</a> &nbsp;
                                        <!--toggleCommentStatus 라는 자바스크립트 function이 돌고 있음: 커멘트 번호, 보드넘버, 페이지 넘버(getAttribute)를 가져옴   -->
                                        <a href="javascript:
                                                 deleteComment(${bcomment.commentNo}, ${bcomment.boardNo})">삭제</a>
                                    </div>

                                </div>

                                <div id='commentedit${bcomment.commentNo}' style="display: none">
                                        ${bcomment.writer} &nbsp;&nbsp; [${bcomment.regDate} ]
                                            <br/><br/>

                                    <form id="commenteditform${bcomment.commentNo}"
                                          action="updatecomment.action" method="post">
                                        <input type="hidden" name="boardno" value="${board.boardNo}"/>
                                        <input type="hidden" name="commentno" value="${bcomment.commentNo}"/>
                                        <textarea name="content" style="width: 600px" rows="3" maxlength="200">
                                                    ${bcomment.content}
                                        </textarea>
                                    </form>


                                    <div>
                                        <a href="javascript:
                                                    document.getElementById('commenteditform${bcomment.commentNo}')
                                                    .submit();">수정</a>&nbsp;
                                        <a href="javascript:
                                                    toggleCommentStatus(${bcomment.commentNo}, false);">취소</a>
                                    </div>

                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
        <br/><br/><br/><br/><br/>
    </div>
</div>
</body>
</html>