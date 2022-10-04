package com.example.prone;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.Set;

public class MainActivity extends AppCompatActivity  {

    Connection connection;
    String ConnectionResult = "";
    SimpleAdapter adapter;
    GridView list;
    private AdapterMask pAdapter;
    private List<Mask> listProduct = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView list = (GridView) findViewById(R.id.gridViewTable);
        pAdapter = new AdapterMask(MainActivity.this, listProduct);
        list.setAdapter(pAdapter);
        new GetProducts().execute();
    }


    public  void  goAdd(View view) // переход в окно добавления новой записи, кнопка "Добавить запись"
    {
    Intent intent = new Intent(this, Add.class);
    startActivity(intent);
    }

    public  void goEditing(View view) // переход в окно редактирования данных, кнопка "Редактировать"
    {
        Intent intent = new Intent(this, editingg.class);
        startActivity(intent);
    }

    public  void GetTableSql(View view) // вывод БД, кнопка "Загрузить БД"
    {
                List <Map<String, String>> data = new ArrayList<Map<String, String>>();
                try
                {
                    ConnectionHelpers connectionHelpers = new ConnectionHelpers();
                    connection = connectionHelpers.connectionClass();
                    if (connection !=null)
                    {
                        String query = "Select * From Student";
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(query);
                        while (resultSet.next())
                        {
                            Map<String, String> tab = new HashMap<String, String>();
                            tab.put("Kod_student", resultSet.getString("Kod_student"));
                            tab.put("Name", resultSet.getString("Name"));
                            tab.put("Surname", resultSet.getString("Surname"));
                            tab.put("Age", resultSet.getString("Age"));
                            tab.put("Kurs", resultSet.getString("Kurs"));
                            tab.put("Images", resultSet.getString("Images"));
                            data.add(tab);

                        }
                        String [] from = {"Kod_student", "Name", "Surname", "Age", "Kurs", "Images"};
                        int [] to = {R.id.Kod_student, R.id.Name, R.id.Surname, R.id.Age, R.id.Kurs , R.id.imageV};

                        adapter = new SimpleAdapter(MainActivity.this, data, R.layout.gridvewtable, from, to);
                        list.setAdapter(adapter);

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

    public  void CleaningOfAllFields(View v) // очистка таблицы бд, кнопка "Очистить"
    {
        try {
           list.setAdapter(null);

        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }

    }

}



