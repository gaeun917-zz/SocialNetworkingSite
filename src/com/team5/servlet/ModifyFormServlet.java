package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team5.dao.MemberDao;
import com.team5.dto.Member;
import com.team5.dto.MemberInfo;
import com.team5.dto.Member;

@WebServlet(value = "/team5/modifyServlet.action")
public class ModifyFormServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher(
				"/WEB-INF/views/member/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//1. 데이터 읽기
		String passwd = req.getParameter("passwd");
		String highschool = req.getParameter("highschool");
		String university = req.getParameter("university");
		String location = req.getParameter("location");
		String phone = req.getParameter("phone");
		
		
		System.out.printf("[%s][%s][%s][%s][%s]",
			passwd, highschool, university,  location, phone);
		
		//2. 데이터 처리 (DB에 데이터를 저장)
		Member member = new Member();
		member.setPasswd(passwd);
		
		MemberInfo memberinfo = new MemberInfo();
		memberinfo.setHighschool(highschool);
		memberinfo.setUniversity(university);
		memberinfo.setLocation(location);
		memberinfo.setPhone(phone);
		
		MemberDao dao = new MemberDao();
		dao.modifyPassword(member);
		dao.modifyUser(memberinfo);
		//3. 결과에 따라 이동
		//지정된 경로로 요청을 보내는 명령
		resp.sendRedirect("/team5/index.jsp");
		
	}

	

}
