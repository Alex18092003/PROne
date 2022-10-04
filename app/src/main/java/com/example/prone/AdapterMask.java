package com.example.prone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AdapterMask  extends BaseAdapter{
    private Context mContext;
    private ArrayList<Mask> maskList;
    String img="";


    public AdapterMask(Context mContext, List<Mask> listProduct) {
        this.mContext = mContext;
        this.maskList = listProduct;
    }

    @Override
    public int getKurs() {
        return maskList.size();
    }
    @Override
    public int getAge() {
        return maskList.size();
    }
    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getID();
    }
    private Bitmap getUserImage(String encodedImg)
    {

        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
        {
            return null;

        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = View.inflate(mContext,R.layout.gridvewtable,null);
        TextView Name = v.findViewById(R.id.Name);
        TextView Surname = v.findViewById(R.id.Surname);
        TextView Age = v.findViewById(R.id.Age);
        TextView Kurs = v.findViewById(R.id.Kurs);
        ImageView imageView = v.findViewById(R.id.imageV);
        Mask mask = maskList.get(i);
        Name.setText(mask.getName());
        Surname.setText(mask.getSurname());
        Age.setText(Integer.toString(mask.getAge2()));
        Kurs.setText(Integer.toString(mask.getKurs2()));
        //Count.setText(Integer.toString(mask.getMinCostForAgent()));
        if(mask.getID()!=4){int c =10;}
        else{
            int a = 5;}
        //  imageView.setImageBitmap(getUserImage(mask.get()));


        return v;
    }
}
