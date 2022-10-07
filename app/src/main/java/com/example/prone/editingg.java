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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class editingg extends AppCompatActivity {

    EditText Kod, Name, Surname, Age, Kurs;
    ImageView Picture;
    Connection connection;
    String ConnectionResult = "";
    TextView st;
    Mask mask;
    View v;
    String img="";
    Integer index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editingg);

        st = findViewById(R.id.txstatus);
        Name =  findViewById(R.id.etName);
        Surname =  findViewById(R.id.etSurname);
        Age =  findViewById(R.id.etAge);
        Kurs =  findViewById(R.id.etKurs);
        Picture=findViewById(R.id.Picture);
        GetData();

    }
    public void GetData()
    {
        try
        {
            mask=getIntent().getParcelableExtra("Student");
            Name.setText(mask.getName());
            Surname.setText(mask.getSurname());
            Age.setText(Integer.toString(mask.getAge()));
            Kurs.setText(Integer.toString(mask.getKurs()));
            Picture.setImageBitmap(toBip(mask.getImages()));


        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
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
                toStr(bitmap);

            }
        }
    }

    public String toStr(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            img= Base64.getEncoder().encodeToString(b);
            return img;
        }
        return "";
    }


    public Bitmap toBip(String encodedImg)
    {
        if (encodedImg != null && !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(editingg.this.getResources(),
                R.drawable.nophoto);


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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void deletePicture(View v)
    {
        Picture.setImageBitmap(null);
        Picture.setImageResource(R.drawable.nophoto);
    }
    public  void  goBack(View view) // выход в главное меню, кнопка "Назад"
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}