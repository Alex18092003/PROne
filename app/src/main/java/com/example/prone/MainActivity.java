package com.example.prone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    Connection connection;
    String ConnectionResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //public  void  goAdd(View view)
    //{
    //Intent intent = new Intent(this, Add.class);
    //startActivity(intent);
    //}


    public void GetTextFromSQL(View v)
    {
        TextView ID = findViewById(R.id.txtID);
        TextView Name = findViewById(R.id.txtName);
        TextView Surname = findViewById(R.id.txtSurname);
        TextView Age = findViewById(R.id.txtAge);
        TextView Kurs = findViewById(R.id.txtKurs);

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