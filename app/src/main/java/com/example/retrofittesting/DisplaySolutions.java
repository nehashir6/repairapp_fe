package com.example.retrofittesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplaySolutions extends AppCompatActivity {

    ListView superListView;
    FloatingActionButton button;
    Integer problemIndex;
    String modelNo;
    String problem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solutions);

        button = findViewById(R.id.fabButton);
        superListView = findViewById(R.id.superListView);

        //retrieving model no. and problem index from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        problemIndex = extras.getInt("problemIndex");
        modelNo = extras.getString("modelNumber");
        problem = extras.getString("problemDescription");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddSolution.class);
                Bundle extras = new Bundle();
                extras.putString("modelNumber", modelNo);
                extras.putString("problemDescription", problem);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        getSolutionList();
    }

    private void getSolutionList() {

        Call<List<Results>> call = RetrofitClient.getInstance().getMyApi().getProblemsByModelNo(modelNo);

        call.enqueue(new Callback<List<Results>>() {
            @Override
            public void onResponse(Call<List<Results>> call, Response<List<Results>> response) {
                List<Results> problemList = response.body();

                List<Solution> solutionList = problemList.get(problemIndex).getSolutionList();
                String[] mySolutions = new String[solutionList.size()];

                for (int i = 0; i < solutionList.size(); i++) {
                    mySolutions[i] = solutionList.get(i).getSolution();
                }

                superListView.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.solutionrow, mySolutions));
            }

            @Override
            public void onFailure(Call<List<Results>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }

        });

    }
}