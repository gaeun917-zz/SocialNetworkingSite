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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 1. 요청 데이터 읽기 (상세 정보를 표시할 글 번호)
		// 읽지 못하면 리스트로 이동
		String memberId = req.getParameter("memberid");
		if (memberId == null || memberId.length() == 0) {
			System.out.println("no memberid");
			resp.sendRedirect("/team5/member/memberinfo.action");
			return;
		}
		// 문자열을 숫자로 변경
		int iMemberId = Integer.parseInt(memberId);

		// 2. 자료 번호로 자료 정보 조회 (DAO)
		// (없으면 목록으로)
		MemberDao dao = new MemberDao();
		MemberInfo memberinfo = new MemberInfo();
		memberinfo = dao.selectMemberInfoById(iMemberId);
		if (memberinfo == null) {
			resp.sendRedirect("list.action");
			return;
		}
		// jsp에서 읽을 수 있도록 request에 데이터 저장
		req.setAttribute("memberinfo", memberinfo);

		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/views/member/InformationUpdate.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
