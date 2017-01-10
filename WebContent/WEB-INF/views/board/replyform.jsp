<%@page import="com.team5.dto.Board"%>
<%@page import="com.team5.dto.Member"%>




<%@ page language="java" contentType="text/html;charset=utf-8"pageEncoding="utf-8"%>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

<!DOCTYPE html>

<html>
<head>
	<meta charset="utf-8" />
	<title>글 수정</title>
	<link rel="Stylesheet" href="/demoweb/styles/default.css" />
	<link rel="Stylesheet" href="/demoweb/styles/input2.css" />	
</head>
<body>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<div id="pageContainer">
	
	
	
		<div style="padding-top:25px;text-align:center">
		<div id="inputcontent">
		    <div id="inputmain">
		        <div class="inputsubtitle">수정하기</div>
		        <!-- edit.action 아님 -->
		        <form action="reply.action" 
		        	  method="post">
		        	  <% Board board = (Board)request.getAttribute("board"); %>
		        	  <input type='hidden' name="boardno" value= "${board.boardNo}"/>
		        	  		        <table>
		            <tr>
		                <th>제목</th>
		                <td>
		                    <input type="text" name="title" style="width:480px" value=' Re:${board.title}'/>
		                </td>
		            </tr>
		            <tr>
		                <th>작성자</th>
		                <td>
		                	<%= ((Member)session.getAttribute("loginuser")).getMemberId() %>
		                </td>
		            </tr>
		           <!--  <tr>
		                <th>첨부파일</th>
		                <td>
		                	<input type="file" name="file1" style="height: 25px" />
		                </td>
		            </tr> -->
		            <tr>
		                <th>내용</th>
		                <td>		                    
		                    <textarea 
		                    	name="content" cols="76" rows="15">
<!-- ====대상 글의 내용==== -->

${board.content}</textarea>
		                </td>
		            </tr>
		        </table>
		        <div class="buttons">
		        	<!-- 아래 a 링크는 input type='submit' 버튼을 누르는 효과 발생 -->		        	
		        	<a href="javascript:document.forms[0].submit();">글쓰기</a>
		        	&nbsp;&nbsp;
		        	<a href='detail.action?boardno=${board.boardNo}&pageno=${requestScope.pageNo}'>취소</a>
		        </div>
		        </form>
		    </div>
		</div>   	
	
	</div>
	</div>

</body>
</html>