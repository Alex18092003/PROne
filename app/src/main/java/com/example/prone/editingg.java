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
    ImageView imageView;
    Connection connection;
    String ConnectionResult = "";
    TextView st, txstatus;
    Mask mask;
    View v;
    String img="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editingg);

        mask=getIntent().getParcelableExtra("Student");
        Kod = (EditText) findViewById(R.id.etKod);
        Name = (EditText) findViewById(R.id.etName);
        Surname = (EditText) findViewById(R.id.etSurname);
        Age = (EditText) findViewById(R.id.etAge);
        Kurs = (EditText) findViewById(R.id.etKurs);
        st = (TextView) findViewById(R.id.st);
        txstatus = (TextView) findViewById(R.id.txstatus);
        imageView=findViewById(R.id.imag);
        v =findViewById(com.google.android.material.R.id.ghost_view);

        GetData(v);
    }

    private Bitmap getImgBitmap(String encodedImg) {
        if (encodedImg != null) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(editingg.this.getResources(),
                R.drawable.ic_launcher_foreground);
    }

    public void onClickChooseImage(View view)
    {
        getImage();

    }
    private void getImage()
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
                imageView.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                encodeImage(bitmap);

            }
        }
    }

    private String encodeImage(Bitmap bitmap) {
        int prevW = 150;
        int prevH = bitmap.getHeight() * prevW / bitmap.getWidth();
        Bitmap b = Bitmap.createScaledBitmap(bitmap, prevW, prevH, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img=Base64.getEncoder().encodeToString(bytes);
            return img;
        }
        return "";
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

}