package com.snn.service;

import java.util.List;

import com.snn.model.FeedItem;

public interface NewsService
{
    public List<FeedItem> getNewsFromSource();

    public List<FeedItem> getNewsFromDb();

    public void insertFeed(List<FeedItem> feedItemList);

    public void deleteOldFeed();
}
