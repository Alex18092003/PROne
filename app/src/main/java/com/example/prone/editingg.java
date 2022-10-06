package com.example.prone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Base64;


public class editingg extends AppCompatActivity {

    EditText Kod, Name, Surname, Age, Kurs;
    ImageView Picture;
    Connection connection;
    String ConnectionResult = "";
    TextView st;
    Mask mask;
    View v;
    String img="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editingg);

        mask=getIntent().getParcelableExtra("Student");
        Name = (EditText) findViewById(R.id.etName);
        Name.setText(mask.getName());
        Surname = (EditText) findViewById(R.id.etSurname);
        Surname.setText(mask.getSurname());
        Age = (EditText) findViewById(R.id.etAge);
        Age.setText(mask.getAge());
        Kurs = (EditText) findViewById(R.id.etKurs);
        Kurs.setText(mask.getKurs());

        Picture=findViewById(R.id.Picture);
        Kurs.setText(mask.getImages());
        v =findViewById(com.google.android.material.R.id.ghost_view);

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




    public  void ChangingData(View view) // редактирование данных в бд, кнопка "Сохранить изменения"
    {
        if(Name.getText().length() == 0 || Surname.getText().length() == 0 || Age.getText().length() == 0|| Kurs.getText().length() == 0)
        {
            st.setText("Все поля должны быть заполнены");
            return;
        }
        else {
            try {
                ConnectionHelpers connectionHelpers = new ConnectionHelpers();
                connection = connectionHelpers.connectionClass();
                if (connection != null) {
                    String query = "UPDATE Student SET Name = '" + Name.getText().toString() + "' , Surname = '" + Surname.getText().toString() + "', Age = '" + Age.getText().toString() + "', Kurs = '" + Kurs.getText().toString() + "' , Images = '" + img + "' where Kod_student = '" + mask.getID() + "'";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);
                    st.setText("Успешное изменение записи");
                } else {
                    ConnectionResult = "Check Connection";
                }
            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());
            }
        }
    }

    public  void DeleteData(View view) // удаление записи из бд, кнопка "Удалить"
    {
        try {
            ConnectionHelpers connectionHelpers = new ConnectionHelpers();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = "Delete From Student where Kod_student = '"+mask.getID()+"' ";
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                Kod.setText("");
                Name.setText("");
                Surname.setText("");
                Age.setText("");
                Kurs.setText("");
                Picture.setImageResource(R.drawable.nophoto);
                st.setText("Успешное удаление всех данных");

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