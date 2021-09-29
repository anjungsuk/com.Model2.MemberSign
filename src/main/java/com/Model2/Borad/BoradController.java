package com.Model2.Borad;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(value = "/borad/*")
public class BoradController extends HttpServlet {
    private static String ARTICLE_IMAGE_REPO = "/Users/gangjungu/ChunbaeCompany";
    BoradService boradService;
    ArticleVO articleVO;


    public void init() throws ServletException{
        boradService = new BoradService();
        //객체를 생성 안해줄 경우 setter에 null 오류 발생
        articleVO = new ArticleVO();
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

            }else if(action.equals("/articleForm.do")){

                nextPage = "/board1/articleForm.jsp";

            }else if(action.equals("/addArticle.do")){
                int articleNO = 0;
                Map<String, String> articleMap = upload(request, response);
                String title = articleMap.get("title");
                String content = articleMap.get("content");
                String imageFileName = articleMap.get("imageFileName");

                articleVO.setParentNO(0);
                articleVO.setId("hong");
                articleVO.setTitle(title);
                articleVO.setContent(content);
                articleVO.setImageFileName(imageFileName);
                articleNO = boradService.addArticle(articleVO);

                if(imageFileName != null && imageFileName.length() != 0)
                {
                    File srcFile = new File(ARTICLE_IMAGE_REPO +"/" +"temp"+"/"+ imageFileName);
                    File destDir = new File(ARTICLE_IMAGE_REPO + "/" + articleNO);
                    destDir.mkdirs();
                    FileUtils.moveFileToDirectory(srcFile, destDir, true);
                }

                PrintWriter pw = response.getWriter();
                pw.print("<script>" + "alert('새글을 등록 하였습니다.');"+"location.href = '" + request.getContextPath() + "/board/listArticles.do'"+"</script>");

                return;


                //boradService.addArticle(articleVO);

                //nextPage = "/board1/listArticles.jsp";
            }
            else if(action.equals("/viewArticle.do"))
            {
                String articleNO = request.getParameter("articleNO");
                articleVO = boradService.viewArticle(Integer.parseInt(articleNO));
                request.setAttribute("article", articleVO);
                nextPage = "/board1/viewArticle.jsp";
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
            dispatcher.forward(request, response);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        Map<String, String> articleMap = new HashMap<String, String>();
        String encoding = "utf-8";

        File currentDirPath = new File(ARTICLE_IMAGE_REPO);
        DiskFileItemFactory factory = new DiskFileItemFactory();

        factory.setRepository(currentDirPath);
        factory.setSizeThreshold(1024*1024);
        ServletFileUpload upload = new ServletFileUpload(factory);

        try{
            List items = upload.parseRequest(request);
            for (int i = 0; i< items.size(); i++)
            {
                FileItem fileItem = (FileItem) items.get(i);

                if(fileItem.isFormField())
                {
                    System.out.println(fileItem.getFieldName() + "=" + fileItem.getString());
                    articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));
                }
                else
                {
                    if(fileItem.getSize() > 0)
                    {
                        int idx = fileItem.getName().lastIndexOf("\\");

                        if(idx == -1)
                        {
                            idx = fileItem.getName().lastIndexOf("/");
                        }

                        String fileName = fileItem.getName().substring(idx+1);
                        articleMap.put(fileItem.getFieldName(), fileName);
                        File uploadFile = new File(currentDirPath +"/temp/"+ fileName);
                        System.out.println(uploadFile);
                        fileItem.write(uploadFile);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return articleMap;
    }
}
