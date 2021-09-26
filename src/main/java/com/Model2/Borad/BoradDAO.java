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

    public List<ArticleVO> selectAllArticles()
    {
        List<ArticleVO> articlesList = new ArrayList<>();
        try{
            conn = dataFactory.getConnection();

            String query = "with recursive cte as (\n" +
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
                    "select articleNO, parentNO, title, content, imageFileName, writeDate, id, level from cte";
            System.out.println(query);
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
}