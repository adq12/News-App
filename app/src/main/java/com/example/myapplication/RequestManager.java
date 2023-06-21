package com.example.myapplication;

import android.content.Context;
import android.widget.Toast;

import com.example.myapplication.Model.NewsAPIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {

    String BASE_URL="https://newsapi.org/v2/";
    Context context;
    Retrofit retrofit= new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public void getNewsHeadlines(onFetchDataListner listner,String category, String query,String country){
        CallNewsApi callNewsApi=retrofit.create(CallNewsApi.class);
        Call<NewsAPIResponse> call= callNewsApi.callHeadlines(country,category,query,context.getString(R.string.api_key));

        try {
            call.enqueue(new Callback<NewsAPIResponse>() {
                @Override
                public void onResponse(Call<NewsAPIResponse> call, Response<NewsAPIResponse> response) {
                    if(!response.isSuccessful()){
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                    listner.onFetchData(response.body().getArticles(),response.message());

                }

                @Override
                public void onFailure(Call<NewsAPIResponse> call, Throwable t) {
                    listner.onError("Request Failed");
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public RequestManager(Context context) {
        this.context = context;
    }
    public interface CallNewsApi{
        @GET("top-headlines")
        Call<NewsAPIResponse> callHeadlines(
                @Query("country") String country,
                @Query("category") String category,
                @Query("q") String q,
                @Query("apikey") String api_key
        );
    }
}
