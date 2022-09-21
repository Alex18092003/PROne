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
    TextView st, txstatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editingg);

        Kod = (EditText) findViewById(R.id.etKod);
        Name = (EditText) findViewById(R.id.etName);
        Surname = (EditText) findViewById(R.id.etSurname);
        Age = (EditText) findViewById(R.id.etAge);
        Kurs = (EditText) findViewById(R.id.etKurs);

       st = (TextView) findViewById(R.id.st);
        txstatus = (TextView) findViewById(R.id.txstatus);


    }

    public  void ChangingData(View view) // редактирование данных в бд, кнопка "Сохранить изменения"
    {
        try {
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = "UPDATE Student SET Name = '"+Name.getText().toString()+"' , Surname = '"+Surname.getText().toString()+"', Age = '"+Age.getText().toString()+"', Kurs = '"+Kurs.getText().toString()+"' where Kod_student = '"+Kod.getText().toString()+"'";
                Statement statement = connection.createStatement();
                //ResultSet resultSet = statement.executeQuery(query);
                statement.executeUpdate(query);
                st.setText("Успешное изменение записи");
                txstatus.setText("Введите код студента, чтобы получить данные о нём");
                Kod.setText("");
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
                txstatus.setText("Успешное удаление записи");
                Kod.setText("");
                Name.setText("");
                Surname.setText("");
                Age.setText("");
                Kurs.setText("");
                st.setText("Введите код студента, чтобы получить данные о нём");

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
                while (resultSet.next())
                {
                    Name.setText(resultSet.getString(2));
                    Surname.setText(resultSet.getString(3));
                    Age.setText(resultSet.getString(4));
                   Kurs.setText(resultSet.getString(5));

                }
                st.setText("Успешное получение данных");
                txstatus.setText("...");

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
            txstatus.setText("...");
            st.setText("Введите код студента, чтобы получить данные о нём");
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }

    }
}