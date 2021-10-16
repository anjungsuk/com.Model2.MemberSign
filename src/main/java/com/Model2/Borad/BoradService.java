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

    public void modArticle(ArticleVO articleVO)
    {
        boradDAO.updateArticle(articleVO);
    }


    public List<Integer> removeArticle(int articleNO)
    {
        List<Integer> articleNOList = boradDAO.selectRemovedArticles(articleNO);
        boradDAO.deleteArticle(articleNO);
        return articleNOList;
    }
    public int addReply(ArticleVO articleVO)
    {
        return boradDAO.insertNewArticle(articleVO);
    }

    public int getTotal(){
        return boradDAO.getTotal();
    }

    public List<ArticleVO> getList(PageVO page)
    {
        return boradDAO.getList(page);
    }
}
