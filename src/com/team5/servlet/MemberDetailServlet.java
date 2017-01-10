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

import com.team5.dao.BoardDao;
import com.team5.dao.MemberDao;
import com.team5.dao.UploadDao2;
import com.team5.dto.Board;
import com.team5.dto.Member;
import com.team5.dto.UploadFile;

@WebServlet(value = "/memberdetail/detail.action")
public class MemberDetailServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//로그인 여부 확인 
		//-> 로그인 하지 않은 사용자는 로그인 화면으로 이동
		//아래 코드는 필터로 이동합니다.
//		HttpSession session = req.getSession();
//		Member member2 = (Member)session.getAttribute("loginuser");
//		if (member2 == null || //로그인 여부 
//			!member2.getUserType().equals("admin")) { //관리자 여부
//			resp.sendRedirect("/demoweb/account/loginform.action");
//			return;//아래 코드를 수행하지 못하도록 반환
//		}
		
		//1. 요청 데이터 읽기 (상세 정보를 표시할 사용자 아이디)
		//   아이디를 읽지 못하면 리스트로 이동
		String memberId = req.getParameter("memberid");
		if(memberId == null || memberId.length() == 0) {
			resp.sendRedirect("/team5/member/list.action");
			return;
		}
		
		//2. 사용자 아이디로 사용자 정보 조회 (DAO)
		MemberDao dao = new MemberDao();
		Member member = dao.selectMemberById(memberId);		
		
		req.setAttribute("member", member);
		// 업로드된 모든 파일들의 목록을 가져온다.
	
		int iMemberId = Integer.parseInt(memberId);
		
		UploadDao2 dao1 = new UploadDao2();
		List<Board> boards = dao1.selectBoardList(iMemberId);
		//3. 조회된 데이터를 jsp에서 사용하도록 Request에 저장
		req.setAttribute("boards", boards);	
		
		// 업로드된 모든 파일들의 목록을 가져온다.
		List<UploadFile> uploadfiles = dao1.selectUploadfileList(); 
		req.setAttribute("uploadfiles", uploadfiles); 
		
		BoardDao dao2 = new BoardDao();
//		List<Board> boards = dao.selectBoardList();
		List<Board> boards2 = dao2.selectBoardList();
		

//		//3. 조회된 데이터를 jsp에서 사용하도록 Request에 저장
		req.setAttribute("boards2", boards2);
		
		//3. 조회된 정보 표시
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher(
					"/WEB-INF/views/member/detail.jsp");
		dispatcher.forward(req, resp);
		

	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
