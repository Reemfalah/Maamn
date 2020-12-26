package com.example.rawan.maamn;

import android.os.Parcel;
import android.os.Parcelable;

public class Caregiver implements Parcelable {

    String id, name, username, email, password, phone, patient_anme, patient_age;

    public Caregiver(){

    }

    public Caregiver(String id, String name, String username, String email, String password, String phone, String patient_anme, String patient_age) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.patient_anme = patient_anme;
        this.patient_age = patient_age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPatient_anme() {
        return patient_anme;
    }

    public void setPatient_anme(String patient_anme) {
        this.patient_anme = patient_anme;
    }

    public String getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(String patient_age) {
        this.patient_age = patient_age;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.username);
        dest.writeString(this.email);
        dest.writeString(this.password);
        dest.writeString(this.phone);
        dest.writeString(this.patient_anme);
        dest.writeString(this.patient_age);
    }

    protected Caregiver(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.username = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.phone = in.readString();
        this.patient_anme = in.readString();
        this.patient_age = in.readString();
    }

    public static final Parcelable.Creator<Caregiver> CREATOR = new Parcelable.Creator<Caregiver>() {
        @Override
        public Caregiver createFromParcel(Parcel source) {
            return new Caregiver(source);
        }

        @Override
        public Caregiver[] newArray(int size) {
            return new Caregiver[size];
        }
    };
}
