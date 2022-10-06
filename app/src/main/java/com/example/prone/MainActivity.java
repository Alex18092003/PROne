package com.example.prone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.util.Set;

public class MainActivity extends AppCompatActivity  {

    Connection connection;
    String ConnectionResult = "";
    View v;
    List<Mask> data;
    ListView listView;
    AdapterMask pAdapter;
    Context nContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        v = findViewById(com.google.android.material.R.id.ghost_view);
        GetTableSql(v);


    }



    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }

    public  void  goAdd(View view) // переход в окно добавления новой записи, кнопка "Добавить запись"
    {
    Intent intent = new Intent(this, Add.class);
    startActivity(intent);
    }



    public  void GetTableSql(View view) // вывод БД
    {
        data = new ArrayList<Mask>();
        listView = findViewById(R.id.gridViewTable);
        pAdapter = new AdapterMask(MainActivity.this, data);
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
                            Mask tempMask = new Mask
                                    (resultSet.getInt("Kod_student"),
                                            Integer.parseInt(resultSet.getString("Age")),
                                            Integer.parseInt(resultSet.getString("Kurs")),
                                            resultSet.getString("Name"),
                                            resultSet.getString("Surname"),
                                            resultSet.getString("Images")

                                            );
                            data.add(tempMask);
                            pAdapter.notifyDataSetInvalidated();
                        }
                        connection.close();
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
        enterMobile();
    }

}



