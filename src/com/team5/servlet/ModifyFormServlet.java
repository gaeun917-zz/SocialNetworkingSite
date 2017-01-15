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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher("/WEB-INF/views/member/index.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override // <form>이 postoffice에서 붙인 data를 request씨가 getParameter()로 전달해줌
				// 전달받은 데이터는 DTO에 setAttributeName으로 정리해서 집어넣음
				// 이 데이터를 가공하고 싶으면 DAO를 불러서 method()를 수행하라고 함
				// DAO로 이동->(DML)인경우, return 값을 받고 DTO에 등록
				// data 가공이 끝나고 client에게 어디로 이동하라고 주소지를 전달해줌
				// (data 전달하는 거 아니기때문에 postoffice의 request씨 필요없고, 그냥 redirect로 말해주면 됨)

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
					throws ServletException, IOException {

		//1. get data
		String passwd = req.getParameter("passwd");
		String highschool = req.getParameter("highschool");
		String university = req.getParameter("university");
		String location = req.getParameter("location");
		String phone = req.getParameter("phone");
		System.out.printf("[%s][%s][%s][%s][%s]",
							passwd, highschool, university,  location, phone);
		
		//2. set data (into DB)
		Member member = new Member();
				member.setPasswd(passwd);
		
		MemberInfo memberinfo = new MemberInfo();
					memberinfo.setHighschool(highschool);
					memberinfo.setUniversity(university);
					memberinfo.setLocation(location);
					memberinfo.setPhone(phone);

		//3. change the passwd
		MemberDao dao = new MemberDao();
					dao.modifyPassword(member);
					dao.modifyUser(memberinfo);

		//4. redirect to index(after changing passwd or memberInfo)
		resp.sendRedirect("/team5/index.jsp");
		
	}
}
