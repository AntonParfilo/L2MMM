package com.ragaban.l2m;

public class Star {

    private String val; // название
    private int stars; // ресурс флага

    public Star(String val1, int stars1){
        val = val1;
        stars = stars1;
    }

    public String getVal() {
        return val;
    }

    public int getStars() {
        return stars;
    }
    public String toString()
    {
        return val;
    }
}