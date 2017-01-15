<%@ page import="com.team5.dto.*" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
    <link rel="Stylesheet" href="/team5/styles/default.css"/>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"/>
    <script src="http://googledrive.com/host/0B-QKv6rUoIcGREtrRTljTlQ3OTg"/>
    <!-- ie10-viewport-bug-workaround.js -->
    <script src="http://googledrive.com/host/0B-QKv6rUoIcGeHd6VV9JczlHUjg"/>
    <meta charset="utf-8"/>
    <title>사용자 정보</title>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <script src="http://code.jquery.com/jquery.js"/>


    <script type="text/javascript">
        var proxy = null;

        function changprofilepic() {
            // proxy readyState code: 1:pre-request, 2:post-request, 3:pre-respond, 4:post-respond
            // proxy status code: 200 success
            if (proxy.readyState == 4) {
                if (proxy.status == 200) {
                    //읽은 데이터를 배분
                    var result = proxy.responseText;
                    alert(result);
                    //eval : String to Code: "var a = 10" -> var a = 10;
                    document.getElementById('profilepic').src = '/team5/upload/' + result;
                    //JQuery $(selector)과 비슷한 기능: '(inline tage, class)#profilepic'인 모든 요소를 List로 Return
                    var list = document.querySelectorAll(".profilepic");
                    for (var i in list) {
                        list[i].src = '/team5/upload/' + result;
                    }
                } else {
                    alert('오류 :' + proxy.status);
                }
            }
        }

        function doUpdatePicture(memberId, fileName) {

            //location.href='/team5/member/updateprofilepic.action?
            //               memberid=${loginuser.memberId}&profilepic=${uploadfile.savedFileName}';

            proxy = new XMLHttpRequest();//Ajax
            proxy.open("GET", "/team5/member/updateprofilepic.action?memberid=" + memberId + "&profilepic=" + fileName, true);
            proxy.onreadystatechange = changprofilepic;
            proxy.send(null);
        }


        function changeImage(from) {
            //소스만 바꿔주면 됨.
            var cover = document.getElementById("cover");
            cover.src = from.src;
        }

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
                    'commentno=' + commentNo + "&boardno=" + boardNo;
            }
        }

        function doDelete(boardNo, pageNo) {
            yes = confirm(boardNo + '번 글을 삭제할까요?');
            if (yes) {
                location.href = 'delete.action?' +
                    'boardno=' + boardNo + '&pageno=' + pageNo;
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
            }
            v2 = document.getElementById("boardcontentview" + boardNo);
            e2 = document.getElementById("boardcontentedit" + boardNo);

            editlink = document.getElementById("boardeditlink" + boardNo);
            updatelink = document.getElementById("boardupdatelink" + boardNo);
            cancellink = document.getElementById("boardcancellink" + boardNo);

            v2.style.display = edit ? 'none' : 'block';
            e2.style.display = edit ? 'block' : 'none';

            editlink.style.display = edit ? 'none' : 'inline';
            updatelink.style.display = edit ? 'inline' : 'none';
            cancellink.style.display = edit ? 'inline' : 'none';
        }


        function doDelete(boardNo) {
            var yes = confirm(boardNo + '번 글을 삭제할까요?');
            if (yes) {
                location.href = 'delete.action?' +
                    'boardno='+ boardNo;
            }
        }

    </script>
</head>



<body>

<jsp:include page="/WEB-INF/views/include/header.jsp"/>

<div id="detail-body-div" >
    <table border="1" align="center">
        <div>회원기본정보</div>

        <div>
            <button type="button" data-toggle="modal" data-target="#myModal">
                <img id="cover"/>
            </button>
            <!-- 모달 팝업 -->
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
                 aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">

                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">
                                <span aria-hidden="true">×</span><span class="sr-only">Close</span>
                            </button>

                            <h4 class="modal-title" id="myModalLabel">${ loginuser.name } 사진첩에서 선택하기</h4>
                        </div>


                        <c:forEach var="board" items="${ boards }" varStatus="status">
                            <c:forEach var="uploadfile" items="${ uploadfiles }" varStatus="status">
                                <c:choose>
                                    <c:when test="${ board.boardNo eq uploadfile.boardNo }">
                                        <button type="button" class="btn btn-default" data-dismiss="modal"
                                                onclick="doUpdatePicture(${loginuser.memberId}, '${uploadfile.savedFileName}');">
                                            <img style='width: 50px; height: 50px'
                                                 src='/team5/upload/${ uploadfile.savedFileName}'
                                                 onclick="changeImage(this);" id="cover1"/>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:forEach>


                    </div>
                </div>
            </div>
        </div>

        <div id="detail-body-div-table-div">
            <input type="button" class="btn btn-primary" value="타임라인" id="memberTimeline"
                   onclick="location.href='#'">
            <input type="button" class="btn btn-primary" value="정보" id="memberInfo"
                   onclick="location.href='/team5/member/memberinfo.action?memberid=${loginuser.memberId}'">
            <input type="button" class="btn btn-primary" value="사진" id="memberEmail"
                   onclick="location.href='/team5/member/lbums.action?memberid=${loginuser.memberId}'">
        </div>
    </table>

    <c:forEach var="board2" items="${ boards2 }" varStatus="status">
        <c:choose>
            <c:when test="${ loginuser.memberId eq board2.memberId }">
                <div style="padding-top: 25px; text-align: center">
                    <table border="1" align="center">
                        <tr style="background-color: white; height: 25px">
                            <td style="width: 400px; height: 500px; text-align: left; padding-left: 5px">

                                <h4>
                                    <img style='width: 50px; height: 50px' class="profilepic" src='/team5/upload/${ loginuser.profile_pic}'/>
                                        ${board2.writer}
                                </h4>
                                <div id="boardcontentview${board2.boardNo}">
                                        ${board2.content}
                                </div>

                                <%--  여기서 Board 내용 및 이미지 업로드 가져오고 update할 수 있음.
                                       (Servlet의 doPost로 가면, doGet로 인도하기 때문에. 뭘 별 상관은 없음)
                                    1. BoardUpdateServlet으로 이동@WebServlet(value='board/update.action')

                                    (문제점
                                     1. 이건 boardform인데, member folder안에 있음: 잘 못 위치함.
                                     2. update.action이 2개 있음: member/update.action, board/update.action
                                        member folder안에 있어서 member/update.action이지만, board/update.action을 용케 찾아간듯)


                                     input hidden으로 현재의 boardno, content, memebrId(session에서 get) parameter로 전달

                                     Edit button 누르면, board에 새로운 boardNo, content를 update하고,
                                     BoardDao의 updateBoard()로 이동 (새로 넣을 값을 가지고): SQL문 실행해서 DB 업데이트

                                     Servlet으로 돌아오면 .jsp 아니라 boardNo가지고 list.action(BoardListServlet)으로 이동
                                     여기서는 UPloadFile, Board, Member에 접근해서 리스트 만들어주고 list.jsp로 타임라인으로 이동

                                     --%>

                                <form id="boardform" action="update.action" method="post">
                                    <input type="hidden" name="boardno" value="${board2.boardNo}"/>
                                    <div id="boardcontentedit${board2.boardNo}" style="display: none">
							    	    <textarea name="boardupdatecontent" style="width: 400px; height: 500px">${board2.content}</textarea>
                                    </div>

                                    <br/><br/>
                                    <div border='1' style='width: 50px; height: 50px'>
                                        <c:forEach var="uploadfiles" items="${ uploadfiles }" varStatus="status">
                                            <c:choose>
                                                <c:when test="${ board2.boardNo eq uploadfiles.boardNo }">
                                                    <a href="/team5//board/detail.action?boardno=${board2.boardNo}">
                                                        <img style='width: 50px; height: 50px' id="profilepic"
                                                             src='/team5/upload/${ uploadfiles.savedFileName}'/></a>
                                                </c:when>
                                                <c:otherwise>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </div>
                                </form>


                            </td>
                        </tr>
                    </table>
                    <div class="buttons">


                        <c:choose>
                            <c:when test="${loginuser.memberId == board2.memberId}">
                                <a href='javascript:
                                            doDelete(${board2.boardNo})'>삭제</a>&nbsp;&nbsp;
                                <a id="boardeditlink${board2.boardNo}" href='javascript:
                                            toggleBoardStatus(${board2.boardNo},true)'>편집</a>&nbsp;&nbsp;
                                <a id="boardcancellink${board2.boardNo}" style="display: none" href='javascript:
                                            toggleBoardStatus(${board2.boardNo},false)'>취소</a>&nbsp;&nbsp;
                                <a id="boardupdatelink${board2.boardNo}" style="display: none" href="javascript:
                                            document.getElementById('boardform').submit();"  >수정</a>&nbsp;&nbsp;
                            </c:when>

                            <c:otherwise>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>


                <c:if test="${empty board2.comments}">
                    <h4 id="nodata" style="text-align: center">작성된 댓글이 없습니다.</h4>
                </c:if>


                <c:if test="${not empty board2.comments}">
                    <!-- comment 표시 -->
                    <table style="width: 400px; border: solid 1px; margin: 0 auto">
                        <c:forEach var="boardComment" items="${ board2.comments }">
                            <tr>
                                <td style="text-align: left; margin: 5px; border-bottom: solid 1px">
                                    <div id='commentview${boardComment.commentNo}'>
                                            ${boardComment.writer} &nbsp;&nbsp; [${boardComment.regDate}]
                                                     <br/><br/>
                                           <span>
                                                <c:set var="res2" value="${fn:replace(boardComment.content, rn, br)}"/>
									                   ${fn:replace(res2, space, nbsp)}
                                           </span>
                                             <br/> <br/>

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
                                                        deleteComment(${boardComment.commentNo}, ${ board2.boardNo })">삭제</a>
                                        </div>
                                    </div>



                                    <div id='commentedit${boardComment.commentNo}' style="display: none">
                                            ${boardComment.writer}&nbsp;&nbsp;[${boardComment.regDate}]
                                     <br/><br/>


                                     <%--



                                     A. GET/Null Check/Parsing
                                     1. BoardUpdateCommentServlet(@WebServlet = updatecomment.action)로 이동
                                     2. parameter (boardno, commentno) doPost()에서 doGet으로 인도됨
                                     3. request.getParameter("boardNo");의 parameter는 <input>의 name으로 value를 가져옴
                                     4. String bNo으로 받고 이 if(String이 null 이거나 .length == 0 인지),
                                     5. true이면 전체 list.action으로 redirect
                                        else는 String으로 받은 BoardNo를 Integer.parseInt()로 parsing

                                     B. DTO/SET
                                     6. BoardComment 객체에 setComent, setContent

                                     C. DAO/UPDATE SQL
                                     7. updateComment: connectionHelper/SQL =  ? ?/
                                     8. PreparedStatement = conn.preparedStatement(1, board.getContent());
                                     9. psttry/catch-> pstmt.close(), conn.close()

                                     D. REDIRECT
                                     10. iboardNo 가지고 list.action(BoardListServlet)으로 이동


                                     --%>




                                        <form id="commenteditform${boardComment.commentNo}"
                                              action="updatecomment.action" method="post">
                                            <input type="hidden" name="boardno" value="${ board2.boardNo }"/>
                                            <input type="hidden" name="commentno" value="${boardComment.commentNo}"/>
                                            <textarea name="content" style="width: 350px" rows="3" maxlength="200">
                                                    ${boardComment.content}
                                            </textarea>
                                        </form>
                                         <br/><br/>

                                        <div>
                                            <a href="javascript:
                                                          document.getElementById('commenteditform${boardComment.commentNo}').submit();">수정</a> &nbsp;
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
            </c:when>
        </c:choose>
    </c:forEach>
</div>
</body>
</html>