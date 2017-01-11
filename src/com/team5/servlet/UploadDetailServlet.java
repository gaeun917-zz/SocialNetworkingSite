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

import com.team5.dao.MemberDao;
import com.team5.dao.UploadDao;
import com.team5.dto.Member;
import com.team5.dto.Upload;

@WebServlet(value = "/upload/detail.action")
public class UploadDetailServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {
		
		//1. get data (uploadNo)
		String uploadNo = req.getParameter("uploadno");
		if(uploadNo == null || uploadNo.length() == 0) {
			resp.sendRedirect("/demoweb/upload/list.action");
			return;
		}
		int iUploadNo = Integer.parseInt(uploadNo);		

		//2. search data (from DB)
		UploadDao dao = new UploadDao();
		Upload upload = dao.selectUploadByUploadNo(iUploadNo);
						dao.increaseUploadReadCount(iUploadNo);
		upload.setReadCount(upload.getReadCount()+1);
		
		//3. set data (into req)
		req.setAttribute("upload", upload);
		
		//4. forward req to jsp (조회된 정보 표시)
		RequestDispatcher dispatcher = 
				req.getRequestDispatcher("/WEB-INF/views/upload/detail.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {
		doGet(req, resp);
	}
}
