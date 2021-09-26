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
}
