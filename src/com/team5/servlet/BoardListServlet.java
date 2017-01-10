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
		
		
//  filter 에 저장되어있음 
//		// 요청된 페이지 번호 읽기 (없으면 1로 설정)
//			String pageNo = req.getParameter("pageno");
//			int currentPage = 1; //현재 페이지 번호 
//			if(pageNo != null && pageNo.length()>0){
//				currentPage = Integer.parseInt(pageNo);
//			}
//			
//			//현재 페이지 번호를 request에 저장 -> jsp 사용 
//			req.setAttribute("pageno", currentPage);
// 필터에 이 코드를 옮겨놨기때문에, 			
//			int currentPage =(int)req.getAttribute("pageno");
//			//			int pageSize = 10; // 한 페이지에 표시할 항목 갯수 
//			int start= (currentPage - 1)*pageSize +1; 
//			int last = start+ pageSize;
				String memberId = req.getParameter("memberid");
				
		        //2. 목록 데이터 조회
				BoardDao dao = new BoardDao();
//				List<Board> boards = dao.selectBoardList();
				List<Board> boards = dao.selectBoardList();
				
				
				UploadDao updao = new UploadDao();
				
				UploadFileDao updao2 = new UploadFileDao();
				List<UploadFile> uploads = updao.selectAllUploadFiles();
				
				MemberDao dao2 = new MemberDao();
				
				List<Member> members = updao2.selectAllMembers();		
				
				req.setAttribute("members", members);
				

//				//3. 조회된 데이터를 jsp에서 사용하도록 Request에 저장
				req.setAttribute("boards", boards);
				req.setAttribute("uploads", uploads);
//				
//				// 데이터 갯수
//				int total = dao.getBoardCount();// 전체 게시물 갯수 조회 
//				int pagerSize =3;
//				ThePager pager = new ThePager(total, currentPage, pageSize, pagerSize, "list.action");
//				req.setAttribute("pager", pager);
				//4. 목록 보기 jsp로 forward
				RequestDispatcher dispatcher = 
					req.getRequestDispatcher("/WEB-INF/views/board/list.jsp");
				dispatcher.forward(req, resp);
				
				//5.카운트
//				dao.getBoardCount();
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		doGet(req, resp);
	}
	
}






