package com.example.prone;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
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
    Spinner spinner;
    View v;
    List<Mask> data;
    ListView listView;
    AdapterMask pAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        v = findViewById(com.google.android.material.R.id.ghost_view);
        GetTableSql(v);
        Sort();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            MenuItem menuItem = menu.findItem(R.id.action);
            SearchView searchView = (SearchView) menuItem.getActionView();

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    data = new ArrayList<Mask>();
        listView = findViewById(R.id.gridViewTable);
        pAdapter = new AdapterMask(MainActivity.this, data);
        try {
            String query="";
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection != null) {
                query = "Select * From Student WHERE Surname like'%"+s+"%'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Mask tempMask = new Mask
                            (resultSet.getInt("Kod_student"),
                                    Integer.parseInt(resultSet.getString("Age")),
                                    Integer.parseInt(resultSet.getString("Kurs")),
                                    resultSet.getString("Name"),
                                    resultSet.getString("Surname"),
                                    resultSet.getString("Images"));
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            }
            else {
                ConnectionResult="Check Connection";
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }
        enterMobile();
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s1) {

                    data = new ArrayList<Mask>();
        listView = findViewById(R.id.gridViewTable);
        pAdapter = new AdapterMask(MainActivity.this, data);
        try {
            String query="";
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection != null) {
                query = "Select * From Student WHERE Surname like'%"+s1+"%'";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                while (resultSet.next()) {
                    Mask tempMask = new Mask
                            (resultSet.getInt("Kod_student"),
                                    Integer.parseInt(resultSet.getString("Age")),
                                    Integer.parseInt(resultSet.getString("Kurs")),
                                    resultSet.getString("Name"),
                                    resultSet.getString("Surname"),
                                    resultSet.getString("Images"));
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            }
            else {
                ConnectionResult="Check Connection";
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }
        enterMobile();
                    return false;
                }
            });
            return super.onCreateOptionsMenu(menu);

    }

//    public  void  Search(String st)
//    {
//        data = new ArrayList<Mask>();
//        listView = findViewById(R.id.gridViewTable);
//        pAdapter = new AdapterMask(MainActivity.this, data);
//        try {
//            String query="";
//            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
//            connection = connectionHelpers.connectionClass();
//            if (connection != null) {
//                query = "Select * From Student WHERE Surname like'%"+st+"%'";
//                Statement statement = connection.createStatement();
//                ResultSet resultSet = statement.executeQuery(query);
//                while (resultSet.next()) {
//                    Mask tempMask = new Mask
//                            (resultSet.getInt("Kod_student"),
//                                    Integer.parseInt(resultSet.getString("Age")),
//                                    Integer.parseInt(resultSet.getString("Kurs")),
//                                    resultSet.getString("Name"),
//                                    resultSet.getString("Surname"),
//                                    resultSet.getString("Images"));
//                    data.add(tempMask);
//                    pAdapter.notifyDataSetInvalidated();
//                }
//                connection.close();
//            }
//            else {
//                ConnectionResult="Check Connection";
//            }
//        }
//        catch (Exception ex)
//        {
//            Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
//        }
//        enterMobile();
//    }

    public void Sort() //сортировка
    {
        try {
            spinner = findViewById(R.id.Spinner);
            List<String> list = new ArrayList<String>();
            list.add("Без фильтра");
            list.add("Сортировка по именам");
            list.add("Сортировка по возрастанию возрасту");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            String s = null;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    switch (position) {
                        case 0: {
                            sortByBez(s);

                        }
                        break;
                        case 1: {
                            sortByName(s);

                        }
                        break;
                        case 2: {
                            sortByAge(s);
                        }
                        break;
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }

            });
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }
    }


    public  void  sortByBez(String s) //запрос на сортировку
    {
        try {
            s = "Select * From Student";
            selectSort(s);
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }
    }
public  void  sortByName(String s)//запрос на сортировку
{
    try {
        s = "Select * From Student ORDER BY Name";
        selectSort(s);
    }
    catch (Exception ex)
    {
        Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
    }
}

    public  void  sortByAge(String s)//запрос на сортировку
    {
        try {
            s = "Select * From Student ORDER BY Age";
            selectSort(s);
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }
    }

    public  void  selectSort(String s)//вывод сортировки
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
                String query = s;
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
            Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }
        enterMobile();
    }



    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }

    public  void  goAdd(View view) // переход в окно добавления новой записи, кнопка "Добавить запись"
    {
        try {
            Intent intent = new Intent(this, Add.class);
            startActivity(intent);
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
        }
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
                    Toast.makeText(MainActivity.this,"Что-то пошло не так", Toast.LENGTH_LONG).show();
                }
        enterMobile();
    }

}



