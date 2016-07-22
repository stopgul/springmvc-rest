package com.snn.job;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snn.model.FeedItem;
import com.snn.service.NewsService;

@Component("newsJob")
public class NewsJob
{
    private static final Logger logger = LoggerFactory.getLogger(NewsJob.class);

    @Autowired
    private NewsService newsService;

    public void UpdateNews()
    {
        logger.debug("UpdateNews job started.");
        List<FeedItem> feedItemList = newsService.getNewsFromSource();
        newsService.insertFeed(feedItemList);
        logger.debug("UpdateNews job finished.");
    }

    public void DeleteOldNews()
    {
        logger.debug("DeleteOldNews job started.");
        newsService.deleteOldFeed();
        logger.debug("DeleteOldNews job finished.");
    }

    public void setNewsService(NewsService newsService)
    {
        this.newsService = newsService;
    }
}
