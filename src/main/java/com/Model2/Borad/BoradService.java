package com.Model2.Borad;

import java.util.List;

public class BoradService {
    BoradDAO boradDAO;

    public BoradService(){
        boradDAO = new BoradDAO();
    }

    public List<ArticleVO> listArticles(){
        List<ArticleVO> articleList = boradDAO.selectAllArticles();
        return articleList;
    }
    public int addArticle(ArticleVO articleVO){
      return boradDAO.insertNewArticle(articleVO);
    }
    public ArticleVO viewArticle(int articleNO)
    {
        ArticleVO article = null;
        article = boradDAO.selectArticle(articleNO);
        return article;
    }
}
