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
import com.team5.dao.MemberDao;
import com.team5.dao.UploadDao;
import com.team5.dao.UploadDao2;
import com.team5.dto.Board;
import com.team5.dto.BoardComment;
import com.team5.dto.Member;
import com.team5.dto.Upload;
import com.team5.dto.UploadFile;


@WebServlet("/memberdetail/friendwritecomment.action")
public class FriendBoardWriteCommentServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		//1. get data
		//1.1 boardNo
		String boardNo = request.getParameter("boardno");
		if(boardNo==null && boardNo.length()==0){
			response.sendRedirect("list.action");//없으면 목록으로 이동
			return;
		}
		int iBoardNo = Integer.parseInt(boardNo);

		//1.2 memebr(loginuser,memeberId)
		Member member1 = (Member)request.getSession().getAttribute("loginuser");
		String memberId = request.getParameter("memberid");
		int iMemberId = Integer.parseInt(memberId);

		//1.3 content
		String content = request.getParameter("content");

		//1.4 save it to comment
		BoardComment comment = new BoardComment();
					comment.setBoardNo(iBoardNo);
					comment.setWriter(member1.getName());
					comment.setMemberId(member1.getMemberId());
					comment.setContent(content);
			
		//2. set data(insert)
		BoardDao dao = new BoardDao();
		dao.insertComment(comment);

		//2.1 UploadDao
		UploadDao2 dao1 = new UploadDao2();
		List<Board> boards = dao1.selectBoardList(iMemberId);
		request.setAttribute("boards", boards);
			
		List<UploadFile> uploadfiles = dao1.selectUploadfileList();
		request.setAttribute("uploadfiles", uploadfiles);

		//2.2 BoardDao
		BoardDao dao2 = new BoardDao();
		List<Board> boards2 = dao2.selectBoardList();
		request.setAttribute("boards2", boards2);

		//2.3 MemberDao
		MemberDao dao3 = new MemberDao();
		Member member = dao3.selectMemberById(memberId);
		request.setAttribute("member", member);

		//3. redirect
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("/WEB-INF/views/member/frienddetail.jsp");
		dispatcher.forward(request, response);
			
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		doGet(req, resp);
	}
}






