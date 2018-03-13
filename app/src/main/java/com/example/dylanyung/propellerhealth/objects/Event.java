package com.example.dylanyung.propellerhealth.objects;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Dylan Yung on 3/6/2018.
 */

public class Event implements Comparable<Event> {
    int id;
    Date dateTime;
    Medication medication;

    public Event(int id, Date d, Medication m) {
        this.id = id;
        this.dateTime = d;
        this.medication = m;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    @Override
    public int compareTo(@NonNull Event o) {
        return this.dateTime.compareTo(o.getDateTime());
    }
}
