package com.example.rawan.maamn;

import android.os.Parcel;
import android.os.Parcelable;

public class Medication  {

    String rem_ID, med_name, dose, medtime, day, patient_ID, caregiver_ID;

    public String getRem_ID() {
        return rem_ID;
    }

    public void setRem_ID(String rem_ID) {
        this.rem_ID = rem_ID;
    }

    public String getMed_name() {
        return med_name;
    }

    public void setMed_name(String med_name) {
        this.med_name = med_name;
    }

    public String getDose() {
        return dose;
    }

    public void setDose(String dose) {
        this.dose = dose;
    }

    public String getMedtime() {
        return medtime;
    }

    public void setMedtime(String medtime) {
        this.medtime = medtime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
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

    public void setCaregiver_ID(String caregiver_ID) {
        this.caregiver_ID = caregiver_ID;
    }

    public Medication(String rem_ID, String med_name, String dose, String medtime, String day, String patient_ID, String caregiver_ID) {
        this.rem_ID = rem_ID;
        this.med_name = med_name;
        this.dose = dose;
        this.medtime = medtime;
        this.day = day;
        this.patient_ID = patient_ID;
        this.caregiver_ID = caregiver_ID;
    }

    public Medication() {
    }
}
