package com.example.prone;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
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
    private ArrayList<Mask> mListMask;
    private OnItemClickListener mListener;
    String img="";

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }
    public AdapterMask(Context mContext, List<Mask> maskList) {
        this.mContext = mContext;
        this.maskList = maskList;
    }
    List<Mask> maskList;



    @Override
    public int getCount() {
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

    public static Bitmap loadContactPhoto(ContentResolver cr, long id, Context context) {
        Uri uri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, id);
        InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(cr, uri);
        if (input == null) {
            Resources res = context.getResources();
            return BitmapFactory.decodeResource(res, R.drawable.nophoto);
        }
        return BitmapFactory.decodeStream(input);
    }

    public Bitmap getUserImage(String encodedImg)
    {
        if(encodedImg!=null && !encodedImg.equals("null")) {
            byte[] bytes = Base64.decode(encodedImg, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        else
            return BitmapFactory.decodeResource(mContext.getResources(), R.drawable.nophoto);
    }



    @Override
    public View getView(int p, View convertView, ViewGroup parent)
    {
        View v = View.inflate(mContext,R.layout.gridvewtable,null);

        TextView Name = v.findViewById(R.id.Name);
        TextView Surname = v.findViewById(R.id.Surname);
        TextView Age = v.findViewById(R.id.Age);
        TextView Kurs = v.findViewById(R.id.Kurs);
        ImageView Images = v.findViewById(R.id.imageView);

        Mask mask = maskList.get(p);
        Name.setText(mask.getName());
        Surname.setText(mask.getSurname());
        Age.setText(Integer.toString(mask.getAge()));
        Kurs.setText(Integer.toString(mask.getKurs()));
        Images.setImageBitmap(getUserImage(mask.getImages()));

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(mContext,editingg.class);
                intent.putExtra("Student",mask);
                mContext.startActivity(intent);

            }
        });

        return v;
    }

}
