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
import android.widget.TextView;

public class Add extends AppCompatActivity {

    EditText Name, Surname, Age, Kurs;
    TextView status;

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
        status = (TextView) findViewById(R.id.status);
    }

    public  void  goBack(View view) // выход в главное меню, кнопка "Назад"
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void AddingDataFromSQL(View v) // добавление новой записи в БД, кнопка "Добавить"
    {
        try {
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = "INSERT INTO Student (Name, Surname, Age, Kurs) VALUES ('"+Name.getText()+"', '"+Surname.getText()+"', '"+Age.getText()+"', '"+Kurs.getText()+"')";
                Statement statement = connection.createStatement();
                //ResultSet resultSet = statement.executeQuery(query);
                statement.executeUpdate(query);
                status.setText("Успешное добаление новых записи");

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

    public  void CleaningOfAllFields(View v) // очистка всех полей, кнопка "Очистить"
    {
        try {
            Name.setText("");
            Surname.setText("");
            Age.setText("");
            Kurs.setText("");
            status.setText("Введите данные");
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }

    }



}