package com.example.caloriecounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Stetho
        Stetho.initializeWithDefaults(this);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        // Database
        DBAdapter db = new DBAdapter(this);
        db.open();

        int numberRows = db.count("food");

        if(numberRows < 1){
            // Run setup
            Toast.makeText(this, "Setup......", Toast.LENGTH_SHORT).show();
            DBSetupInsert setupInsert = new DBSetupInsert(this);
            setupInsert.insertAllFood();
            setupInsert.insertAllCategories();
            Toast.makeText(this, "Setup Completed", Toast.LENGTH_SHORT).show();
        }

        // Check user in the table
        numberRows = db.count("users");
        if (numberRows <1 ){
            // Sign up
            Toast.makeText(this, "You are only few fields away from signing up...", Toast.LENGTH_LONG).show();
            Intent i = new Intent(MainActivity.this, sign_up.class);
            startActivity(i);
        }




        db.close();





    }
}