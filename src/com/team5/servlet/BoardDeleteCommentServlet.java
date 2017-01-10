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

import com.team5.dto.Upload;
import com.team5.dto.UploadFile;


@WebServlet("/board/deletecomment.action")
public class BoardDeleteCommentServlet extends HttpServlet {

	@Override
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {

		//1. detail에서 deletecomment function의 3 argument 해결하기 위해서 

		// 코멘트 넘버 읽기 	
	String commentNo = request.getParameter("commentno");
			if(commentNo==null && commentNo.length()==0){
				response.sendRedirect("list.action");//없으면 목록으로 이동
				return;
			}	
			// commentNo를 정수로 바꿔줌 
			int iCommentNo = Integer.parseInt(commentNo);
			
			//board를 parameter로 가져오고, 없으면 리스트로 이동
	String boardNo = request.getParameter("boardno");	
			if(boardNo==null && boardNo.length()==0){
				response.sendRedirect("list.action");//없으면 목록으로 이동
				return;
			}
			// boardNo를 정수로 바꿔줌 
			int iBoardNo = Integer.parseInt(boardNo);
			
	//int pageNo =(int)request.getAttribute("pageno");
			
	//2. 데이터 삭제 
			BoardDao dao = new BoardDao();
			dao.deleteComment(iCommentNo);
			
			
    //3. 목록으로 이동 (특정 페이지로 board와 페이지 넘버 지정& 이동)
			response.sendRedirect("list.action?boardno=" + boardNo);
	
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		doGet(req, resp);
	}
	
}






