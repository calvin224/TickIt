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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TicketPage extends AppCompatActivity implements View.OnClickListener {
    private FirebaseUser user;
    private String BusinessName, BusinessLocation, UserID, UserName, UserEmail, Eventname, About, Count;
    private int countint;
    private Button addbutton,location;
    private int hastaken = 0;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);
        Intent mIntent = getIntent();
        addbutton = (Button) findViewById(R.id.button);
        addbutton.setOnClickListener(this);
        location = (Button) findViewById(R.id.location);
        location.setOnClickListener(this);
        BusinessName = mIntent.getStringExtra("Name");
        BusinessLocation = mIntent.getStringExtra("Location");
        Eventname = mIntent.getStringExtra("EventName");
        final TextView Ticketname = (TextView) findViewById(R.id.ticketname);
        Ticketname.setText(BusinessName);
        getcount();
        getuserinfo();
    }

    public void getuserinfo() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        DocumentReference docRef = db.collection("Users").document(UserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserName = document.getString("Name");
                        UserEmail = document.getString("Email");
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
                taketicket();
                break;
            case R.id.location:
                Intent myIntent = new Intent(TicketPage.this, Maps.class);
                myIntent.putExtra("location",BusinessLocation);
                startActivity(myIntent);
                break;
        }

    }

    private void taketicket() {
        getcount();
        if(hastaken == 1) {
            addbutton.setText("You have a ticket");
        } else
        if (Count.equals("-1")) {
            addbutton.setText("full");
        } else {
            user = FirebaseAuth.getInstance().getCurrentUser();
            UserID = user.getUid();
            Map<String, Object> businessMap = new HashMap<>();
            businessMap.put("UserID", UserID);
            businessMap.put("Name", UserName);
            businessMap.put("EventName", Eventname);
            businessMap.put("BusinessName", BusinessName);
            businessMap.put("Location", BusinessLocation);
            businessMap.put("Scanned", false);
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
            Ticket ticket = new Ticket(UserID, BusinessName, UserEmail, UserName, BusinessLocation, false);
            db.collection("UserTickets")
                    .whereEqualTo("UserID", UserID)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                Ticket temp = document.toObject(Ticket.class);
                                if (temp.getEventName().equals(Eventname)) {
                                    String ticketID = document.getId();
                                    DocumentReference query = db.collection("UserTickets").document(ticketID);
                                    query.update("TicketID", ticketID);
                                } else{
                                    continue;
                                }

                            }

                        }
                    });
            minusticket();
            addbutton.setText("You have a ticket");
            hastaken = 1;
        }
    }


    private void minusticket() {
        {
            DocumentReference washingtonRef = db.collection("Tickets").document(Eventname);
            washingtonRef
                    .update("Count", Count)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "DocumentSnapshot successfully updated!");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error updating document", e);
                        }
                    });
        }
    }

    private void getcount() {
        DocumentReference docRef = db.collection("Tickets").document(Eventname);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Count = document.getString("Count");
                        countint = Integer.parseInt(Count);
                        countint = countint - 1;
                        Count = String.valueOf(countint);
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

}
