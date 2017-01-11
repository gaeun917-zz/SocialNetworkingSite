package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.MemberDao;
import com.team5.dto.Member;

@WebServlet("/getsuggestions.action")
public class GetSuggestions extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
						throws ServletException, IOException {

		response.setContentType("text/plain;charset=utf-8");

		//요청 데이터 읽어서 변수에 저장 (없으면 오류 반환)
		String search = request.getParameter("id");
		if (search == null || search.length() == 0) {
			return;
		}
		
		//요청된 데이터로 검색 (검색 실패하면 오류 반환)
		MemberDao dao = new MemberDao();
		ArrayList<Member> memberList = dao.getMemberListByNameAndSchoolAndLocation(search);

		ArrayList<String> idList = new ArrayList<>();
		for(Member m : memberList){
			if (idList.size() == 0) {
				return;
			}else {
				idList.add(m.getName());
			}
		}

		//검색 결과를 문자열 형태로 반환
		PrintWriter writer = response.getWriter();
		for(int i = 0; i < idList.size(); i++) {
			if (i < idList.size() - 1) {
				writer.print(";");
			}else {
				writer.print(idList.get(i));
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		doGet(request, response);
	}

}















