package com.Model2.Borad;

public class PageVO {
    private int page;
    private int amount;

    public PageVO(){
        this(0, 10);
    }

    public PageVO(int page, int amount)
    {
        this.page = page;
        this.amount = amount;
    }

    public int getSkip()
    {
        return  (page-1) * 10;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
