package com.team5.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
import com.team5.dao.MemberDao;
import com.team5.dao.UploadDao;
import com.team5.dao.UploadDao2;
import com.team5.dto.Board;
import com.team5.dto.BoardComment;
import com.team5.dto.Member;
import com.team5.dto.Upload;
import com.team5.dto.UploadFile;


@WebServlet("/memberdetail/friendwritecomment.action")
public class FriendBoardWriteCommentServlet extends HttpServlet {

	@Override
	protected void doGet(
		HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		// 코멘트를 데이터 베이스에 저장하고, 이걸 화면에 구현하는게 어려움 
		//1. 데이터 읽기
			String boardNo = request.getParameter("boardno");
			if(boardNo==null && boardNo.length()==0){
				response.sendRedirect("list.action");//없으면 목록으로 이동
				return;
			}
			// 입력으로 넘어옴 
			String memberId = request.getParameter("memberid");
			int iMemberId = Integer.parseInt(memberId);
			int iBoardNo = Integer.parseInt(boardNo);
			String content = request.getParameter("content");
			//작성자
			Member member1 = (Member)request.getSession().getAttribute("loginuser");	
			BoardComment comment = new BoardComment();
			comment.setWriter(member1.getName());
			comment.setMemberId(member1.getMemberId());
			comment.setBoardNo(iBoardNo);
			//comment.setWriter(member.getName());
			comment.setContent(content);
			
			//2. 데이터 insert 
			BoardDao dao = new BoardDao();
			dao.insertComment(comment);
			//3. detail로 이동 

			UploadDao2 dao1 = new UploadDao2();
			List<Board> boards = dao1.selectBoardList(iMemberId);
			//3. 조회된 데이터를 jsp에서 사용하도록 Request에 저장
			request.setAttribute("boards", boards);	
			
			// 업로드된 모든 파일들의 목록을 가져온다.
			List<UploadFile> uploadfiles = dao1.selectUploadfileList(); 
			request.setAttribute("uploadfiles", uploadfiles); 
			
			BoardDao dao2 = new BoardDao();
//			List<Board> boards = dao.selectBoardList();
			List<Board> boards2 = dao2.selectBoardList();
			
			MemberDao dao3 = new MemberDao();
			Member member = dao3.selectMemberById(memberId);		
			
			request.setAttribute("member", member);

//			//3. 조회된 데이터를 jsp에서 사용하도록 Request에 저장
			request.setAttribute("boards2", boards2);
			
			//3. 조회된 정보 표시
			RequestDispatcher dispatcher = 
					request.getRequestDispatcher(
						"/WEB-INF/views/member/frienddetail.jsp");
			dispatcher.forward(request, response);
			
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		
		doGet(req, resp);
	}
	
}






