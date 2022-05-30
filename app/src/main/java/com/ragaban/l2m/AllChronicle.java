package com.ragaban.l2m;


public class AllChronicle {


    private String chronicle;



    public String getCh ()
    {
        return chronicle;
    }

    public void setCh (String date)
    {
        this.chronicle = date;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [ch = "+chronicle+"]";
    }
}
