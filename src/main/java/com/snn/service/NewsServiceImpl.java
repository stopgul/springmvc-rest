package com.snn.service;

import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snn.Enum.NewsUrlEnum;
import com.snn.dao.NewsDAO;
import com.snn.model.FeedItem;
import com.sun.syndication.feed.module.mediarss.MediaEntryModule;
import com.sun.syndication.feed.module.mediarss.types.UrlReference;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;

@Service
public class NewsServiceImpl implements NewsService
{
    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsDAO newsDAO;

    @Override
    public List<FeedItem> getNewsFromSource()
    {
        List<FeedItem> feedItemList = new ArrayList<FeedItem>();
        for (NewsUrlEnum urlEnum : NewsUrlEnum.values())
        {
            feedItemList.addAll(getNewsFromSource(urlEnum.getValue()));
        }
        return feedItemList;
    }

    private List<FeedItem> getNewsFromSource(Short newsUrlId)
    {
        List<FeedItem> feedItemList = new ArrayList<FeedItem>();
        try
        {
            String url = NewsUrlEnum.getNewsEnum(newsUrlId).getUrl();
            URLConnection feedUrl = new URL(url).openConnection();
            feedUrl.setConnectTimeout(5000);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new com.sun.syndication.io.XmlReader(feedUrl)
            {
            });
            List<SyndEntry> feedList = feed.getEntries();
            int feedSize = feedList.size();
            feedItemList = prepareFeedItemList(newsUrlId, feedList, feedSize);
        }
        catch (Exception e)
        {
            logger.error("getNewsFromSource error:", e);
        }
        return feedItemList;
    }

    private static List<FeedItem> prepareFeedItemList(Short newsUrlId, List<SyndEntry> feedList, int feedSize)
    {
        List<FeedItem> feedItemList = new ArrayList<FeedItem>();
        for (int i = 0; i < feedSize; i++)
        {
            SyndEntry entry = feedList.get(i);
            String title = entry.getTitle();
            String content = entry.getDescription().getValue();
            Date date = entry.getPublishedDate();
            String link = entry.getLink();
            String smallImage = StringUtils.EMPTY;

            if (entry.getModule(MediaEntryModule.URI) != null)
            {
                MediaEntryModule mediaEntryModule = (MediaEntryModule) entry.getModule(MediaEntryModule.URI);
                if (mediaEntryModule.getMediaContents() != null)
                {
                    UrlReference ref = (UrlReference) mediaEntryModule.getMediaContents()[0].getReference();
                    smallImage = ref.getUrl().toString();
                }
            }
            feedItemList.add(new FeedItem(newsUrlId, title, content, date, link, smallImage));
        }
        return feedItemList;
    }

    @Override
    public List<FeedItem> getNewsFromDb()
    {
        return newsDAO.getNewsFromDb();
    }

    @Override
    public void insertFeed(List<FeedItem> feedItemList)
    {
        newsDAO.insertFeed(feedItemList);
    }

    @Override
    public void deleteOldFeed()
    {
        newsDAO.deleteOldFeed();
    }

    public void setNewsDAO(NewsDAO newsDAO)
    {
        this.newsDAO = newsDAO;
    }
}
