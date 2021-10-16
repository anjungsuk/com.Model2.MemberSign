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
import java.util.*;

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
        HttpSession session;


        try {
            List<ArticleVO> articlesList = new ArrayList<ArticleVO>();

            if(action == null){
                articlesList = boradService.listArticles();
                request.setAttribute("articlesList", articlesList);
                nextPage ="/board1/listArticles.jsp";

            }else if(action.equals("/listArticles.do")){

                int pageNum;
                int pageAmount;

                String PageNumber = request.getParameter("page");
                if(PageNumber != null)
                {
                    pageNum = Integer.parseInt(PageNumber.trim());
                }
                else
                {
                    pageNum = 1;
                }

                String PageAmount = request.getParameter("amount");

                if(PageAmount != null)
                {
                    pageAmount = Integer.parseInt(PageAmount.trim());
                }
                else
                {
                    pageAmount = 10;
                }


                // 1 ~10 잘 전달 받음,,
                PageVO page = new PageVO(pageNum, pageAmount);

                //총 컬럼 수 14 잘 전달 받음
                int total = boradService.getTotal();

                articlesList = boradService.getList(page);

                request.setAttribute("articlesList", articlesList);

                request.setAttribute("page", new PageOper(page, total));

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
                pw.print("<script>" + "alert('새글을 등록 하였습니다.');"+"location.href = '" + request.getContextPath() + "/borad/listArticles.do'"+"</script>");

                return;

            }
            else if(action.equals("/viewArticle.do"))
            {
                String articleNO = request.getParameter("articleNO");
                articleVO = boradService.viewArticle(Integer.parseInt(articleNO));
                request.setAttribute("article", articleVO);
                nextPage = "/board1/viewArticle.jsp";
            }
            else if(action.equals("/modArticle.do"))
            {
                //2021.10.04 수정 부분 작업 진행 중
                Map<String, String> articleMap = upload(request, response);
                int articleNO = Integer.parseInt(articleMap.get("articleNO"));
                articleVO.setArticleNO(articleNO);
                String title = articleMap.get("title");
                String content = articleMap.get("content");
                String imageFileName = articleMap.get("imageFileName");

                articleVO.setParentNO(0);
                articleVO.setId("hong");
                articleVO.setTitle(title);
                articleVO.setContent(content);
                articleVO.setImageFileName(imageFileName);

                boradService.modArticle(articleVO);

                if(imageFileName != null && imageFileName.length() !=0)
                {
                    String originalFileName = articleMap.get("originalFileName");
                    //수정된 파일 경로 이동
                    File srcFile = new File(ARTICLE_IMAGE_REPO +"/" + "temp" + "/" + imageFileName);
                    File destDir = new File(ARTICLE_IMAGE_REPO +"/"+"/"+articleNO);
                    destDir.mkdirs();
                    FileUtils.moveFileToDirectory(srcFile, destDir, true);

                    //구 폴더 이미지 파일 삭제
                    File oldFile = new File(ARTICLE_IMAGE_REPO + "/" + articleNO + "/" + originalFileName);
                    oldFile.delete();
                }
                PrintWriter pw = response.getWriter();
                pw.print("<script>" + "  alert('글을 수정했습니다.');" + " location.href='" + request.getContextPath()
                        + "/borad/viewArticle.do?articleNO=" + articleNO + "';" + "</script>");

                return;
            }
            else if(action.equals("/removeArticle.do"))
            {
                int articleNO = Integer.parseInt(request.getParameter("articleNO"));

                List<Integer> articleNOList = boradService.removeArticle(articleNO);

                for (int _articleNO : articleNOList)
                {
                    File imgDir = new File(ARTICLE_IMAGE_REPO + "/" + _articleNO);

                    if(imgDir.exists())
                    {
                        FileUtils.deleteDirectory(imgDir);
                    }
                }

                PrintWriter pw = response.getWriter();
                pw.print("<script>" + "  alert('글을 삭제했습니다.');" + " location.href='" + request.getContextPath()
                        + "/borad/listArticles.do';" + "</script>");

                return;

            }
            else if(action.equals("/replyForm.do"))
            {
                // 파라미터 요청 통한 부모 인덱스 전달
                String parent = request.getParameter("test");
                if(parent == null)
                {
                    parent = "0";
                }
                int parentNO = Integer.parseInt(parent);



                // session내 부모 인덱스 저장
                session = request.getSession();
                session.setAttribute("parentNO", parentNO);

                nextPage = "/board1/replyForm.jsp";

            }
            else if(action.equals("/addReply.do"))
            {
                session = request.getSession();

                int parentNO = (Integer) session.getAttribute("parentNO");


                session.removeAttribute("parentNO");
                Map<String, String> articleMap = upload(request, response);

                String title = articleMap.get("title");
                String content = articleMap.get("content");
                String imageFileName = articleMap.get("imageFileName");

                articleVO.setParentNO(parentNO);
                articleVO.setTitle("lee");
                articleVO.setTitle(title);
                articleVO.setContent(content);
                articleVO.setImageFileName(imageFileName);

                int articleNO = boradService.addReply(articleVO);

                if(imageFileName != null && imageFileName.length() != 0)
                {
                    File srcFile = new File(ARTICLE_IMAGE_REPO + "/" + "temp" + "/" + imageFileName);
                    File desDir = new File(ARTICLE_IMAGE_REPO + "/" + articleNO);

                    desDir.mkdirs();
                    FileUtils.moveFileToDirectory(srcFile, desDir, true);
                }
                PrintWriter pw = response.getWriter();
                pw.print("<script>" +
                        "alert('답글을 추가 하였습니다.');" +
                        "location.href = '" +
                        request.getContextPath()+
                        "/borad/listArticles.do';" +
                        "</script>");
                return;

            }
            else if(action.equals("/listArticles.do"))
            {


                nextPage = "/board1/listArticles.jsp";


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
