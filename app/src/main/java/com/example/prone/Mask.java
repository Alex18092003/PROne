package com.example.prone;


import android.os.Parcel;
import android.os.Parcelable;

public class Mask  implements Parcelable{
    private int Kod_student;
    private int Age;
    private int Kurs;
    private String Surname;
    private String Name;
    private String Images;

    protected Mask(Parcel in) {
        Kod_student = in.readInt();
        Age = in.readInt();
        Kurs =in.readInt();
        Surname = in.readString();
        Name =in.readString();
        Images = in.readString();
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
        this.Kod_student = Kod_student;
    }
    public void setAge(int age) {
        Age = age;
    }
    public void setKurs(int kurs) {
        Kurs = kurs;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setSurname(String surname) {
        Surname = surname;
    }
    public void setImages(String images) {
        Images = images;
    }



    public Mask(int Kod_student, int age, int kurs, String name, String surname, String images) {
        this.Kod_student = Kod_student;
        Age = age;
        Kurs = kurs;
        Name = name;
        Surname = surname;
        Images = images;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(Kod_student);
        parcel.writeInt(Age);
        parcel.writeInt(Kurs);
        parcel.writeString(Name);
        parcel.writeString(Surname);
        parcel.writeString(Images);
    }

    public int getID() {
        return Kod_student;
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
    public String getImages() {
        return Images;
    }

}
