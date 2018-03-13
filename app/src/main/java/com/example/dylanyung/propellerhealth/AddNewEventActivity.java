package com.example.dylanyung.propellerhealth;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dylanyung.propellerhealth.objects.Event;
import com.example.dylanyung.propellerhealth.objects.Medication;
import com.example.dylanyung.propellerhealth.objects.MedicationType;
import com.example.dylanyung.propellerhealth.objects.Person;
import com.example.dylanyung.propellerhealth.objects.UserDatabase;

import java.util.Calendar;
import java.util.Date;

public class AddNewEventActivity extends AppCompatActivity implements View.OnClickListener {
    Button timePicker;
    Button datePicker;
    Button saveButton;
    Button cancelButton;
    TextInputEditText medicationInput;
    TextView setTime;
    TextView setDate;

    String selectedUserId;

    Calendar myCalendar = Calendar.getInstance();
    Date newDate = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_event);
        selectedUserId = UserDatabase.getInstance().getCurrentUser();

        this.medicationInput = (TextInputEditText) findViewById(R.id.medication);
        this.datePicker = (Button) findViewById(R.id.date_picker);
        this.timePicker = (Button) findViewById(R.id.time_picker);
        this.saveButton = (Button) findViewById(R.id.save_button);
        this.cancelButton = (Button) findViewById(R.id.cancel_button);
        this.setTime = (TextView) findViewById(R.id.time_set);
        this.setDate = (TextView) findViewById(R.id.date_set);

        this.datePicker.setOnClickListener(this);
        this.timePicker.setOnClickListener(this);
        this.saveButton.setOnClickListener(this);
        this.cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == datePicker) {
            final DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, month);
                    newDate = myCalendar.getTime();
                    setDate.setText("Date: " + year + "-" + month + "-" + dayOfMonth);
                }
            };
            new DatePickerDialog(AddNewEventActivity.this,
                    dateListener,
                    this.myCalendar.get(Calendar.YEAR),
                    this.myCalendar.get(Calendar.MONTH),
                    this.myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        } else if (v == timePicker) {
            final TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalendar.set(Calendar.MINUTE, minute);
                    myCalendar.set(Calendar.SECOND, 0);

                    newDate = myCalendar.getTime();
                    String minute_string = minute + "";

                    if (minute < 10) {
                        minute_string = "0" + minute_string;
                    }

                    setTime.setText("Time: " + hourOfDay + ":" + minute_string);
                }
            };
            new TimePickerDialog(AddNewEventActivity.this,
                    timeListener,
                    myCalendar.getTime().getHours(),
                    myCalendar.getTime().getMinutes(),
                    false).show();
        } else if (v == saveButton) {
            Person currentPerson = UserDatabase.getInstance().getPerson(selectedUserId);
            int id = currentPerson.getEvents().size() + 1;
            String medicationString = medicationInput.getText().toString();
            Medication medication = new Medication(medicationInput.getText().toString(), MedicationType.getType(medicationString));
            Event newEvent = new Event(id, newDate, medication);
            currentPerson.addEvent(newEvent);

            Intent intent = new Intent(getBaseContext(), UserEventActivity.class);
            startActivity(intent);
        } else if (v == cancelButton) {
            Intent intent = new Intent(getBaseContext(), UserEventActivity.class);
            startActivity(intent);
        }
    }
}
