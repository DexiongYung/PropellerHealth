package com.example.dylanyung.propellerhealth.objects;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dylan Yung on 3/6/2018.
 */

public class Person {
    String name;
    String addressOne;
    String addressTwo;
    String uid;
    String sex;
    Date dob;
    String disease;
    ArrayList<Medication> medications;
    ArrayList<Event> events;

    public Person(String n, String a1, String a2, String id, String sex, Date dob) {
        this.name = n;
        this.addressOne = a1;
        this.addressTwo = a2;
        this.uid = id;
        this.sex = sex;
        this.dob = dob;
        this.medications = new ArrayList<Medication>();
        this.events = new ArrayList<Event>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDisease() {
        return disease;
    }

    public void addDisease(String disease) {
        this.disease = disease;
    }

    public ArrayList<Medication> getMedications() {
        return medications;
    }

    public void addMedications(Medication medication) {
        this.medications.add(medication);
    }

    public void addEvent(Event e) {
        this.events.add(e);
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }
}


