package com.example.retrofittesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayProblems extends AppCompatActivity {

    ListView superListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problems);

        superListView = findViewById(R.id.superListView);

        getProblemsByModelNo();
    }

    private void getProblemsByModelNo() {
        Intent intent = getIntent();
        String modelNo = intent.getStringExtra("modelNumber"); //retrieving model no. from previous activity

        Call<List<Results>> call = RetrofitClient.getInstance().getMyApi().getProblemsByModelNo(modelNo);

        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                List<Results> problemList = response.body();
                String[] myProblems = new String[problemList.size()];

                for (int i = 0; i < problemList.size(); i++) {
                    myProblems[i] = problemList.get(i).getProblemDescription();
                }

                superListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.problemrow, myProblems));

                //adding click events
                superListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //passing problem index and model no. to solution activity
                        Intent intent = new Intent(getApplicationContext(), DisplaySolutions.class);
                        Bundle extras = new Bundle();
                        extras.putInt("problemIndex", position);
                        extras.putString("modelNumber", modelNo);
                        extras.putString("problemDescription", myProblems[position]);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }

        });

    }
}