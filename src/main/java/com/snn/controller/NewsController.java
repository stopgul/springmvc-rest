package com.snn.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snn.model.FeedItem;
import com.snn.service.NewsService;

//@Controller olursa ve method değer döndürüyorsa başına @ResponseBody eklenmelidir
@RestController
// @RequestMapping("/{userId}/bookmarks")
public class NewsController
{
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @RequestMapping(value = "/updateNewsFromSource", method = RequestMethod.GET)
    public String updateNewsFromSource()
    {
        String message = "OK";
        try
        {
            List<FeedItem> feedItemList = newsService.getNewsFromSource();
            newsService.insertFeed(feedItemList);
            logger.info("updateNewsFromSource basarili: ");
        }
        catch (Exception e)
        {
            logger.error("updateNewsFromSource ta hata: ", e);
            message = e.getMessage();
        }

        return message;
    }

    @RequestMapping(value = "/getNewsFromDb", method = RequestMethod.GET)
    public List<FeedItem> getNewsFromDb()
    {
        List<FeedItem> feedItemList = null;
        try
        {
            feedItemList = newsService.getNewsFromDb();
            logger.info("getNewsFromSource basarili: ");
        }
        catch (Exception e)
        {
            logger.error("getNewsFromDb de hata: ", e);
        }

        return feedItemList;
    }

    @RequestMapping(value = "/deleteOldNews", method = RequestMethod.GET)
    public String deleteOldNews()
    {
        String message = "OK";
        try
        {
            newsService.deleteOldFeed();
            logger.info("DeleteOldNews basarili: ");
        }
        catch (Exception e)
        {
            logger.error("DeleteOldNews te hata: ", e);
            message = e.getMessage();
        }

        return message;
    }
}
