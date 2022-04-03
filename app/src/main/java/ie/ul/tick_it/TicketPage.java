package ie.ul.tick_it;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TicketPage extends AppCompatActivity implements View.OnClickListener {
    private FirebaseUser user;
    private String BusinessName,BusinessLocation,UserID,UserName,Userid,UserEmail,Eventname,About;
    private Button addbutton;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    User userinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);
        Intent mIntent = getIntent();
        addbutton = (Button) findViewById(R.id.button);
        addbutton.setOnClickListener(this);
        BusinessName = mIntent.getStringExtra("Name");
        BusinessLocation = mIntent.getStringExtra("Location");

        final TextView Ticketname = (TextView) findViewById(R.id.ticketname);
        Ticketname.setText(BusinessName);



        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        DocumentReference docRef = db.collection("Users").document(UserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserName= document.getString("Name");
                        UserEmail=  document.getString("Email");
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                addticket();
                break;
        }

    }

    private void addticket() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        Map<String, Object> businessMap = new HashMap<>();
        businessMap.put("UserID", UserID);
        businessMap.put("Name",UserName );
        businessMap.put("Email", UserEmail);
        businessMap.put("BusinessName", BusinessName);
        businessMap.put("Location", BusinessLocation);
        db.collection("UserTickets").document()
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
        Ticket ticket = new Ticket(UserID,BusinessName,UserEmail,UserName,BusinessLocation);
    }


}
