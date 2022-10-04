package com.example.prone;


import android.os.Parcel;
import android.os.Parcelable;

public class Mask  implements Parcelable{
    private int ID;
    private int Age;
    private int Kurs;
    private String Surname;
    private String Name;
    private String ArticleNumber;

    public Mask(int ID, int age,int kurs, String surname, String name, String articleNumber) {
        this.ID = ID;
        Age = age;
        Kurs = kurs;
        Surname = surname;
        Name = name;
        ArticleNumber = articleNumber;
    }

    protected Mask(Parcel in) {
        ID = in.readInt();
        Age = in.readInt();
        Kurs = in.readInt();
        Surname = in.readString();
        Name = in.readString();
        ArticleNumber = in.readString();
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

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAge(int age) {
        Age = age;
    }
    public void setKurs(int age) {
        Kurs = age;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setArticleNumber(String articleNumber) {
        ArticleNumber = articleNumber;
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
        parcel.writeString(ArticleNumber);
    }

    public int getKurs2() {
        return Kurs;
    }

    public int getAge2() {
        return Age;
    }

    public String getName() {

        return Name;
    }
    public String getSurname() {
        return Surname;
    }

    public String getArticleNumber() {
        return ArticleNumber;
    }

    public int getID() {
        return ID;
    }
}
