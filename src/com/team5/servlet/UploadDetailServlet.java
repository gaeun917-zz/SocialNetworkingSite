package com.team5.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.team5.dao.MemberDao;
import com.team5.dao.UploadDao;
import com.team5.dto.Member;
import com.team5.dto.Upload;

@WebServlet(value = "/upload/detail.action")
public class UploadDetailServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//1. 요청 데이터 읽기 (상세 정보를 표시할 자료 번호)
		//   읽지 못하면 리스트로 이동
		String uploadNo = req.getParameter("uploadno");
		if(uploadNo == null || uploadNo.length() == 0) {
			resp.sendRedirect("/demoweb/upload/list.action");
			return;
		}
		//문자열을 숫자로 변경
		int iUploadNo = Integer.parseInt(uploadNo);		
		//2. 자료 번호로 자료 정보 조회 (DAO)
		UploadDao dao = new UploadDao();
		Upload upload = dao.selectUploadByUploadNo(iUploadNo);	
		
		//조회수 증가->  화면 리프레쉬하면 조회수 증가 
		// jsp가 안나오기때문에 다운로드가 됨. 
		dao.increaseUploadReadCount(iUploadNo);
		upload.setReadCount(upload.getReadCount()+1);
		
		//3. 조회된 자료를 jsp에서 읽을 수 있도록 req에 저장
		req.setAttribute("upload", upload);
		
		//4. 조회된 정보 표시
		RequestDispatcher dispatcher = 
			req.getRequestDispatcher(
					"/WEB-INF/views/upload/detail.jsp");
		dispatcher.forward(req, resp);


	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}
