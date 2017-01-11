package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.BoardDao;
import com.team5.dao.MemberDao;
import com.team5.dao.UploadDao;
import com.team5.dao.UploadFileDao;
import com.team5.dto.Board;
import com.team5.dto.Member;
import com.team5.dto.Upload;
import com.team5.dto.UploadFile;
import com.team5.ui.ThePager;


@WebServlet("/board/list.action")
public class BoardListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {
		
		
		//1. 로그인 여부 확인 (로그인 하지 않은 사용자는 로그인 화면으로 redirect))
//		if (req.getSession().getAttribute("loginuser") == null) {
//			resp.sendRedirect(
//				"/team5/account/loginform.action?returnurl=" + req.getRequestURI());
//			return;
//		}
		
//		//1.2 요청된 페이지 번호 읽기
//			String pageNo = req.getParameter("pageno");
//			int currentPage =(int)req.getAttribute("pageno");
	//			if(pageNo != null && pageNo.length()>0){
	//				currentPage = Integer.parseInt(pageNo);
	//			}
//			req.setAttribute("pageno", currentPage);
//
//			int pageSize = 10; // list per view
//			int start= (currentPage - 1)*pageSize +1; 
//			int last = start+ pageSize;

		//2. 목록 데이터 조회
		BoardDao dao = new BoardDao();
		List<Board> boards = dao.selectBoardList();
		//3. 조회된 데이터를 Request에 저장
		req.setAttribute("boards", boards);

		UploadDao updao = new UploadDao();
		List<UploadFile> uploads = updao.selectAllUploadFiles();
		req.setAttribute("uploads", uploads);

		UploadFileDao updao2 = new UploadFileDao();
		List<Member> members = updao2.selectAllMembers();
		req.setAttribute("members", members);

//		 1.3 data counting (pager)
//		int total = dao.getBoardCount();// count board number
//		int pagerSize =3;
//		ThePager pager = new ThePager(total, currentPage, pageSize, pagerSize, "list.action");
//		req.setAttribute("pager", pager);

		//4. move to the list: jsp로 forward
		RequestDispatcher dispatcher =
				req.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		doGet(req, resp);
	}
}






