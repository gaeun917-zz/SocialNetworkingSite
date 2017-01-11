package com.team5.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.team5.dao.BoardDao;



@WebServlet("/board/deletecomment.action")
public class BoardDeleteCommentServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //1. detail에서 deletecomment function의 3 argument 해결하기 위해서

        // 코멘트 & 보드 넘버 읽기
        String commentNo = request.getParameter("commentno");
        String boardNo = request.getParameter("boardno");

        if (commentNo == null || commentNo.length() == 0) {
            response.sendRedirect("list.action");//# 없으면 목록으로 이동
            return;
        }
        int iCommentNo = Integer.parseInt(commentNo);

        if (boardNo == null || boardNo.length() == 0) {
            response.sendRedirect("list.action");//없으면 목록으로 이동
            return;
        }
        int iBoardNo = Integer.parseInt(boardNo);

        //int pageNo =(int)request.getAttribute("pageno");

        //2. 데이터 삭제
        BoardDao dao = new BoardDao();
        dao.deleteComment(iCommentNo);

        //3. 목록으로 이동 (특정 페이지로 board와 페이지 넘버 지정 & 이동)
        response.sendRedirect("list.action?boardno=" + boardNo);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        doGet(req, resp);
    }
}






