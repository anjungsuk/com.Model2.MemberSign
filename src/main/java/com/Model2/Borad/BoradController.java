package com.Model2.Borad;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(value = "/borad/*")
public class BoradController extends HttpServlet {
    BoradService boradService;
    ArticleVO articleVO;

    public void init() throws ServletException{
        boradService = new BoradService();
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
        String nextPage = "";

        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        String action = request.getPathInfo();


        try {
            List<ArticleVO> articlesList = new ArrayList<ArticleVO>();

            if(action == null){
                articlesList = boradService.listArticles();
                request.setAttribute("articlesList", articlesList);
                nextPage ="/board1/listArticles.jsp";

            }else if(action.equals("/listArticles.do")){

                articlesList = boradService.listArticles();
                request.setAttribute("articlesList", articlesList);
                nextPage ="/board1/listArticles.jsp";

            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
            dispatcher.forward(request, response);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
