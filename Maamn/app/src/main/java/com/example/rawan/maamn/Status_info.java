package com.example.rawan.maamn;

public class Status_info {

    String sei_id, patient_id, date, time, duration;

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
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

    public String getSei_id() {
        return sei_id;
    }

    public void setSei_id(String sei_id) {
        this.sei_id = sei_id;
    }

    public Status_info(String patient_id, String date, String time, String duration, String sei_id) {
        this.patient_id = patient_id;
        this.date = date;
        this.time = time;
        this.duration = duration;
        this.sei_id = sei_id;
    }

    public Status_info() {
    }
}
