package com.Model2.Borad;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoradDAO {
    private DataSource dataFactory;
    ResultSet rs;
    Connection conn;
    PreparedStatement pstmt;

    public BoradDAO(){
        try{
            Context ctx = new InitialContext();
            Context evnContext = (Context) ctx.lookup("java:/comp/env");
            dataFactory = (DataSource) evnContext.lookup("jdbc/mariadb");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private int getNewArticleNO()
    {
        try{
            conn = dataFactory.getConnection();
            String query = "select max(articleNO) from t_board";

            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            if(rs.next())
            {
                return (rs.getInt(1) +1);
            }

            rs.close();
            pstmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public int insertNewArticle(ArticleVO articleVO){
        int articleNO = getNewArticleNO();
        try {
            conn = dataFactory.getConnection();
            int parentNO = articleVO.getParentNO();
            String title = articleVO.getTitle();
            String content = articleVO.getContent();
            String Id = articleVO.getId();
            String imageFileName = articleVO.getImageFileName();

            String query = "insert into t_board (articleNo, parentNo, title, content, imageFileName, id) values (?,?,?,?,?,?)";

            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, articleNO);
            pstmt.setInt(2, parentNO);
            pstmt.setString(3, title);
            pstmt.setString(4, content);
            pstmt.setString(5, imageFileName);
            pstmt.setString(6, Id);
            pstmt.executeUpdate();

            pstmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return articleNO;
    }

    public List<ArticleVO> selectAllArticles()
    {
        List<ArticleVO> articlesList = new ArrayList<>();
        try{
            conn = dataFactory.getConnection();

            String query = "SELECT CASE WHEN LEVEL-1 > 0 then CONCAT(CONCAT(REPEAT('    ', level  - 1),'┗'), board.title)\n" +
                    "            ELSE board.title\n" +
                    "    END AS title\n" +
                    "     , board.articleNO\n" +
                    "     , board.parentNO\n" +
                    "     , board.content\n" +
                    "     , board.imageFileName\n" +
                    "     , board.writeDate\n" +
                    "     , board.id\n" +
                    "     , result.level\n" +
                    "FROM\n" +
                    "    (SELECT function_hierarchical() AS articleNO, @level AS level, title\n" +
                    "     FROM (SELECT @start_with:=0, @articleNO:=@start_with, @level:=0) tbl JOIN t_board\n" +
                    "     WHERE articleNO IS NOT NULL) result\n" +
                    "      left outer join t_board board ON board.articleNO = result.articleNO;";


                    /*"with recursive cte as (\n" +
                    "    select articleNO\n" +
                    "    ,parentNO\n" +
                    "    ,title\n" +
                    "    ,content\n" +
                    "    ,imageFileName\n" +
                    "    ,writeDate\n" +
                    "    ,id\n" +
                    "    ,1 as level\n" +
                    "    from t_board\n" +
                    "    where\n" +
                    "        parentNO = 0\n" +
                    "    union all\n" +
                    "    select\n" +
                    "        a.articleNO\n" +
                    "    ,a.parentNO\n" +
                    "    ,a.title\n" +
                    "    ,a.content\n" +
                    "    ,a.imageFileName\n" +
                    "    ,a.writeDate\n" +
                    "    ,a.id\n" +
                    "    ,1+level as level\n" +
                    "    from t_board as a\n" +
                    "    inner join cte\n" +
                    "    on a.parentNO = cte.articleNO\n" +
                    ")\n" +
                    "select articleNO, parentNO, title, content, imageFileName, writeDate, id, level from cte"*/;

            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                int level = rs.getInt("level");
                int articleNO = rs.getInt("articleNO");
                int parentNO = rs.getInt("parentNO");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String Id = rs.getString("id");
                Date writedate = rs.getDate("writedate");

                ArticleVO articleVO = new ArticleVO();
                articleVO.setLevel(level);
                articleVO.setArticleNO(articleNO);
                articleVO.setParentNO(parentNO);
                articleVO.setTitle(title);
                articleVO.setContent(content);
                articleVO.setId(Id);
                articleVO.setWritedate(writedate);
                articlesList.add(articleVO);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return articlesList;
    }

    public ArticleVO selectArticle(int articleNO)
    {
        ArticleVO articleVO = new ArticleVO();
        try {
            conn = dataFactory.getConnection();

            String query = "select * from t_board where articleNO = ?";

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, articleNO);
            ResultSet rs = pstmt.executeQuery();

            rs.next();

            int _articleNO = rs.getInt("articleNO");
            int parentNO = rs.getInt("parentNO");
            String title = rs.getString("title");
            String content = rs.getString("content");
            String imageFileName = rs.getString("imageFileName");
            String id = rs.getString("id");
            Date writeDate = rs.getDate("writeDate");

            articleVO.setArticleNO(_articleNO);
            articleVO.setParentNO(parentNO);
            articleVO.setTitle(title);
            articleVO.setContent(content);
            articleVO.setImageFileName(imageFileName);
            articleVO.setId(id);
            articleVO.setWritedate(writeDate);
            rs.close();
            pstmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return articleVO;
    }

    public void updateArticle(ArticleVO articleVO)
    {
        int articleNo = articleVO.getArticleNO();
        String title = articleVO.getTitle();
        String content = articleVO.getContent();
        String imageFileName = articleVO.getImageFileName();

        try{
            conn= dataFactory.getConnection();
            String query = "update t_board set title = ?, content = ?";
            if(imageFileName != null && imageFileName.length() != 0)
            {
                query += ", imageFileName = ?";
                query += " where articleNO = ?";
            }
            else
            {
                query += " where articleNO = ?";
            }
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            if(imageFileName != null && imageFileName.length() != 0)
            {
                pstmt.setString(3, imageFileName);
                pstmt.setInt(4, articleNo);
            }
            else
            {
                pstmt.setInt(3, articleNo);
            }
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /***************************************
    *stackoverFlow 오류는 mariadb 프로시져 내에서
    *무의미한 별칭때문에 발생한거로 확인됨
    *****************************************/
    public void deleteArticle(int  articleNO) {
        try {
            conn = dataFactory.getConnection();
            String query = "call deletePost(?)";

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, articleNO);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**********************
    * 삭제 하기 전 삭제하는 글 리스트를 articleNOList에 저장함.
    *articleNO 구하는데에서 해매는중 ㅠ*/
    public List<Integer> selectRemovedArticles(int articleNO)
    {
        List<Integer> articleNOList = new ArrayList<Integer>();

        try {
            conn = dataFactory.getConnection();
            String query = "with recursive cte as (\n" +
                    "                        select articleNO\n" +
                    "                        ,parentNO\n" +
                    "                        ,title\n" +
                    "                        ,content\n" +
                    "                        ,imageFileName\n" +
                    "                        ,writeDate\n" +
                    "                        ,id\n" +
                    "                        ,1 as level\n" +
                    "                        from t_board\n" +
                    "                        where\n" +
                    "                            articleNO = ?\n" +
                    "                        union all\n" +
                    "                        select\n" +
                    "                            a.articleNO\n" +
                    "                        ,a.parentNO\n" +
                    "                        ,a.title\n" +
                    "                        ,a.content\n" +
                    "                        ,a.imageFileName\n" +
                    "                        ,a.writeDate\n" +
                    "                        ,a.id\n" +
                    "                        ,1+level as level\n" +
                    "                        from t_board as a\n" +
                    "                        inner join cte\n" +
                    "                        on a.parentNO = cte.articleNO\n" +
                    "                    )\n" +
                    "                    select articleNO, parentNO, title, content, imageFileName, writeDate, id, level from cte";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, articleNO);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                articleNO = rs.getInt("articleNO");
                articleNOList.add(articleNO);
            }
            pstmt.close();
            rs.close();
            conn.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        return articleNOList;
    }

    public int getTotal()
    {
       try {
           conn = dataFactory.getConnection();
           String query = "select count(*) as total from t_board";

           pstmt = conn.prepareStatement(query);
           rs = pstmt.executeQuery();
           rs.next();
           int total = rs.getInt("total");

           return rs.getInt("total");
       }catch (Exception e){
           e.printStackTrace();
       }
       return -1;
    }

    public List<ArticleVO> getList(PageVO page)
    {
        List<ArticleVO> list = new ArrayList<>();

        try{
            conn = dataFactory.getConnection();

            String query = "SELECT CASE WHEN LEVEL-1 > 0 then CONCAT(CONCAT(REPEAT('    ', level  - 1),'┗'), board.title)\n" +
                    "            ELSE board.title\n" +
                    "    END AS title\n" +
                    "     , board.articleNO\n" +
                    "     , board.parentNO\n" +
                    "     , board.content\n" +
                    "     , board.imageFileName\n" +
                    "     , board.writeDate\n" +
                    "     , board.id\n" +
                    "     , result.level\n" +
                    "FROM\n" +
                    "    (SELECT function_hierarchical() AS articleNO, @level AS level, title\n" +
                    "     FROM (SELECT @start_with:=0, @articleNO:=@start_with, @level:=0) tbl JOIN t_board\n" +
                    "     WHERE articleNO IS NOT NULL) result\n" +
                    "        left outer join t_board board ON board.articleNO = result.articleNO\n" +
                    "order by articleNO asc\n" +
                    "limit ?, ?";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, page.getSkip());
            pstmt.setInt(2, page.getAmount());

            rs = pstmt.executeQuery();

            while (rs.next())
            {
                int level = rs.getInt("level");
                int articleNO = rs.getInt("articleNO");
                int parentNO = rs.getInt("parentNO");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String Id = rs.getString("id");
                Date writedate = rs.getDate("writedate");

                ArticleVO articleVO = new ArticleVO();
                articleVO.setLevel(level);
                articleVO.setArticleNO(articleNO);
                articleVO.setParentNO(parentNO);
                articleVO.setTitle(title);
                articleVO.setContent(content);
                articleVO.setId(Id);
                articleVO.setWritedate(writedate);

                list.add(articleVO);
            }

            return list;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
