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

@WebServlet("/board/write.action")
public class BoardWriteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 데이터 읽기
		String content = request.getParameter("content");
		Member member = (Member) request.getSession().getAttribute("loginuser");
		// 2. 데이터 저장
//		Board board = new Board();
//		/* board.setTitle(title); */
//		board.setWriter(member.getName());
//		board.setContent(content);
//		board.setMemberId(member.getMemberId());
//
//		BoardDao dao = new BoardDao();
//		dao.insertBoard(board);

		// 파일 업로드를 포함하는지 확인 (multipart/form-data 형식인지 검사)
		if (!ServletFileUpload.isMultipartContent(request)) {
			response.sendRedirect("list.action");// 목록으로 이동
			return;
		}

		// 1. 전송데이터 읽기
		ServletContext application = request.getServletContext();
		// application.getRealPath (ServletContext.getRealPath)
		// -> 가상경로(http://......) -> 물리경로(C:\\......)
		String path = application.getRealPath("/upload");// 실제 파일을 저장할
																	// 경로
		String tempPath = application.getRealPath("/temp");// 임시 파일을 저장할
																	// 경로

		// 전송 데이터 각 요소를 분리해서 개별 객체를 만들때 사용할 처리기
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(1024 * 1024 * 2);// 임시 파일 생성 여부를 결정하는 파일 크기
		factory.setRepository(new File(tempPath));// 임시 파일 저장 경로 설정

		// 요청 파서 (요청 정보를 읽고 개별 요소를 구성하는 역할 수행)
		ServletFileUpload uploader = new ServletFileUpload(factory);
		uploader.setFileSizeMax(1024 * 1024 * 10);// 업로드 최대 파일 크기 설정

		try {
			// 요청 정보를 파싱하고 분해해서 FileItem객체의 리스트로 반환
			List<FileItem> items = uploader.parseRequest(request);
			

			// Board 테이블에 insert할 데이터를 저장할 객체
			Board board = new Board();

			// UploadFile 테이블에 insert할 데이터를 저장할 객체
			ArrayList<UploadFile> files = new ArrayList<>();

			for (FileItem item : items) {
				if (item.isFormField()) {// 일반 form-data인 경우
					if (item.getFieldName().equals("content")) {
						board.setContent(item.getString("utf-8"));
					}
				} else {// 파일인 경우
					if (item.getSize() > 0) {// 파일의 내용이 존재하는 경우
						String fileName = item.getName();// 파일이름 반환

						// C:\\AAA\\BBB\\CCC.txt -> CCC.txt
						if (fileName.contains("\\")) {
							fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
						}

						String uniqueFileName =
						// Util.getUniqueFileName(path, fileName);
						Util.getUniqueFileName(fileName);

						// 파일을 로컬 경로에 저장
						// item.write(new File(path, fileName));
						item.write(new File(path, uniqueFileName));

						item.delete();// 임시 파일을 삭제

						UploadFile f = new UploadFile();
						f.setSavedFileName(uniqueFileName);
						f.setUserFileName(fileName);
						files.add(f);
					}
				}
			}

			// 데이터베이스에 데이터 insert
			UploadDao dao = new UploadDao();
			BoardDao dao2 = new BoardDao();
			board.setMemberId(member.getMemberId());
			board.setWriter(member.getName());
			System.out.println(board.getContent());
			System.out.println(member.getMemberId());
			System.out.println(member.getName());
			int newBoardNo = dao2.insertBoard(board);// Board insert
			for (UploadFile f : files) {
				f.setBoardNo(newBoardNo);
				f.setMemberId(member.getMemberId());
				dao.insertUploadFile(f);// UploadFile insert
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// 3. 목록으로 이동
		response.sendRedirect("list.action");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("utf-8");
		doGet(req, resp);
	}

}
