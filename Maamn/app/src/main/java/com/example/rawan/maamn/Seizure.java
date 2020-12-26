package com.example.rawan.maamn;

public class Seizure {

    String sei_ID, date, time, duration, description, patient_ID , caregiver_ID;

    public String getSei_ID() {
        return sei_ID;
    }

    public void setSei_ID(String sei_ID) {
        this.sei_ID = sei_ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPatient_ID() {
        return patient_ID;
    }

    public void setPatient_ID(String patient_ID) {
        this.patient_ID = patient_ID;
    }

    public String getCaregiver_ID() {
        return caregiver_ID;
    }

    public void setCaregiver_ID (String caregiver_ID) { this.caregiver_ID = caregiver_ID;
    }

    public Seizure(String sei_ID, String date, String time, String duration, String description, String patient_ID , String caregiver_ID) {
        this.sei_ID = sei_ID;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.description = description;
        this.patient_ID = patient_ID;
        this.caregiver_ID = caregiver_ID;
    }

    public Seizure() {
    }
}
