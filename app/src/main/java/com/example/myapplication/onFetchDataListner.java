package com.example.myapplication;

import com.example.myapplication.Model.NewsHeadlines;

import java.util.List;

public interface onFetchDataListner<NewsApiResponse> {
    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);
}
