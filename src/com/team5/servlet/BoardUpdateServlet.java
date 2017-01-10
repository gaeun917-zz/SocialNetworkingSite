package com.team5.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.team5.common.Util;
import com.team5.dao.BoardDao;
import com.team5.dao.UploadDao;
import com.team5.dto.Board;
import com.team5.dto.Member;
import com.team5.dto.Upload;
import com.team5.dto.UploadFile;


@WebServlet("/board/update.action")
public class BoardUpdateServlet extends HttpServlet {

	@Override
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		//1. 데이터 읽기
		String boardNo = request.getParameter("boardno");
		//정수로 바꿔줄려고 
		int iBoardNo = Integer.parseInt(boardNo);
		System.out.println(iBoardNo);
		String title = request.getParameter("title");
		String content = request.getParameter("boardupdatecontent");
		System.out.println(content);
		Member member = 
			(Member)request.getSession().getAttribute("loginuser");
		int memberId = member.getMemberId();
		System.out.println(memberId);
		//2. 데이터 저장
		Board board = new Board();
		board.setBoardNo(iBoardNo);
		/*board.setTitle(title);*/
		/*board.setWriter(memberId);*/
		board.setContent(content);
		
		BoardDao dao = new BoardDao();
		dao.updateBoard(board);			
		
		//3. 목록으로 이동
		response.sendRedirect("list.action?boardno=" +boardNo);
		
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		doGet(req, resp);
	}
	
}






