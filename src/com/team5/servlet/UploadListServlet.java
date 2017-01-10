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

import com.team5.dao.UploadDao;
import com.team5.dto.Upload;


@WebServlet("/upload/list.action")
public class UploadListServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		
		//1. 로그인 여부 확인 (로그인 하지 않은 사용자는 로그인 화면으로 redirect))
//		if (req.getSession().getAttribute("loginuser") == null) {
//			resp.sendRedirect(
//				"/demoweb/account/loginform.action?returnurl=" + req.getRequestURI());
//			return;
//		}
		

		//2. 목록 데이터 조회
		UploadDao dao = new UploadDao();
		List<Upload> uploads = dao.selectUploadList();
		
		//3. 조회된 데이터를 jsp에서 사용하도록 Request에 저장
		req.setAttribute("uploads", uploads);
		
		//4. 목록 보기 jsp로 forward
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher("/WEB-INF/views/upload/uploadlist.jsp");
		dispatcher.forward(req, resp);
		//
		
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		doGet(req, resp);
	}
	
}






