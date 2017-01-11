package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team5.dao.MemberDao;
import com.team5.dao.UploadDao2;
import com.team5.dto.Board;
import com.team5.dto.Member;
import com.team5.dto.UploadFile;

@WebServlet(value = "/member/lbums.action")
public class MemberalbumsServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {

		//1. get data
		String memberId = req.getParameter("memberid");
		int iMemberId = Integer.parseInt(memberId);
		UploadDao2 dao = new UploadDao2();

		//2. set data(memeber id를 파라미터로 dao에 있는 method실행-> return 값을 set)
		List<Board> boards = dao.selectBoardList(iMemberId);
		req.setAttribute("boards", boards);

		List<UploadFile> uploadfiles = dao.selectUploadfileList();// 업로드된 모든 파일들의 목록을 가져온다.
		req.setAttribute("uploadfiles", uploadfiles);

		//3. redirect(jsp로 forward)
		RequestDispatcher dispatcher = 
						req.getRequestDispatcher("/WEB-INF/views/member/lbums.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		doGet(req, resp);
	}
}
