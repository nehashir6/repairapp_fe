package com.example.retrofittesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModelNumbers extends AppCompatActivity {

    ListView superListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelnumbers);

        superListView = findViewById(R.id.superListView);

        getModelNumbers();
    }

    private void getModelNumbers() {
        Call<String> call = RetrofitClient.getInstance().getMyApi().getModelNumbers();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i ("hello", response.body());
                String modelNumberList = response.body().substring(1, response.body().length()-1);
                String[] st = modelNumberList.replaceAll("\"", "").split(",");

                superListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.modelrow, st));

                //adding click events
                superListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        //passing model no.to problem activity
                        String modelNo = (String) (superListView.getItemAtPosition(position));
                        Intent intent = new Intent(getApplicationContext(), DisplayProblems.class);
                        intent.putExtra("modelNumber", modelNo);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }
        });
    }
}