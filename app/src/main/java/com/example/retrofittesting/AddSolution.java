package com.example.retrofittesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSolution extends AppCompatActivity {

    EditText editText;
    Button button;
    String modelNo;
    String problem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_solution);

        editText = findViewById(R.id.editTextTextMultiLine2);
        button = findViewById(R.id.button3);

        //retrieving model no. and problem description from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        modelNo = extras.getString("modelNumber");
        problem = extras.getString("problemDescription");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSolution();
            }
        });


    }

    private void addSolution() {

        String myFix = editText.getText().toString();
        Solution s = new Solution();
        s.setSolution(myFix);
        List<Solution> listSoln = new ArrayList<Solution>();
        listSoln.add(s);


        SolutionListModel slm = new SolutionListModel();
        slm.setSolutionList(listSoln);


        Call<Results> call = RetrofitClient.getInstance().getMyApi().addSolution(modelNo, problem, slm);

        call.enqueue(new Callback<Results>() {
            @Override
            public void onResponse(Call<Results> call, Response<Results> response) {
                Toast.makeText(getApplicationContext(), "Solution Added!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ModelNumbers.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Results> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(), "An error has occurred", Toast.LENGTH_LONG).show();
            }
        });



    }
}