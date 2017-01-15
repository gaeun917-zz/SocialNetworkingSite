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
import com.team5.dto.BoardComment;
import com.team5.dto.Member;
import com.team5.dto.Upload;
import com.team5.dto.UploadFile;


@WebServlet("/board/updatecomment.action")
public class BoardUpdateCommentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //1. get/set data
        //boardNo는 redirect할 때 쓰려고 parsing함
        String boardNo = request.getParameter("boardno");
        if (boardNo == null && boardNo.length() == 0) {
            response.sendRedirect("list.action");//없으면 목록으로 이동
            return;
        }
        int iBoardNo = Integer.parseInt(boardNo);


        String commentNo = request.getParameter("commentno");
        if (commentNo == null && commentNo.length() == 0) {
            response.sendRedirect("list.action");//없으면 목록으로 이동
            return;
        }
        int iCommentNo = Integer.parseInt(commentNo);

        String content = request.getParameter("content");


        //2. BoardComment object에 CommentNo, content set하기: 아직 DB에 적용된거 아님
        BoardComment comment = new BoardComment();
                     comment.setCommentNo(iCommentNo); //2.1 comment에 number 저장
                     comment.setContent(content); //2.2 comment에 content 저장


        //3. update DB
        BoardDao dao = new BoardDao();
                 dao.updateComment(comment); //2.3 DB에서 comment 업데이트


        //4. redirect: boardno로 해당 보드 보여줌
        response.sendRedirect("list.action?boardno=" + iBoardNo);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        doGet(req, resp);
    }
}






