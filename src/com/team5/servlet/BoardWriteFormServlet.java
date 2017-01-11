package com.team5.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/board/writeform.action")
public class BoardWriteFormServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {

		RequestDispatcher dispatcher = 
			req.getRequestDispatcher("/WEB-INF/views/board/writeform.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");		
		doGet(req, resp);
	}
}






