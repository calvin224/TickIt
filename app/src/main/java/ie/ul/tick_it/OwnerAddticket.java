package ie.ul.tick_it;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

import java.util.Calendar;

public class OwnerAddticket extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String Name;
    private EditText EventName;
    private EditText About;
    private EditText Location;
    private EditText Image;
    private EditText Type;
    private TextView banner, RegisterBusiness;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_addticket);
        RegisterBusiness = (Button) findViewById(R.id.RegisterBusiness);
        RegisterBusiness.setOnClickListener(this);
        Intent mIntent = getIntent();
        Name = mIntent.getStringExtra("Name");
        EventName = (EditText) findViewById(R.id.EventName);
        About = (EditText) findViewById(R.id.About);
        Location = (EditText) findViewById(R.id.BusinessAddress);
        Image = (EditText) findViewById(R.id.BusinessImage);
        Type = (EditText) findViewById(R.id.businesscount);
        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegisterBusiness:
                addticket();
                System.out.println("it works");
                startActivity(new Intent(this,OwnerHomePage.class));
                break;

        }
    }


    private void addticket() {
        String Eventname = EventName.getText().toString().trim();
        String about = About.getText().toString().trim();
        String location = Location.getText().toString().trim();
        String image = Image.getText().toString().trim();
        String type = Type.getText().toString().trim();
        String indate = dateButton.getText().toString().trim();
        LocalDate eventdate = LocalDate.parse(indate, DateTimeFormatter.ofPattern("MMM d yyy"));
        ZoneId zoneId = ZoneId.systemDefault();
        long date = eventdate.atStartOfDay(zoneId).toEpochSecond();

        Map<String, Object> businessMap = new HashMap<>();
        businessMap.put("Name", Name);
        businessMap.put("EventName", Eventname);
        businessMap.put("About", about);
        businessMap.put("image", image);
        businessMap.put("Location", location);
        businessMap.put("Count", type);
        businessMap.put("Date", date);
        db.collection("Tickets").document(Eventname)
                .set(businessMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                    }
                });
        Ticket ticket = new Ticket(Name,Eventname,about,location,image,type,date);

    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "Jan";
        if(month == 2)
            return "Feb";
        if(month == 3)
            return "Mar";
        if(month == 4)
            return "Apr";
        if(month == 5)
            return "May";
        if(month == 6)
            return "Jun";
        if(month == 7)
            return "Jul";
        if(month == 8)
            return "Aug";
        if(month == 9)
            return "Sep";
        if(month == 10)
            return "Oct";
        if(month == 11)
            return "Nov";
        if(month == 12)
            return "Dec";

        //default should never happen
        return "Jan";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}