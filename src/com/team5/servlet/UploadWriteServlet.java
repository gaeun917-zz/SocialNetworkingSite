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
import com.team5.dao.UploadDao;
import com.team5.dto.Upload;
import com.team5.dto.UploadFile;


@WebServlet("/upload/write.action")
public class UploadWriteServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
						throws ServletException, IOException {
		RequestDispatcher dispatcher =
				req.getRequestDispatcher("/WEB-INF/views/upload/uploadwriteform.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");

		//파일 업로드를 포함하는지 확인 (multipart/form-data 형식인지 검사)
		if (!ServletFileUpload.isMultipartContent(request)) {
			response.sendRedirect("list.action");//목록으로 이동
			return;
		}

		//1. get data
		ServletContext application = request.getServletContext();
		//application.getRealPath (ServletContext.getRealPath)
		//-> virtual path(http://...) -> physical path(C:\\...)
		String path = application.getRealPath("/upload");//path to save file
		String tempPath = application.getRealPath("/WEB-INF/temp");

		//전송 데이터 각 요소를 분리해서 개별 객체를 만들때 사용할 처리기
		DiskFileItemFactory factory = new DiskFileItemFactory();
							factory.setSizeThreshold(1024 * 1024 * 2);//임시 파일 생성 여부를 결정하는 파일 크기
							factory.setRepository(new File(tempPath));//임시 파일 저장 경로 설정

		//요청 파서 (요청 정보를 읽고 개별 요소를 구성하는 역할 수행)
		ServletFileUpload uploader = new ServletFileUpload(factory);
						  uploader.setFileSizeMax(1024* 1024 * 10);//업로드 최대 파일 크기 설정

		try {
            //요청 정보를 파싱하고 분해해서 FileItem 객체의 리스트로 반환
            List<FileItem> items = uploader.parseRequest(request);
            //Upload에 insert할 데이터를 저장할 객체
            Upload upload = new Upload();
            //UploadFile에 insert할 데이터를 저장할 객체
            ArrayList<UploadFile> files = new ArrayList<>();

            for (FileItem item : items) {
                if (item.isFormField()) {//일반 form-data인 경우

                    if (item.getFieldName().equals("title")) {
                        upload.setTitle(item.getString("utf-8"));
                    } else if (item.getFieldName().equals("content")) {
                        upload.setContent(item.getString("utf-8"));
                    } else if (item.getFieldName().equals("memberId")) {
                        upload.setUploader(item.getString("utf-8"));
                    }
                }else if(item.getSize() > 0){// 파일의 내용이 존재하는 경우

                    String fileName = item.getName();
                    if (fileName.contains("\\")) { //returning file name, not path(C:\\AAA\\BBB\\CCC.txt -> CCC.txt)
                        fileName = fileName.substring(fileName.lastIndexOf(("\\") + 1));
                    }

                    String uniqueFileName = Util.getUniqueFileName(fileName);
                                            //Util.getUniqueFileName(path, fileName);
                    item.write(new File(path, uniqueFileName)); //파일을 로컬 경로에 저장
                    item.delete();//임시 파일을 삭제

                    UploadFile f = new UploadFile();
                                f.setSavedFileName(uniqueFileName);
                                f.setUserFileName(fileName);
                    files.add(f);
                    }
                }

			    //2. data insert

                //2.1 Upload insert
			    UploadDao dao = new UploadDao();
			    dao.insertUpload(upload);
                 //2.2 UploadFile insert
                for (UploadFile f : files) {
					/*f.setUploadNo(newUploadNo);*/
					dao.insertUploadFile(f);
                }
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		response.sendRedirect("list.action");
	}
}






