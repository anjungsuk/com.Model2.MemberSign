/*
package Model2.sec02;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/member/*")
public class MemberController extends HttpServlet {

    private static final  long serialVersionUID = 1L;
    MemberDAO memberDAO;
    public void init() throws ServletException{
        memberDAO = new MemberDAO();
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        String nextPage = null;
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        String action = request.getPathInfo();

        if(action == null || action.equals("/listMembers.do"))
        {
            List<MemberVO> membersList = memberDAO.listmembers();
            request.setAttribute("membersList", membersList);
        }


    }
}
*/
