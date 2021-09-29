package com.Model2.common;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet(value = "/download.do")
public class FileDownloadController extends HttpServlet {
    private static String ARTICLE_IMAGE_REPO = "/Users/gangjungu/ChunbaeCompany";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doHandle(request, response);
    }

    private void doHandle(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException{
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");

        String imageFileName = (String) request.getParameter("imageFileName");
        String articleNO = request.getParameter("articleNO");

        OutputStream out = response.getOutputStream();
        //다운 받을 파일 경로 설정
        String path = ARTICLE_IMAGE_REPO+"/"+articleNO+"/"+imageFileName;
        File imageFile = new File(path);
        response.setHeader("Cache-Control", "no-cache");
        response.addHeader("Content-disposition", "attachment;fileName = " + imageFileName);
        FileInputStream in = new FileInputStream(imageFile);

        byte[] buffer = new byte[1024*8];
        while (true)
        {
            int count = in.read(buffer);
            if(count == -1)
                break;
            out.write(buffer, 0, count);

        }
        in.close();
        out.close();

    }
}
