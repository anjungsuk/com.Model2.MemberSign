package com.Model2.sec01;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/member/*")
public class MemberController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    MemberDAO memberDAO;
    public void init() throws ServletException{
        memberDAO = new MemberDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }

    private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextPage = null;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        String action = request.getPathInfo();
        
        if(action == null || action.equals("/listMembers.do"))
        {
            //들어온 요청에 대해 회원정보 조회
            List<MemberVO> membersList = memberDAO.listmembers();

            request.setAttribute("membersList", membersList);
            nextPage = "/MemberData/ListMemberData.jsp";
        }
        else if(action.equals("/addMember.do"))
        {
            String user_id = request.getParameter("user_id");
            String pwd = request.getParameter("pwd");
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            MemberVO memberVO = new MemberVO(user_id, pwd, name, email);
            memberDAO.addMember(memberVO);
            request.setAttribute("msg", "addMember");
            nextPage = "/member/listMembers.do";
        }
        else if(action.equals("/modMemberForm.do"))
        {
            String id = request.getParameter("user_id");

            MemberVO memberID = memberDAO.modMemberForm(id);
            request.setAttribute("memberID", memberID);
            nextPage = "/MemberJoin/MemberListUpdate.jsp";
        }
        else if(action.equals("/modMember.do"))
        {
            String user_id = request.getParameter("user_id");
            String pwd = request.getParameter("pwd");
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            MemberVO memberVO = new MemberVO(user_id, pwd, name, email);

            memberDAO.modMember(memberVO);
            request.setAttribute("msg", "modified");
            nextPage = "/member/listMember.do";
        }
        else if(action.equals("/delMember.do"))
        {
            String user_id = request.getParameter("user_id");

            memberDAO.delMember(user_id);
            request.setAttribute("msg", "complete");
            nextPage = "/member/listMember.do";
        }
        else if(action.equals("/memberForm.do"))
        {
            nextPage = "/MemberJoin/MemberListJoin.jsp";
        }
        else
        {
            List<MemberVO> membersList = memberDAO.listmembers();
            request.setAttribute("membersList", membersList);
            nextPage = "/MemberData/ListMemberData.jsp";
        }

        /*********************************************
         * RequestDispathcer 클래스 사용 하여
         * /MemberData/ListMemberData.jsp로 데이터 포워딩
         *********************************************/
        RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
        dispatcher.forward(request, response);

    }
}
