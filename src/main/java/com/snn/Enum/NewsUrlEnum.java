package com.snn.Enum;

public enum NewsUrlEnum // implements IEnum<Short>
{
    WSJ((short) 1, "http://online.wsj.com/xml/rss/3_7085.xml"), 
    VOANEWS((short) 2, "http://www.voanews.com/api/epiqq"),
    NYTIMES((short) 3, "http://rss.nytimes.com/services/xml/rss/nyt/HomePage.xml");
    //INDEPENDENT((short) 4, "http://rss.feedsportal.com/c/266/f/3495/index.rss");

    private final Short value;
    private final String url;

    private NewsUrlEnum(Short value, String url)
    {
        this.value = value;
        this.url = url;
    }

    public static NewsUrlEnum getNewsEnum(short newsUrlId)
    {
        for (NewsUrlEnum newsUrlEnum : NewsUrlEnum.values())
        {
            if (newsUrlEnum.getValue() == newsUrlId)
            {
                return newsUrlEnum;
            }
        }
        return null;
    }
    
    public short getValue()
    {
        return value;
    }

    public String getUrl()
    {
        return url;
    }
}
