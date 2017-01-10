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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String memberId = req.getParameter("memberid");
		System.out.println(memberId);
		int iMemberId = Integer.parseInt(memberId);
		
		String location = req.getParameter("location");
		System.out.println(location);
		String highschool = req.getParameter("highschool");
		String university = req.getParameter("university");
		String phone = req.getParameter("phone");
		
		//updateMemberInfo(MemberInfo memberinfo, int memberId) {

		
		MemberInfo memberinfo = new MemberInfo();
		memberinfo.setLocation(location);
		memberinfo.setHighschool(highschool);
		memberinfo.setUniversity(university);
		memberinfo.setPhone(phone);
		
		MemberDao dao = new MemberDao();
		dao.updateMemberInfo(memberinfo ,iMemberId);
		
		resp.sendRedirect("memberinfo.action?memberid="+ memberId);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);// 처리 내용이 같으므로 doGet으로 전달
		
	}

	

}
