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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		
		String memberId = req.getParameter("memberid");
		int iMemberId = Integer.parseInt(memberId);
		
		UploadDao2 dao = new UploadDao2();
		List<Board> boards = dao.selectBoardList(iMemberId);
		
		//3. 조회된 데이터를 jsp에서 사용하도록 Request에 저장
		req.setAttribute("boards", boards);	
		
		// 업로드된 모든 파일들의 목록을 가져온다.
		List<UploadFile> uploadfiles = dao.selectUploadfileList(); 
		req.setAttribute("uploadfiles", uploadfiles); 
		// 모든 리스트
		
		
		
		//4. 목록 보기 jsp로 forward
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher("/WEB-INF/views/member/lbums.jsp");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
