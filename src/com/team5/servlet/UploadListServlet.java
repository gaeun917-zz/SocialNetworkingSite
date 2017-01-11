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

		// (if 'upload page' is only for member, then use the if statment below to check user has logged in)
		// if user is not a member, redirect to a login page
//		if (req.getSession().getAttribute("loginuser") == null) {
//			resp.sendRedirect("/demoweb/account/loginform.action?returnurl=" + req.getRequestURI());
//			return;
//		}

		//1. search data(upload from dao)
		UploadDao dao = new UploadDao();
		List<Upload> uploads = dao.selectUploadList();
		
		//2. set data (into req)
		req.setAttribute("uploads", uploads);
		
		//3. forward to jsp(with req)
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher("/WEB-INF/views/upload/uploadlist.jsp");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		doGet(req, resp);
	}
	
}






