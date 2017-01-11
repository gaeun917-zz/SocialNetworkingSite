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


@WebServlet("/board/delete.action")
public class BoardDeleteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
						throws ServletException, IOException {
			//1. 데이터 읽기
			String boardNo = request.getParameter("boardno");
			if(boardNo==null || boardNo.length()==0){
				response.sendRedirect("list.action");//없으면 목록으로 이동
				return;
			}
			int iBoardNo = Integer.parseInt(boardNo);
			
			//2. 데이터 삭제 
			BoardDao dao = new BoardDao();
			dao.deleteBoard(iBoardNo);// 해당 Board 삭제: 삭제된 data는 DB에서 1로 표시
			
			//3. 목록으로 이동 (특정 페이지로 이동)
			response.sendRedirect("list.action");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		doGet(req, resp);
	}
}






