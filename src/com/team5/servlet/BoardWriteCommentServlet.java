package com.team5.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.team5.common.Util;
import com.team5.dao.BoardDao;
import com.team5.dao.UploadDao;
import com.team5.dto.Board;
import com.team5.dto.BoardComment;
import com.team5.dto.Member;
import com.team5.dto.Upload;
import com.team5.dto.UploadFile;


@WebServlet("/board/writecomment.action")
public class BoardWriteCommentServlet extends HttpServlet {

	@Override
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// 코멘트를 데이터 베이스에 저장하고, 이걸 화면에 구현하는게 어려움 
		//1. 데이터 읽기
			String boardNo = request.getParameter("boardno");
			if(boardNo==null && boardNo.length()==0){
				response.sendRedirect("list.action");//없으면 목록으로 이동
				return;
			}
			// 입력으로 넘어옴 
			int iBoardNo = Integer.parseInt(boardNo);
			//int pageNo = (int)request.getAttribute("pageno");
			String content = request.getParameter("content");
			//작성자
			Member member = (Member)request.getSession().getAttribute("loginuser");	
			BoardComment comment = new BoardComment();
			comment.setWriter(member.getName());
			comment.setMemberId(member.getMemberId());
			comment.setBoardNo(iBoardNo);
			comment.setContent(content);
			
						
			//2. 데이터 insert 
			BoardDao dao = new BoardDao();
			dao.insertComment(comment);
			//3. detail로 이동 

			response.sendRedirect("list.action?boardno=" +boardNo);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		doGet(req, resp);
	}
	
}






