package com.team5.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.BoardDao;
import com.team5.dto.Board;

@WebServlet("/board/edit.action")
public class BoardEditFormServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. 요청 데이터 읽기 (상세 정보를 표시할 자료 번호)
		String BoardNo = req.getParameter("boardno");
			if (BoardNo == null || BoardNo.length() == 0) {
				resp.sendRedirect("list.action");// 읽지 못하면 리스트로 이동
				return;
			}
			int iBoardNo = Integer.parseInt(BoardNo);//String to Interger


		//2. BoardNmber를 request에 저장
		BoardDao dao = new BoardDao();
		Board b = dao.selectBoardByBoardNo(iBoardNo);//DAO 접근 BoardNo로 해당보드 저장
		req.setAttribute("board", b);


		//3. redirect
		RequestDispatcher dispatcher =
				req.getRequestDispatcher("/WEB-INF/views/board/editform.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");// filter에서 해도되지 않나?
		doGet(req, resp);
	}

}
