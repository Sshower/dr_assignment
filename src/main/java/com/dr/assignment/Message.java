
package com.dr.assignment;

import org.springframework.data.annotation.Id;

public class Message
{
    @Id
    private String id;
    private String text;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }
}
