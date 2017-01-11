package com.team5.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.MemberDao;
import com.team5.dto.Member;
import com.team5.dto.MemberInfo;

@WebServlet(value = "/member/update.action")
public class MemberMyInfoUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {

		//1. get data (memeberId, location, highschool, university, phone)
		String memberId = req.getParameter("memberid");
		int iMemberId = Integer.parseInt(memberId); //string to int
		
		String location = req.getParameter("location");
		String highschool = req.getParameter("highschool");
		String university = req.getParameter("university");
		String phone = req.getParameter("phone");


		//2. set data
		MemberInfo memberinfo = new MemberInfo();
					memberinfo.setLocation(location);
					memberinfo.setHighschool(highschool);
					memberinfo.setUniversity(university);
					memberinfo.setPhone(phone);

		MemberDao dao = new MemberDao();
		dao.updateMemberInfo(memberinfo ,iMemberId);

		//3. redirect to personal page
		resp.sendRedirect("memberinfo.action?memberid="+ memberId);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);// same logic is used: method=post -> method=get
	}
}
