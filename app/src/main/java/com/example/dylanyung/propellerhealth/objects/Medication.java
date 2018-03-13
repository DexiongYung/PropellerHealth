package com.example.dylanyung.propellerhealth.objects;

/**
 * Created by Dylan Yung on 3/6/2018.
 */

public class Medication {
    String name;
    MedicationType medicationType;


    public Medication(String n, MedicationType mT) {
        this.name = n;
        this.medicationType = mT;
    }

    public MedicationType getMedicationType() {
        return medicationType;
    }

    public void setMedicationType(MedicationType medicationType) {
        this.medicationType = medicationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
