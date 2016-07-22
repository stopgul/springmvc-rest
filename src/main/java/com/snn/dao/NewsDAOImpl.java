package com.snn.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.snn.model.FeedItem;

@Repository("newsDAO")
public class NewsDAOImpl implements NewsDAO
{
    private static final Logger logger = LoggerFactory.getLogger(NewsDAOImpl.class);

    private MongoOperations mongoOps;
    private static final String FEED_ITEM_COLLECTION = "feed";

    public NewsDAOImpl(MongoOperations mongoOps)
    {
        this.mongoOps = mongoOps;
    }

    @Override
    public List<FeedItem> getNewsFromDb()
    {
        logger.debug("getNewsFromDb");
        List<FeedItem> feedItemList = mongoOps.find(null, FeedItem.class, FEED_ITEM_COLLECTION);
        return feedItemList;
    }

    @Override
    public void insertFeed(List<FeedItem> feedItemList)
    {
        for (FeedItem feedItem : feedItemList)
        {
            try
            {
                if (!hasSameFeed(feedItem))
                {
                    mongoOps.insert(feedItem, FEED_ITEM_COLLECTION);
                }
            }
            catch (Exception e)
            {
                logger.error("insertFeed de hata: ", e);
            }
        }
    }

    private boolean hasSameFeed(FeedItem feedItem)
    {
        boolean result = true;
        Query query = new Query(Criteria.where("newsUrlId").is(feedItem.getNewsUrlId()).and("link").is(feedItem.getLink()));
        result = mongoOps.exists(query, FeedItem.class, FEED_ITEM_COLLECTION);

        return result;
    }

    @Override
    public void deleteOldFeed()
    {
        logger.debug("deleteOldFeed");
        Date oldDate = DateUtils.addDays(new Date(), -2);
        Query query = new Query(Criteria.where("date").lt(oldDate));
        mongoOps.remove(query, FEED_ITEM_COLLECTION);
    }
}
