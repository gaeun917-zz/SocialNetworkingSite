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
import com.team5.dto.Board;

@WebServlet("/board/edit.action")
public class BoardEditFormServlet extends HttpServlet {

	// 2. 1의 글 번호로 데이터 조회(없으면 목록으로)
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 1. 요청 데이터 읽기 (상세 정보를 표시할 자료 번호)
		// 읽지 못하면 리스트로 이동
		String BoardNo = req.getParameter("boardno");
		if (BoardNo == null || BoardNo.length() == 0) {
			resp.sendRedirect("/demoweb/board/list.action");
			return;
		}
		
		// 문자열을 숫자로 변경
		int iBoardNo = Integer.parseInt(BoardNo);
		// BoardListServlet에서 페이지 번호 읽기 코드 copy&paste
		// 요청된 페이지 번호 읽기 (없으면 1로 설정

		// 2. 자료 번호로 자료 정보 조회 (DAO)
		BoardDao dao = new BoardDao();
		Board b = dao.selectBoardByBoardNo(iBoardNo);

		if (BoardNo == null || BoardNo.length() == 0) {
			resp.sendRedirect("list.action");
			return;
		}
		// 현재 페이지 번호를 request에 저장 -> jsp 사용
		req.setAttribute("board", b);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/board/editform.jsp");
		dispatcher.forward(req, resp);

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");

		doGet(req, resp);
	}

}
