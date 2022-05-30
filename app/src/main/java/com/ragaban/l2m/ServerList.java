package com.ragaban.l2m;


public class ServerList
{

    private String date;

    private String server_adress;

    private String user_name;

    private String rates;

    private String start;

    private String client_mail;

    private String rating_count;

    private String stars;

    private String id;

    private String time;

    private String vip;

    private String chronicle;

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getServer_adress ()
    {
        return server_adress;
    }

    public void setServer_adress (String server_adress)
    {
        this.server_adress = server_adress;
    }

    public String getUser_name ()
    {
        return user_name;
    }

    public void setUser_name (String user_name)
    {
        this.user_name = user_name;
    }

    public String getRates ()
    {
        return rates;
    }

    public void setRates (String rates)
    {
        this.rates = rates;
    }

    public String getStart ()
    {
        return start;
    }

    public void setStart (String start)
    {
        this.start = start;
    }

    public String getClient_mail ()
    {
        return client_mail;
    }

    public void setClient_mail (String client_mail)
    {
        this.client_mail = client_mail;
    }

    public String getRating_count ()
    {
        return rating_count;
    }

    public void setRating_count (String rating_count)
    {
        this.rating_count = rating_count;
    }

    public String getStars ()
    {
        return stars;
    }

    public void setStars (String stars)
    {
        this.stars = stars;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getVip ()
    {
        return vip;
    }

    public void setVip (String vip)
    {
        this.vip = vip;
    }

    public String getChronicle ()
    {
        return chronicle;
    }

    public void setChronicle (String chronicle)
    {
        this.chronicle = chronicle;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [date = "+date+", server_adress = "+server_adress+", user_name = "+user_name+", rates = "+rates+", start = "+start+", client_mail = "+client_mail+", rating_count = "+rating_count+", stars = "+stars+", id = "+id+", time = "+time+", vip = "+vip+", chronicle = "+chronicle+"]";
    }
}
