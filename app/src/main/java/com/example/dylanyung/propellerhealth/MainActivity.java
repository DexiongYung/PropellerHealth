package com.example.dylanyung.propellerhealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dylanyung.propellerhealth.objects.Event;
import com.example.dylanyung.propellerhealth.objects.UserDatabase;
import com.example.dylanyung.propellerhealth.objects.Medication;
import com.example.dylanyung.propellerhealth.objects.MedicationType;
import com.example.dylanyung.propellerhealth.objects.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private ListView userListView;
    private ArrayList<Person> peopleArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Generate initial data
        if (UserDatabase.getInstance().getPeople().isEmpty()) {
            parseJson();
        }

        //Get User information
        if (peopleArrayList == null) {
            HashMap people = UserDatabase.getInstance().getPeople();
            this.peopleArrayList = new ArrayList<>(people.values());
        }

        //View object bindings
        this.userListView = (ListView) findViewById(R.id.user_list_view);

        CustomAdaptor customAdaptor = new CustomAdaptor();
        this.userListView.setAdapter(customAdaptor);

        userListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Person selectedPerson = peopleArrayList.get(position);
        Intent intent = new Intent(getBaseContext(), UserEventActivity.class);
        UserDatabase.getInstance().setCurrentUser(selectedPerson.getUid());
        startActivity(intent);
    }

    //JSON is separated into user in events, parse separately
    private void parseJson() {
        JSONObject jsonObject = get_json();
        parseUser(jsonObject);
        parseEvents(jsonObject);
    }

    private void parseEvents(JSONObject json) {
        try {
            JSONArray events = json.getJSONArray("events");
            for (int i = 0; i < events.length(); i++) {
                JSONObject objectI = (JSONObject) events.get(i);

                //Get parameters
                String uid = objectI.getString("uid");
                String dateTime = objectI.getString("datetime");
                String medicationString = objectI.getString("medication");
                MedicationType medicationType = MedicationType.getType(medicationString);
                int id = objectI.getInt("id");

                //Convert date and medication to object format
                Date date = parseDate(dateTime);
                Medication medication = new Medication(medicationString, medicationType);

                //Generate event object and add the person's event
                Event event = new Event(id, date, medication);
                Person user = null;
                user = UserDatabase.getInstance().getPerson(uid);

                if(user != null){
                    user.addEvent(event);
                }
            }
        } catch (JSONException e) {
        }
    }

    //Required to properly parse "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" format
    private Date parseDate(String date_string) {
        //Z implies UTC time zone so much parse in UTC format
        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(utc);
        GregorianCalendar cal = new GregorianCalendar(utc);
        Date date = null;

        try {
            cal.setTime(format.parse(date_string));
            //Gets time and date in current time zone
            date = cal.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private void parseUser(JSONObject json) {
        try {
            JSONObject user = json.getJSONObject("user");

            //Get required parameters
            String name = user.getString("name");
            String addressOne = user.getString("address1");
            String addressTwo = user.getString("address2");
            String uid = user.getString("uid");
            String sex = user.getString("sex");
            Date dob = new Date(user.getString("dob"));
            String disease = user.getString("disease");

            //Generate User, Stephen
            Person stephen = new Person(name, addressOne, addressTwo, uid, sex, dob);
            stephen.addDisease(disease);

            //Get all medications
            JSONArray medications = user.getJSONArray("medications");
            for (int i = 0; i < medications.length(); i++) {
                JSONObject objectI = (JSONObject) medications.get(i);
                String medicationName = objectI.getString("name");
                MedicationType medicationType = MedicationType.getType(medicationName);
                Medication medication = new Medication(medicationName, medicationType);
                stephen.addMedications(medication);
            }

            //Add stephen to User DB
            UserDatabase.getInstance().addPerson(stephen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public JSONObject get_json() {
        JSONObject jsonObject = null;
        try {
            InputStream is = getAssets().open("mobile_assessment_data.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            String json = new String(buffer, "UTF-8");

            jsonObject = new JSONObject(json);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return peopleArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return peopleArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.user_list_view_item, null);

            TextView userAddress = view.findViewById(R.id.user_address);
            TextView userAddress2 = view.findViewById(R.id.user_address_2);
            TextView userName = view.findViewById(R.id.user_name);
            TextView userSex = view.findViewById(R.id.user_sex);
            TextView userDisease = view.findViewById(R.id.user_disease);

            Person currentPerson = peopleArrayList.get(position);
            userName.setText("Name: " + currentPerson.getName());
            userAddress.setText("Address: " + currentPerson.getAddressOne());
            userAddress2.setText("Address2: " + currentPerson.getAddressTwo());
            userSex.setText("Sex: " + currentPerson.getSex());
            userDisease.setText("Disease: " + currentPerson.getDisease());

            return view;
        }
    }
}
