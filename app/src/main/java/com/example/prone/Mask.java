package com.example.prone;


import android.os.Parcel;
import android.os.Parcelable;

public class Mask  implements Parcelable{
    private int ID;
    private int Age;
    private int Kurs;
    private String Surname;
    private String Name;
    private String Image;

    protected Mask(Parcel in) {
        ID = in.readInt();
        Age = in.readInt();
        Kurs = in.readInt();
        Surname = in.readString();
        Name =in.readString();
        Image = in.readString();
    }


    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };

    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAge(int age) {
        Age = age;
    }
    public void setKurs(int kurs) {
        Kurs = kurs;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setImage(String image) {
        Image = image;
    }



    public Mask(int ID, int age, int kurs, String name, String surname, String image) {
        this.ID = ID;
        Name = name;
        Surname = surname;
        Age = age;
        Kurs = kurs;
        Image = image;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeInt(Kurs);
        parcel.writeInt(Age);
        parcel.writeString(Name);
        parcel.writeString(Surname);
        parcel.writeString(Image);
    }
    public int getAge() {
        return Age;
    }
    public int getKurs() {
        return Kurs;
    }
    public String getName() {
        return Name;
    }

    public String getSurname() {
        return Surname;
    }
    public String getImage() {
        return Image;
    }

}
