package com.Model2.Borad;

import java.util.Date;

public class ArticleVO {
    private int level;
    private int articleNO;
    private int parentNO;
    private String title;
    private String content;
    private String imageFileName;
    private String id;
    private Date writedate;

    public ArticleVO(){

    }

    public ArticleVO(int level, int articleNO, int parentNO, String title, String content, String imageFileName, String id)
    {
        this.level = level;
        this.articleNO = articleNO;
        this.parentNO = parentNO;
        this.title = title;
        this.content = content;
        this.imageFileName = imageFileName;
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getArticleNO() {
        return articleNO;
    }

    public void setArticleNO(int articleNO) {
        this.articleNO = articleNO;
    }

    public int getParentNO() {
        return parentNO;
    }

    public void setParentNO(int parentNO) {
        this.parentNO = parentNO;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getWritedate() {
        return writedate;
    }

    public void setWritedate(Date writedate) {
        this.writedate = writedate;
    }


}
