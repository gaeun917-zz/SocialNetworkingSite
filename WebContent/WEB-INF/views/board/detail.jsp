<%@page import="com.team5.dto.Board"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8" session="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>

<html>
<head>

<meta charset="utf-8" />
<title>글쓰기</title>


</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
	<div id="pageContainer">

		
		<div style="padding-top: 25px; text-align: center">
			<div id="inputcontent">
				<div id="inputmain">
					<div class="inputsubtitle">게시판 글 내용</div>

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
							<td style="height: 200px; vertical-align: top" >${fn:replace(board.content, rn, br)}</td>
							<c:set var="rn" value="
" />
							<c:set var="br" value="<br>" />
							

						</tr>
					</table>

					<div class="buttons">
						<script type="text/javascript">
							function doDelete(boardNo) {
								yes = confirm(boardNo + '번 글을 삭제할까요?');
								if (yes) {
									location.href = 'delete.action?boardno='
											+ boardNo ;
								}
							}
						</script>
						<!--로그인한 사용자는 request가 아니라 session에 들어 있음   -->


						<c:choose>
							<c:when test="${loginuser.memberId == board.writer}">
								<a
									href='javascript:doDelete(${board.boardNo},${requestScope.pageNo})'>
									삭제</a>&nbsp;&nbsp;
					<a
									href='edit.action?boardno=${board.boardNo}&pageno=${requestScope.pageNo}'>수정
								</a>&nbsp;&nbsp;
				</c:when>

							<c:otherwise>
								<a
									href='replyform.action?boardno=${board.boardNo}&pageno=${requestScope.pageNo}'>댓글쓰기</a>&nbsp;&nbsp;
					<a href='list.action?pageno=${requestScope.pageNo}'>목록보기</a>
							</c:otherwise>
						</c:choose>


					</div>
				</div>
			</div>

			<br />
			<br />



			<!------------------ comment 쓰기 영역 시작 -------------------->

			<form id="commentform" action="writecomment.action" method="post">
				<input type="hidden" name="boardno" value="${board.boardNo}" /> <input
					type="hidden" name="pageno" value="${requestScope.pageNo}" />
				<!-- getAttribute: 서버에서 사용하는 데이터 getParameter: 브라우저가 전송하는 데이터 -->
				<table style="width: 300px; border: solid 1px; margin: 0 auto">
					<tr>
						<td style="width: 550px"><textarea name="content"
								style="width: 550px" rows="3"> Comment는 여기에 써주세요!</textarea></td>
						<td style="width: 50px; vertical-align: middle"><a
							href="javascript:document.getElementById('commentform').submit();"
							style="text-decoration: none"> 댓글등록</a>
							</td>
					</tr>
				</table>
			</form>

			<hr align="center" style="width: 600px;" />

			<!--------------------------------------------------------------------->

			<c:choose>
				<c:when test="${ empty board.comments }">
					<h4 id="nodata" style="text-align: center">작성된 댓글이 없습니다.</h4>
				</c:when>
				<c:otherwise>
					<!-------------- comment 표시 영역 div 1에서는 board내용 div2에서는 커멘트 -------------------------------------->


					<script type="text/javascript">
						var v = null, e = null;
						function toggleCommentStatus(commentNo, edit) {
							// view와 edit 모두에 commentNo가 붙어 있음 		
							if (v != null) {
								v.style.display = edit ? 'block' : 'none';
								e.style.display = edit ? 'none' : 'block';
							}
							v = document.getElementById("commentview"
									+ commentNo);
							e = document.getElementById("commentedit"
									+ commentNo);

							v.style.display = edit ? 'none' : 'block';
							e.style.display = edit ? 'block' : 'none';

						}

						//삭제하고 페이지 넘버 리프레쉬 할때 사용할거임- 삭제할까요? 메세지 보여주고, 넘어가면 404 
						function deleteComment(commentNo, boardNo) {
							var yes = confirm(commentNo + '번 댓글을 삭제할까요?');
							if (yes) {
								// location.href : 주소창에 입력해서 enter한것과 같은 기능 
								location.href = 'deletecomment.action?commentno='
										+ commentNo
										+ "&boardno="
										+ boardNo;
										
							}
						}
						
						
					</script>
					<table style="width: 100px; border: solid 1px; margin: 0 auto">
						<c:forEach var="bcomment" items="${board.comments}">
							<tr>
								<td
									style="text-align: left; margin: 5px; border-bottom: solid 1px">
									<!-- 코멘트 뷰에 아이디로 식별값을 넣음 -->
									<div id='commentview ${bocomment.commentNo}'>
										${bcomment.writer} &nbsp;&nbsp; [ ${bcomment.regDate}] 
										<br /><br /> 
										<span> 
										${bcomment.content}
										</span> 
										<br /><br />

										<c:choose>
											<c:when test="${ loginuser.memberId == bcomment.memberId }">
												<c:set var="display" value="${ block }" />
											</c:when>
											<c:otherwise>
												<c:set var="display" value="${ none }" />
											</c:otherwise>
										</c:choose>
										<%-- <div style="display: <%=display%>"> --%>
										<!--toggleCommentStatus: 편집: 커멘트 번호를 넘기고, true  -->
										<div style="display: ${display}" >
										<a href="javascript:toggleCommentStatus(${bcomment.commentNo}, true);">편집</a>
										&nbsp;
<!--toggleCommentStatus 라는 자바스크립트 function이 돌고 있음: 커멘트 번호, 보드넘버, 페이지 넘버(getAttribute)를 가져옴   -->
										<a href="javascript:deleteComment(${bcomment.commentNo}, ${bcomment.boardNo})">삭제</a>
									</div>
									</div> 
									
									<!-- 식별값 주고, display none  -->
									<div id='commentedit${bcomment.commentNo}'style="display: none">
										${bcomment.writer} &nbsp;&nbsp; [${bcomment.regDate} ] <br />
										<br />
										<form id="commenteditform${bcomment.commentNo}" action="updatecomment.action" method="post">
											<input type="hidden" name="boardno" value="${board.boardNo}" /> 
											<input type="hidden" name="commentno" value="${bcomment.commentNo}" />
											<textarea name="content" style="width: 600px" rows="3" maxlength="200">${bcomment.content}</textarea>
										</form>
										<br />
										<div>
											<a href="javascript:document.getElementById('commenteditform${bcomment.commentNo}').submit();">수정</a>
											&nbsp; 
											<a href="javascript:toggleCommentStatus(${bcomment.commentNo}, false);">취소</a>
										</div>
									</div>

								</td>
							</tr>
						</c:forEach>
					</table>
				</c:otherwise>
			</c:choose>


			<br />
			<br />
			<br />
			<br />
			<br />
		</div>
	</div>
</body>
</html>