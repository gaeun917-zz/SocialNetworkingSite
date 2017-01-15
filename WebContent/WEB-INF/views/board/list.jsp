<%@page import="com.team5.dto.Member" %>
<%@page import="com.team5.dto.Board" %>
<%@page import="java.util.List" %>
<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="utf-8"/>
    <title>게시물 목록</title>
    <link rel="Stylesheet" href="/team5/styles/default.css"/>

    <script type="text/javascript">
        var v = null, e = null;
        function toggleCommentStatus(commentNo, edit) {
            if (v != null) {
                v.style.display = edit ? 'block' : 'none';
                e.style.display = edit ? 'none' : 'block';
            }
            v = document.getElementById("commentview" + commentNo);
            e = document.getElementById("commentedit" + commentNo);

            v.style.display = edit ? 'none' : 'block';
            e.style.display = edit ? 'block' : 'none';
        }
        function deleteComment(commentNo, boardNo) {
            var yes = confirm(commentNo + '번 댓글을 삭제 할까요?');
            if (yes) {
                location.href = 'deletecomment.action?' +
                    'commentno=' + commentNo
                    + "&boardno=" + boardNo;
            }
        }
        function doDelete(boardNo, pageNo) {
            var yes = confirm(boardNo + '번 글을 삭제할까요?');
            if (yes) {
                location.href = 'delete.action?' +
                    'boardno=' + boardNo
                    + '&pageno=' + pageNo;
            }
        }

        /////////////////////////////////////////
        var v2 = null;
        var e2 = null;
        var editlink = null;
        var updatelink = null;
        var cancellink = null;

        function toggleBoardStatus(boardNo, edit) {
            if (v2 != null) {
                v2.style.display = edit ? 'block' : 'none';
                e2.style.display = edit ? 'none' : 'block';

            } else {

                v2.style.display = edit ? 'none' : 'block';
                e2.style.display = edit ? 'block' : 'none';

                v2 = document.getElementById("boardcontentview" + boardNo);
                e2 = document.getElementById("boardcontentedit" + boardNo);

                editlink = document.getElementById("boardeditlink" + boardNo);
                updatelink = document.getElementById("boardupdatelink" + boardNo);
                cancellink = document.getElementById("boardcancellink" + boardNo);

                editlink.style.display = edit ? 'none' : 'inline';
                updatelink.style.display = edit ? 'inline' : 'none';
                cancellink.style.display = edit ? 'inline' : 'none';
            }
        }

        function doDelete(boardNo) {
            var yes = confirm(boardNo + '번 글을 삭제할까요?');
            if (yes) {
                location.href = 'delete.action?boardno=' + boardNo;
            }
        }
    </script>
</head>


<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<!-- ---------------  글쓰기 영역  ------------------------->
<div id="pageContainer">
    <div style="padding-top: 25px; text-align: center">
        <div id="inputcontent">
            <div id="inputmain">


                <form action="write.action" method="POST" enctype="multipart/form-data">
                    <%
                        Board board = (Board) request.getAttribute("board");
                    %>
                    <input type='hidden' name="boardno" value="${board.boardNo}"/>

                    <table border="1" align="center">
                        <tr>
                            <th>작성자</th>
                            <td>${loginuser.name}</td>
                        </tr>
                        <tr>
                            <th>첨부파일</th>
                            <td>
                                <input type="file" name="attach" style="height: 25px"/>
                            </td>
                        </tr>
                        <tr>
                            <th>내용</th>
                            <td>
                                <textarea name="content" cols="77" rows="15"/>
                            </td>
                        </tr>
                    </table>

                    <div class="buttons">
                        <!-- input type='submit' 버튼과 같음 -->
                        <a href="javascript:
                                  document.forms[0].submit();">글쓰기</a>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <table border="1" align="center">
        <c:forEach var="board" items="${ boards }" varStatus="status">

            <tr style="background-color:white;height:25px">
                <td id="list-table-td">
                    <h4>${board.writer}</h4>
                    <div id="boardcontentview${board.boardNo}">
                            ${board.content}
                    </div>
                    <div id="boardcontentedit${board.boardNo}" style="display: none">
                        <form id="boardform" action="update.action" method="post">
                            <input type="hidden" name="boardno" value="${board.boardNo}"/>
                            <textarea name="boardupdatecontent" cols="77" rows="5">${board.content}</textarea>
                        </form>
                    </div>

                    <br/><br/>
                    <div class="row" align="center">
                        <div class="col-sm-12">
                            <div class="thumbnail">
                                <img class="img-responsive center-block" src='/team5/upload/1.jpg'/>
                                <c:forEach var="upload" items="${ uploads }" varStatus="status2">
                                    <c:if test="${ board.boardNo == upload.boardNo }">
                                        <div border='1' align='center'>
                                            <img src='/team5/upload/${upload.savedFileName}'
                                                 style="width:350px;height:350px"/>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="buttons">
                        <c:choose>
                            <c:when test="${loginuser.memberId == board.memberId}">
                                <a href='javascript:
                                                    doDelete(${board.boardNo})'>
                                    <span class="glyphicon glyphicon-trash"></span> 삭제</a>&nbsp;&nbsp;
                                <a id="boardeditlink${board.boardNo}"
                                   href='javascript:
                                                    toggleBoardStatus(${board.boardNo},true)'>
                                    <span class="glyphicon glyphicon-scissors"></span> 편집</a>&nbsp;&nbsp;
                                <a id="boardcancellink${board.boardNo}"
                                   href='javascript:
                                                    toggleBoardStatus(${board.boardNo},false)'
                                   style="display: none">
                                    <span class="glyphicon glyphicon-remove-circle"></span>취소</a>&nbsp;&nbsp;
                                <a id="boardupdatelink${board.boardNo}"
                                   href="javascript:
                                            document.getElementById('boardform').submit();"
                                   style="display: none">
                                    <span class="glyphicon glyphicon-ok-circle"></span>수정</a>&nbsp;&nbsp;
                            </c:when>
                            <c:otherwise></c:otherwise>
                        </c:choose>
                    </div>

                    <div>
                        <form id="commentform" action="writecomment.action" method="post">
                            <input type="hidden" name="boardno" value="${board.boardNo}"/>
                            <table id="list-table-commentForm">
                                <tr>
                                    <td style="width: 400px">
                                        <textarea name="content" style="width: 350px" rows="3"></textarea>
                                    </td>
                                    <td style="width: 50px; vertical-align: middle">
                                        <a style="text-decoration: none"
                                           href="javascript:
                                                    document.getElementById('commentform').submit();"> 댓글<br/>등록
                                        </a>
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </div>

                    <!-- comment 표시 영역 -->
                    <c:if test="${not empty board.comments}">
                        <table id="list-table-comment">
                            <c:forEach var="boardComment" items="${ board.comments }">
                                <tr>
                                    <td id="list-table-td-comment">
                                        <div id='commentview${boardComment.commentNo}'>
                                                ${boardComment.writer} &nbsp;&nbsp; [${boardComment.regDate}]
                                            <br/><br/>
                                            <span>
                                                <c:set var="res2" value="${fn:replace(boardComment.content, rn, br)}"/>
										        	${fn:replace(res2, space, nbsp)}
										    </span>
                                            <br/><br/>
                                            <c:set var="display" value=""/>
                                            <c:if test="${loginuser.memberId == boardComment.memberId}">
                                                <c:set var="display" value="block"/>
                                            </c:if>
                                            <c:if test="${loginuser.memberId != boardComment.memberId}">
                                                <c:set var="display" value="none"/>
                                            </c:if>

                                            <div style="display: ${display}">
                                                <a href="javascript:
                                                               toggleCommentStatus(${boardComment.commentNo}, true);">편집</a>&nbsp;
                                                <a href="javascript:
                                                               deleteComment(${boardComment.commentNo} , ${ board.boardNo })">삭제</a>
                                            </div>
                                        </div>

                                        <div id='commentedit${boardComment.commentNo}' style="display: none">
                                                ${boardComment.writer}&nbsp;&nbsp; [${boardComment.regDate}]
                                            <br/><br/>



                                            <form id="commenteditform${boardComment.commentNo}"
                                                  action="updatecomment.action" method="post">
                                                <input type="hidden" name="boardno" value="${ board.boardNo }"/>
                                                <input type="hidden" name="commentno" value="${boardComment.commentNo}"/>
                                                <textarea name="content" style="width: 350px" rows="3" maxlength="200">
                                                        ${boardComment.content}
                                                </textarea>
                                            </form>
                                            <br/><br/>
                                            <div>
                                                <!-- 위에있는 form의 id값을 이용해서 submit함 -->
                                                <a href="javascript:
                                                            document.getElementById('commenteditform${boardComment.commentNo}').submit();">수정</a>  &nbsp;
                                                <a href="javascript:
                                                            toggleCommentStatus(${boardComment.commentNo}, false);">취소</a>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        <br/><br/><br/>
                        <hr align="center" style="width: 500px;"/>
                    </c:if>
                    <br/>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>
