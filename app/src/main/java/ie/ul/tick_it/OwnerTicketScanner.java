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

public class OwnerTicketScanner extends AppCompatActivity implements View.OnClickListener {
    private EditText InputTicketID;
    private Button VerifyTicketButton;
    private boolean confirmed = false;
    private boolean ticketScannedSuccess = false;
    private boolean isScanningTicketScanned;
    private String TestID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_ticket_scanner);
        VerifyTicketButton = (Button) findViewById(R.id.VerifyTicketID);
        VerifyTicketButton.setOnClickListener(this);
        InputTicketID = (EditText) findViewById(R.id.InputTicketID);
    }

    public boolean validateTicket(String TicketID){
       db.collection("UserTickets").get()
        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    if (document.getId().equals(TicketID)) {
                        TestID = document.getId();
                        ticketScannedSuccess = true;
                    }
                }
            }
        });
        return ticketScannedSuccess;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.VerifyTicketID:
                if(confirmed == true) {
                    String TicketID = InputTicketID.getText().toString().trim();
                    boolean scanned = validateTicket(TicketID);
                    db.collection("UserTickets").whereEqualTo("TicketID",TicketID)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        Ticket temp = document.toObject(Ticket.class);
                                        isScanningTicketScanned = temp.getScanned();
                                    }
                                }
                            });
                    if (scanned == true && isScanningTicketScanned == false) {
                        VerifyTicketButton.setText("Ticket " + TicketID + " successfully validated!");
                        DocumentReference query = db.collection("UserTickets").document(TicketID);
                        query
                                .update("Scanned", scanned
                                )
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
                    } else {
                        VerifyTicketButton.setText("Error! Ticket not valid!");
                    }
                } else {
                    String TicketID = InputTicketID.getText().toString().trim();
                    boolean scanned = validateTicket(TicketID);
                    db.collection("UserTickets").whereEqualTo("TicketID",TicketID)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                        Ticket temp = document.toObject(Ticket.class);
                                        isScanningTicketScanned = temp.getScanned();
                                    }
                                }
                            });
                    VerifyTicketButton.setText("Tap to confirm ticket ID");
                    confirmed = true;
                }
                break;
        }
    }
}
