package com.ragaban.l2m;

public class Comments {
    private String date;

    private String user_name;

    private String comment;

    private String id;

    private String server_id;

    private String user_image;

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getUser_name ()
    {
        return user_name;
    }

    public void setUser_name (String user_name)
    {
        this.user_name = user_name;
    }

    public String getComment ()
    {
        return comment;
    }

    public void setComment (String comment)
    {
        this.comment = comment;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getServer_id ()
    {
        return server_id;
    }

    public void setServer_id (String server_id)
    {
        this.server_id = server_id;
    }

    public String getUser_image ()
    {
        return user_image;
    }

    public void setUser_image (String user_image)
    {
        this.user_image = user_image;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [date = "+date+", user_name = "+user_name+", comment = "+comment+", id = "+id+", server_id = "+server_id+", user_image = "+user_image+"]";
    }
}