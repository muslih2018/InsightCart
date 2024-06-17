package com.dicoding.InsightCart;

public class MenuItem {
    private String menu;
    private String price;

    public MenuItem(String menu, String price) {
        this.menu = menu;
        this.price = price;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
