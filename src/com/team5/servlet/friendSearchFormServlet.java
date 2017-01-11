package com.team5.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.MemberDao;
import com.team5.dto.Member;

@WebServlet("/friend/friendSearch.action")
public class friendSearchFormServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		
		response.setContentType("text/plain;charset=utf-8");

		//1. input의 key's value 가져옴
		String search = request.getParameter("search");

		//2. member dao에서 위의 value를 parameter로 method(search) 실행
		MemberDao dao = new MemberDao();
		ArrayList<Member> searchByMember = dao.getMemberListByNameAndSchoolAndLocation(search);

		//2.1 request에 method로 리턴된 값을 key.value로 setAttribute
		request.setAttribute("searchByMember", searchByMember);// request에 보낼 key와 value

		//3. redirect
		RequestDispatcher dispatcher=
				request.getRequestDispatcher("/WEB-INF/views/include/friendSearchForm.jsp");
		dispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}















