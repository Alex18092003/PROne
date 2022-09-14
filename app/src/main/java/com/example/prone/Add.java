package com.example.prone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import android.widget.Button;
import android.widget.EditText;

public class Add extends AppCompatActivity {

    EditText Name, Surname, Age, Kurs;
    Button Back, Add;
    Connection connection;
    String ConnectionResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }
    public  void  goBack(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Name = (EditText) findViewById(R.id.etName);
        Surname = (EditText) findViewById(R.id.etSurname);
        Age = (EditText) findViewById(R.id.etAge);
        Kurs = (EditText) findViewById(R.id.etKurs);
        Back = (Button) findViewById(R.id.btnBack);
        Add = (Button) findViewById(R.id.btnAd);
    }

    public void AddingDataFromSQL(View v)
    {
        try {
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = "Select * From Student";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next())
                {
                    ID.setText(resultSet.getString(1));
                    Name.setText((resultSet.getString(2)));
                    Surname.setText((resultSet.getString(3)));
                    Age.setText((resultSet.getString(4)));
                    Kurs.setText((resultSet.getString(5)));
                }
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