package com.example.prone;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.List;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Add extends AppCompatActivity {

    EditText Name, Surname, Age, Kurs;
    TextView status;
    ImageView Picture;
    Connection connection;
    String ConnectionResult = "";
    AdapterMask pAdapter;
    ListView listView;
    String img="";
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Name = (EditText) findViewById(R.id.etName);
        Surname = (EditText) findViewById(R.id.etSurname);
        Age = (EditText) findViewById(R.id.etAge);
        Kurs = (EditText) findViewById(R.id.etKurs);
        status = (TextView) findViewById(R.id.status);
        Picture = (ImageView) findViewById(R.id.Picture);
    }

    public void onClickImage(View view)
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && data!= null && data.getData()!= null)
        {
            if(resultCode==RESULT_OK)
            {
                Log.d("MyLog","Image URI : "+data.getData());
                Picture.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)Picture.getDrawable()).getBitmap();
                encodeImg(bitmap);

            }
        }
    }

    public String encodeImg(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img= Base64.getEncoder().encodeToString(b);
            return img;
        }
        return "";
    }

    public void AddingDataFromSQL(View v) // добавление новой записи в БД, кнопка "Добавить"
    {
        if(Name.getText().length() == 0 || Surname.getText().length() == 0 || Age.getText().length() == 0|| Kurs.getText().length() == 0)
        {
            status.setText("Все поля должны быть заполнены");
            return;
        }
        else {
            try {


                ConnectionHelpers connectionHelpers = new ConnectionHelpers();
                connection = connectionHelpers.connectionClass();
                if (connection != null) {
                    String query = "INSERT INTO Student (Name, Surname, Age, Kurs, Images) VALUES ('" + Name.getText() + "', '" + Surname.getText() + "', '" + Age.getText() + "', '" + Kurs.getText() + "' , '" + img + "')";
                    Statement statement = connection.createStatement();
                    //ResultSet resultSet = statement.executeQuery(query);
                    statement.executeUpdate(query);
                    status.setText("Успешное добаление новых записи");

                } else {
                    ConnectionResult = "Check Connection";
                }


            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());
            }
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
            Picture.setImageResource(R.drawable.nophoto);
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }

    }

    public void deletePicture(View v)
    {
        ImageView Picture = (ImageView) findViewById(R.id.Picture);
        Picture.setImageBitmap(null);
        Picture.setImageResource(R.drawable.nophoto);
    }

    public  void  goBack(View view) // выход в главное меню, кнопка "Назад"
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}