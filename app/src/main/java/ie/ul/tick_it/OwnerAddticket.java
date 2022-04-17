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

import java.util.HashMap;
import java.util.Map;

public class OwnerAddticket extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String Name;
    private EditText EventName;
    private EditText About;
    private EditText Location;
    private EditText Image;
    private EditText Type;
    private TextView banner, RegisterBusiness;

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
        Map<String, Object> businessMap = new HashMap<>();
        businessMap.put("Name", Name);
        businessMap.put("EventName", Eventname);
        businessMap.put("About", about);
        businessMap.put("image", image);
        businessMap.put("Location", location);
        businessMap.put("Count", type);
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
        Ticket ticket = new Ticket(Name,Eventname,about,location,image,type);

    }
}