package com.example.prone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class editingg extends AppCompatActivity {

    EditText Kod, Name, Surname, Age, Kurs;
    Connection connection;
    String ConnectionResult = "";
    TextView status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editingg);

        Kod = (EditText) findViewById(R.id.etKod);
        Name = (EditText) findViewById(R.id.etName);
        Surname = (EditText) findViewById(R.id.etSurname);
        Age = (EditText) findViewById(R.id.etAge);
        Kurs = (EditText) findViewById(R.id.etKurs);


    }

    public  void ChangingData(View view) // редактирование данных в бд, кнопка "Сохранить изменения"
    {
        try {
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = "Update Student set (Kod_student = '"+Kod.getText()+"',Name = '"+Name.getText()+"' , Surname = '"+Surname.getText()+"', Age = '"+Age.getText()+"', Kurs = '"+Kurs.getText()+"')";
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                status.setText("Успешное изменение записи");
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

    public  void DeleteData(View view) // удаление записи из бд, кнопка "Удалить"
    {
        try {
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = "Delete From Student where Kod_student = '"+Kod.getText()+"' ";
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                status.setText("Успешное удаление записи");
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

    public  void GetData(View view) // получение данных по их коду, кнопка "Получить"
    {
        try {
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = "Select * From Student where Kod_student = '" + Kod.getText().toString() + "' ";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                //statement.executeUpdate(query);
                while (resultSet.next())
                {
                    Name.setText(resultSet.getString(2));
                    Surname.setText(resultSet.getString(3));
                    Age.setText(resultSet.getString(4));
                   Kurs.setText(resultSet.getString(5));

                }
                status.setText("Успешное получение данных");
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


    public  void  goBack(View view) // выход в главное меню, кнопка "Назад"
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public  void CleaningOfAllFields(View v) // очистка всех полей, кнопка "Очистить"
    {
        try {
            Kod.setText("");
            Name.setText("");
            Surname.setText("");
            Age.setText("");
            Kurs.setText("");
            status.setText("...");
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }

    }
}