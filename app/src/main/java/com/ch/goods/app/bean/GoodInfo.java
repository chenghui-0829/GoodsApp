package com.ch.goods.app.bean;

public class GoodInfo {

//    {"Price":666.00,"Createtime":"2020-04-25T21:20:11.3","Title":"电视","Id":"c920fb8a-c91c-4fda-b9ff-1e8c2e1ceecb"}

    double price;
    String title;
    String id;
    String imageUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
