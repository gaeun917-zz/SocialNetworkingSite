package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.team5.dao.BoardDao;
import com.team5.dao.MemberDao;
import com.team5.dto.BoardComment;
import com.team5.dto.Member;
import com.team5.dto.MemberInfo;
import com.google.gson.Gson;

@WebServlet("/commentConfirm.action")
public class CommentConfirm extends HttpServlet {

	 
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Member member = (Member)request.getSession().getAttribute("loginuser");
		BoardDao dao = new BoardDao();
		
		if (member != null) {
			
			List<BoardComment> commentList = dao.selectCommentsOfMyFriend2(member.getMemberId(), 10);
			
			response.setContentType("text/plain;charset=utf-8");
			PrintWriter writer = response.getWriter();
			
			for(int i = commentList.size() - 1; i >= 0; i--) {
				
				BoardComment comment = commentList.get(i);
				
				writer.print(String.format("%s$%d$%s", comment.getWriter(), comment.getBoardNo(), comment.getRegDate()));
				
				if (i > 0) {
					writer.print(";");
					
				}
			}
		}
		
		
		
	}	
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}















