package com.example.dylanyung.propellerhealth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dylanyung.propellerhealth.objects.Event;
import com.example.dylanyung.propellerhealth.objects.UserDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserEventActivity extends AppCompatActivity implements View.OnClickListener {
    Button backButton;
    Button addEventButton;
    String selectedUserId;
    ArrayList<Event> selectedUserEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_events);

        selectedUserId = UserDatabase.getInstance().getCurrentUser();
        selectedUserEvents = UserDatabase.getInstance().getPerson(selectedUserId).getEvents();
        Collections.sort(selectedUserEvents, new CustomComparator());

        ListView userEventTable = (ListView) findViewById(R.id.user_event_table);
        backButton = (Button) findViewById(R.id.back_button);
        addEventButton = (Button) findViewById(R.id.add_button);

        backButton.setOnClickListener(this);
        addEventButton.setOnClickListener(this);

        CustomAdaptor customAdaptor = new CustomAdaptor();
        userEventTable.setAdapter(customAdaptor);
    }

    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return selectedUserEvents.size();
        }

        @Override
        public Object getItem(int position) {
            return selectedUserEvents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.event_list_view_item, null);

            TextView eventTime = view.findViewById(R.id.date_time);
            TextView eventMedication = view.findViewById(R.id.medication_name);
            TextView eventMedicationType = view.findViewById(R.id.medication_type);
            TextView eventId = view.findViewById(R.id.id);

            Event selectedUserEvent = selectedUserEvents.get(position);
            eventId.setText("Event Id: " + selectedUserEvent.getId());
            eventTime.setText("Date: " + selectedUserEvent.getDateTime().toString());
            eventMedication.setText("Medication: " + selectedUserEvent.getMedication().getName());
            eventMedicationType.setText("Type: " + selectedUserEvent.getMedication().getMedicationType());

            return view;
        }
    }

    @Override
    public void onClick(View v) {
        if (v == backButton) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        } else if (v == addEventButton) {
            Intent intent = new Intent(getBaseContext(), AddNewEventActivity.class);
            startActivity(intent);
        }
    }

    public class CustomComparator implements Comparator<Event> {
        @Override
        public int compare(Event o1, Event o2) {
            return o2.getDateTime().compareTo(o1.getDateTime());
        }
    }
}
