package com.example.prone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Add extends AppCompatActivity {

    EditText Name, Surname, Age, Kurs;
    TextView Status;
    Button Back, Add;
    Connection connection;
    String ConnectionResult = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Name = (EditText) findViewById(R.id.etName);
        Surname = (EditText) findViewById(R.id.etSurname);
        Age = (EditText) findViewById(R.id.etAge);
        Kurs = (EditText) findViewById(R.id.etKurs);
        Back = (Button) findViewById(R.id.btnBack);
        Add = (Button) findViewById(R.id.btnAd);
        Status = (TextView) findViewById(R.id.status);


    }
    public  void  goBack(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }


    public void AddingDataFromSQL(View v)
    {
        try {
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = "INSERT INTO Student (Name, Surname, Age, Kurs) VALUES ('"+Name.getText()+"', '"+Surname.getText()+"', '"+Age.getText()+"', '"+Kurs.getText()+"')";

                Statement statement = connection.createStatement();

                statement.executeUpdate(query);
                Status.setText("Данные успешно сохранены в БД");
                Name.setText("");
                Surname.setText("");
                Age.setText("");
                Kurs.setText("");


            }
            else
            {
                ConnectionResult="Check Connection";
            }
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
    }


}