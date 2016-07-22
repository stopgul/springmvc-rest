package com.snn.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class FeedItem implements Serializable
{
    private Short newsUrlId;
    private String title;
    private String content;
    private Date date;
    private String link;
    private String smallImage;

    public FeedItem()
    {
    }

    public FeedItem(Short newsUrlId, String title, String content, Date date, String link, String smallImage)
    {
        this.newsUrlId = newsUrlId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.link = link;
        this.smallImage = smallImage;
    }

    public Short getNewsUrlId()
    {
        return newsUrlId;
    }

    public void setNewsUrlId(Short newsUrlId)
    {
        this.newsUrlId = newsUrlId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getSmallImage()
    {
        return smallImage;
    }

    public void setSmallImage(String smallImage)
    {
        this.smallImage = smallImage;
    }
}
