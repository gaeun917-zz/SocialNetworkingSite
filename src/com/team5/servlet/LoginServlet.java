package com.team5.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team5.dao.MemberDao;
import com.team5.dto.Member;


@WebServlet(value = "/login.action")
public class LoginServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//1. 데이터 읽기(id, passwd)
		String email = req.getParameter("email");
		String passwd = req.getParameter("passwd");
		//passwd = Util.getHashedString(passwd, "SHA-256");

		//2. DB에서 데이터 조회
		MemberDao dao = new MemberDao();
		Member member = dao.selectMemberByEmailAndPasswd(email, passwd);		
		if (member != null) {
			//Servlet에서는 session이 기본 객체가 아니므로 읽어옴
			HttpSession session = req.getSession();
			session.setAttribute("loginuser", member);

			// 3. redirect to a personal page
			RequestDispatcher dispatcher =
						req.getRequestDispatcher("/memberdetail/detail.action?memberid="+member.getMemberId());
			dispatcher.forward(req, resp);
			
		} else {
			// 존재하는 멤버 아니면 index로 이동
				resp.sendRedirect("/team5/index.jsp");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);// 처리 내용이 같음: doGet으로 전달
	}

}
