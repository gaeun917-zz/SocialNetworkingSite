package com.team5.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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


@WebServlet("/memberdetail/writecomment.action")
public class memberWriteCommentServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
						throws ServletException, IOException {

		//1. get data (baordNo, content, loginuser)
		String boardNo = request.getParameter("boardno");
		if(boardNo == null || boardNo.length() == 0){
			response.sendRedirect("list.action");
			return;
		}
		int iBoardNo = Integer.parseInt(boardNo);
		//int pageNo = (int)request.getAttribute("pageno");
		String content = request.getParameter("content");
		Member member = (Member)request.getSession().getAttribute("loginuser");

		//2. set data
		BoardComment comment = new BoardComment();
					comment.setBoardNo(iBoardNo);
					comment.setContent(content);
					comment.setWriter(member.getName());// get name, memberId from session's loginuser
					comment.setMemberId(member.getMemberId());

		//3. insert data into DB
		BoardDao dao = new BoardDao();
				dao.insertComment(comment);

		//4. forward(request) to jsp
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/views/member/detail.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		doGet(req, resp);
	}
	
}






