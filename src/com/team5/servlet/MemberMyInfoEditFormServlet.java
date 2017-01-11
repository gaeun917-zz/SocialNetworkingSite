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

@WebServlet(value = "/member/edit.action")
public class MemberMyInfoEditFormServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {

		// 1. get data(memeberid to get memberinfo)
		String memberId = req.getParameter("memberid");
		if (memberId == null || memberId.length() == 0) {
			resp.sendRedirect("/team5/member/memberinfo.action");	//if there is no memeberid, redirect
			return;
		}
		int iMemberId = Integer.parseInt(memberId);//string to int

		MemberDao dao = new MemberDao();
		MemberInfo memberinfo = dao.selectMemberInfoById(iMemberId);
		if (memberinfo == null) {
			resp.sendRedirect("list.action");
			return;
		}

		//2. set data
		req.setAttribute("memberinfo", memberinfo);

		//3. redirect
		RequestDispatcher dispatcher =
				req.getRequestDispatcher("/WEB-INF/views/member/InformationUpdate.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
