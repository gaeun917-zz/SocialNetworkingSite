<%@ page import="com.team5.dto.*"%>
<%@ page import="java.util.List"%>
<%@ page language="java" contentType="text/html;charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!DOCTYPE html>
<html>
<head>
<link rel="Stylesheet" href="/team5/styles/default.css" />
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
<script src="http://googledrive.com/host/0B-QKv6rUoIcGREtrRTljTlQ3OTg"></script>
<!-- ie10-viewport-bug-workaround.js -->
<script src="http://googledrive.com/host/0B-QKv6rUoIcGeHd6VV9JczlHUjg"></script>
<meta charset="utf-8" />
<title>사용자 정보</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
<script type="text/javascript">
	function changeImage(from) {
		var cover = document.getElementById("cover");
		//var cover1 =document.getElementById("cover1");
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
			location.href = 'deletecomment.action?commentno='
					+ commentNo + "&boardno=" + boardNo;
		}
	}
	function doDelete(boardNo, pageNo) {
    	yes = confirm(boardNo + '번 글을 삭제할까요?');
    	if (yes) {
    		location.href = 'delete.action?boardno=' + boardNo + '&pageno=' + pageNo;
    	}
    }
	
/////////////////////////////////////////
	var v2= null; 
	var e2=null;
	var editlink =null;
	var updatelink =null;
	var cancellink=null;
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
		yes = confirm(boardNo + '번 글을 삭제할까요?');
		if (yes) {
			location.href = 'delete.action?boardno='
					+ boardNo ;
		}
	}
	
</script>
</head>

<body>

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

	<div style="padding-top: 25px; text-align: center; align: center">
		<table border="1" align="center">

			<div>회원기본정보</div>


			<div>
				<!-- 버튼 -->
				<button type="button" data-toggle="modal" data-target="#myModal">
					<img id="cover" />
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
								<h4 class="modal-title" id="myModalLabel">♡${ loginuser.name }♡
									사진첩에서 선택하기</h4>
							</div>

							<c:forEach var="board" items="${ boards }" varStatus="status">


								<c:forEach var="uploadfile" items="${ uploadfiles }"
									varStatus="status">
									<c:choose>
										<c:when test="${ board.boardNo eq uploadfile.boardNo }">
											<button type="button" class="btn btn-default"
												data-dismiss="modal">
												<img style='width: 50px; height: 50px'
													src='/team5/upload/${ uploadfile.savedFileName}'
													onclick="changeImage(this)" id="cover1" />
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



			<div style="padding-top: 25px; text-align: center; align: center">
				<ul>
					<li><a href="#">타임라인</a></li>
					<li><a
						href="/team5/member/memberinfo.action?memberid=${loginuser.memberId}">정보</a></li>
					<li><a href="#">친구</a></li>
					<li><a
						href="/team5/member/lbums.action?memberid=${loginuser.memberId}">사진</a></li>
				</ul>
				</div>
		</table>
	



	<%----------------------------------------------------------------------------------------------------------- --%>
	
	<c:forEach var="board2" items="${ boards2 }" varStatus="status">
	<c:choose>
		<c:when test="${ loginuser.memberId eq board2.memberId }">
		<div style="padding-top: 25px; text-align: center">

			<table border="1" align="center">
				<tr style="background-color: white; height: 25px">
					<td
						style="width: 400px; height: 500px; text-align: left; padding-left: 5px">
						<h4>${board2.writer}</h4>
						<div id="boardcontentview${board2.boardNo}">
							${board2.content}</div>

						<form id="boardform" action="update.action" method="post">
							<input type="hidden" name="boardno" value="${board2.boardNo}" />
							<div id="boardcontentedit${board2.boardNo}" style="display: none">
								<textarea name="boardupdatecontent"
									style="width: 400px; height: 500px">${board2.content}</textarea>
							</div>
							<br /> <br />
							<div border='1' style='width: 50px; height: 50px'>
								<!-- <img src='http://localhost:8080/WEB-INF/upload/1.jpg' /> -->
							</div>
						</form>

					</td>
				</tr>
			</table>

			<div class="buttons">


				<c:choose>
					<c:when test="${loginuser.memberId==board2.memberId}">
						<a href='javascript:doDelete(${board2.boardNo})'>삭제</a>&nbsp;&nbsp;
							<a id="boardeditlink${board2.boardNo}"
							href='javascript:toggleBoardStatus(${board2.boardNo},true)'>편집</a>&nbsp;&nbsp;
							<a id="boardcancellink${board2.boardNo}"
							href='javascript:toggleBoardStatus(${board2.boardNo},false)'
							style="display: none">취소</a>&nbsp;&nbsp;
							<a id="boardupdatelink${board2.boardNo}"
							href="javascript:document.getElementById('boardform').submit();"
							style="display: none">수정</a>&nbsp;&nbsp;
						</c:when>

					<c:otherwise></c:otherwise>
				</c:choose>
			</div>
		</div>

		<div>
			<form id="commentform" action="/memberdetail/writecomment.action" method="post">
				<input type="hidden" name="boardno" value="${board2.boardNo}" />

				<table style="width: 400px; border: solid 1px; margin: 0 auto">
					<tr>
						<td style="width: 400px"><textarea name="content"
								style="width: 350px" rows="3"></textarea></td>
						<td style="width: 50px; vertical-align: middle"><a
							href="javascript:document.getElementById('commentform').submit();"
							style="text-decoration: none"> 댓글<br />등록
						</a></td>
					</tr>
				</table>
				
				
			</form>
		</div>


		<!-------------------------------------------------------->

		<c:if test="${empty board2.comments}">
			<h4 id="nodata" style="text-align: center">작성된 댓글이 없습니다.</h4>
		</c:if>

		<c:if test="${not empty board2.comments}">
			<!-- comment 표시 영역 -->


			<table style="width: 400px; border: solid 1px; margin: 0 auto">

				<c:forEach var="boardComment" items="${ board2.comments }">
					<tr>
						<td
							style="text-align: left; margin: 5px; border-bottom: solid 1px">

							<div id='commentview${boardComment.commentNo}'>
								${boardComment.writer} &nbsp;&nbsp; [${boardComment.regDate}] <br />
								<br /> <span> <c:set var="res2"
										value="${fn:replace(boardComment.content, rn, br)}" />
									${fn:replace(res2, space, nbsp)}
								</span> <br /> <br />

								<c:set var="display" value="" />
								<c:if test="${loginuser.memberId==boardComment.memberId}">
									<c:set var="display" value="block" />
								</c:if>
								<c:if test="${loginuser.memberId!=boardComment.memberId}">
									<c:set var="display" value="none" />
								</c:if>

								<div style="display: ${display}">
									<a
										href="javascript:toggleCommentStatus(${boardComment.commentNo}, true);">편집</a>
									&nbsp; <a
										href="javascript:deleteComment(${boardComment.commentNo}, ${ board2.boardNo })">삭제</a>
								</div>
							</div>

							<div id='commentedit${boardComment.commentNo}'
								style="display: none">
								${boardComment.writer}&nbsp;&nbsp; [${boardComment.regDate}] <br />
								<br />

								<form id="commenteditform${boardComment.commentNo}"
									action="updatecomment.action" method="post">
									<input type="hidden" name="boardno" value="${ board2.boardNo }" />
									<input type="hidden" name="commentno"
										value="${boardComment.commentNo}" />
									<textarea name="content" style="width: 350px" rows="3"
										maxlength="200">
								${boardComment.content}
								</textarea>
								</form>

								<br />
								<div>
									<a
										href="javascript:document.getElementById('commenteditform${boardComment.commentNo}').submit();">수정</a>
									&nbsp;
									<!-- 위에있는 form의 id값을 이용해서 submit함 -->
									<a
										href="javascript:toggleCommentStatus(${boardComment.commentNo}, false);">취소</a>
								</div>
							</div>

						</td>
					</tr>
				</c:forEach>
			</table>
			<br />
			<br />
			<br />
			<hr align="center" style="width: 500px;" />
		</c:if>
		<br />
		
		</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
		
	</c:forEach>


	</div>
</body>
</html>