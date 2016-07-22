package com.snn.dao;

import java.util.List;

import com.snn.model.FeedItem;

public interface NewsDAO
{

    public List<FeedItem> getNewsFromDb();

    public void insertFeed(List<FeedItem> feedItemList);

    public void deleteOldFeed();
}
