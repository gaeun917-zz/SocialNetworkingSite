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

import com.team5.dao.BoardDao;
import com.team5.dao.MemberDao;
import com.team5.dao.UploadDao;
import com.team5.dto.Board;
import com.team5.dto.Member;
import com.team5.dto.Upload;

@WebServlet(value = "/board/detail.action")
public class BoardDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        //1. 요청 데이터 읽기
        String BoardNo = req.getParameter("boardno");
        String pageNo = req.getParameter("pageno");

            //1.1 보드 넘버 구하기
            if (BoardNo == null || BoardNo.length() == 0) {
                resp.sendRedirect("/demoweb/board/list.action");//읽지 못하면 리스트로 이동
                return;
            }
            int iBoardNo = Integer.parseInt(BoardNo); //String to Integer
            //1.2 페이지 넘버 구하기
            int currentPage = 1; //현재 페이지 번호
            if (pageNo != null && pageNo.length() > 0) {
                currentPage = Integer.parseInt(pageNo);
            }

        //2. 현재 페이지#를 request에 저장(jsp에서 사용)
        req.setAttribute("pageno", currentPage);

        BoardDao dao = new BoardDao();
        Board b = dao.selectBoardByBoardNo(iBoardNo);//DAO 접근해서 해당 보드 가져오기

        //3. 조회수 카운트 (화면 리프레쉬하면 조회수 증가)
        dao.updateBoardread_cnt(iBoardNo);
        b.setReadCnt(b.getReadCnt() + 1);

        req.setAttribute("board", b);// req에 저장

        //4. 조회된 정보 표시
        RequestDispatcher dispatcher =
                req.getRequestDispatcher("/WEB-INF/views/board/detail.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
