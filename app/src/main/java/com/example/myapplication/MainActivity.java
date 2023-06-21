package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.myapplication.Model.NewsAPIResponse;
import com.example.myapplication.Model.NewsHeadlines;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity implements SelectListener, AdapterView.OnItemSelectedListener {
    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog dialog;
   TabLayout tabLayout;

   Spinner spinner;
    String choice;
    String category="business";
   String[] country={"ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","fr","gb","gr","hk","hu","id","ie","il","in","us"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog=new ProgressDialog(this);
        dialog.setTitle("Fetching new Articles..");
        dialog.show();
        RequestManager manager=new RequestManager(this);
        manager.getNewsHeadlines(listner,category,null,"us");
        ArrayAdapter co_ad= new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,country);
        spinner=findViewById(R.id.select_country);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(co_ad);


        tabLayout=findViewById(R.id.category_select);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                 category=tab.getText().toString();
                dialog.setTitle("Fetching news article of "+category);
                dialog.show();
                RequestManager manager=new RequestManager(MainActivity.this);
                manager.getNewsHeadlines(listner,category,null,choice);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private final onFetchDataListner<NewsAPIResponse> listner= new onFetchDataListner<NewsAPIResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            showNews(list);
            dialog.dismiss();
        }

        @Override
        public void onError(String message) {

        }
    };

    private void showNews(List<NewsHeadlines> list) {
        recyclerView=findViewById(R.id.recycler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter=new CustomAdapter(this,list,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnNewsClicked(NewsHeadlines headlines) {
        startActivity(new Intent(MainActivity.this,DetailsActivity.class).putExtra("data",headlines));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
         choice=adapterView.getItemAtPosition(position).toString();
        dialog.setTitle("Fetching news article of "+choice);
        dialog.show();
        RequestManager manager=new RequestManager(MainActivity.this);
        manager.getNewsHeadlines(listner,category,null,choice);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}