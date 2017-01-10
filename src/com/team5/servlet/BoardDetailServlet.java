package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team5.dao.BoardDao;
import com.team5.dao.MemberDao;
import com.team5.dao.UploadDao;
import com.team5.dto.Board;
import com.team5.dto.Member;
import com.team5.dto.Upload;

@WebServlet(value = "/board/detail.action")
public class BoardDetailServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//1. 요청 데이터 읽기 (상세 정보를 표시할 자료 번호)
		//   읽지 못하면 리스트로 이동
		String BoardNo = req.getParameter("boardno");
		if(BoardNo == null || BoardNo.length() == 0) {
			resp.sendRedirect("/demoweb/board/list.action");
			return;
		}
		//문자열을 숫자로 변경
		int iBoardNo = Integer.parseInt(BoardNo);	
		//BoardListServlet에서 페이지 번호 읽기 코드 copy&paste
	    // 요청된 페이지 번호 읽기 (없으면 1로 설정)
					String pageNo = req.getParameter("pageno");
					int currentPage = 1; //현재 페이지 번호 
					if(pageNo != null && pageNo.length()>0){
						currentPage = Integer.parseInt(pageNo);
					}
					
		//현재 페이지 번호를 request에 저장 -> jsp 사용 
					req.setAttribute("pageno", currentPage);
				
		//2. 자료 번호로 자료 정보 조회 (DAO)
		BoardDao dao = new BoardDao();
		Board b = dao.selectBoardByBoardNo(iBoardNo);
		
		//조회수 증가-> 화면 리프레쉬하면 조회수 증가 
		// jsp가 안나오기때문에 다운로드가 됨. 
//		dao.updateBoardReadCount(iBoardNo);
//		b.setReadCount(b.getReadCount()+1);

		dao.updateBoardread_cnt(iBoardNo);
		b.setReadCnt(b.getReadCnt()+1);
		/*dao.updateBoardReadCount(iBoardNo);
		b.setReadCount(b.getReadCount()+1);*/
		//3. 조회된 자료를 jsp에서 읽을 수 있도록 req에 저장
		
		req.setAttribute("board", b);
		
		//4. 조회된 정보 표시
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher(
					"/WEB-INF/views/board/detail.jsp");
		dispatcher.forward(req, resp);

	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
