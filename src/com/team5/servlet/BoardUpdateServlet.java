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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
						throws ServletException, IOException {

		//1. 데이터 읽기
		String boardNo = request.getParameter("boardno");
		int iBoardNo = Integer.parseInt(boardNo);

		String title = request.getParameter("title");
		String content = request.getParameter("boardupdatecontent");
		Member member = (Member)request.getSession().getAttribute("loginuser");
		int memberId = member.getMemberId();//2. 데이터 저장

		//2. 보드 넘버 & content 구하기
		Board board = new Board();
			  board.setBoardNo(iBoardNo);
		      board.setContent(content);

		//3. DB에 update 적용
		BoardDao dao = new BoardDao();
				 dao.updateBoard(board);
		
		//4. board로 이동
		response.sendRedirect("list.action?boardno=" +boardNo);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		doGet(req, resp);
	}
}






