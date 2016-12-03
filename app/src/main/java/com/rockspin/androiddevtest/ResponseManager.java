package com.rockspin.androiddevtest;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import retrofit2.Response;

public class ResponseManager extends AsyncTask<Void,Void,Void>{
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private Context context;
    List<CosmonautActivity> ideaList;

    public ResponseManager(Context context,  RecyclerView recyclerView) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        this.recyclerView = recyclerView;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            Response<List<CosmonautActivity>> response = MyApplication.getAPIService().getEVList().execute();
            ideaList = response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        EVActivityAdapter adapter = new  EVActivityAdapter(context, ideaList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
}
