package com.ch.goods.app.bean;

public class GoodDetailModel {
//    {"data":{"Describe":" 数量：3个&功率：50w ","Price":200.00,"Createtime":"2020-04-25T21:19:47.387","MainFilesPath":"/Files/42d89a98-6ec3-40a9-a8a4-c826c15bfdb8/Main.jpg","Title":"吸尘器","FilesPath":"/Files/42d89a98-6ec3-40a9-a8a4-c826c15bfdb8/1.jpg,","Id":"42d89a98-6ec3-40a9-a8a4-c826c15bfdb8"},"description":"获取成功","state":"1"}

    private String describe;
    private String mainFilesPath;
    private String FilesPath;
    private String title;
    private double price;

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getMainFilesPath() {
        return mainFilesPath;
    }

    public void setMainFilesPath(String mainFilesPath) {
        this.mainFilesPath = mainFilesPath;
    }

    public String getFilesPath() {
        return FilesPath;
    }

    public void setFilesPath(String filesPath) {
        FilesPath = filesPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
