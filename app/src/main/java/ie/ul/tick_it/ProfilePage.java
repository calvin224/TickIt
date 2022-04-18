package ie.ul.tick_it;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProfilePage extends AppCompatActivity implements View.OnClickListener {


    private Button Logout;
    private FirebaseUser user;
    private String UserID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button editprofile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        ArrayList<Ticket> TicketList = new ArrayList<>();
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        final TextView nameTextView = (TextView) findViewById(R.id.DisplayName);
        final TextView EmailTextView = (TextView) findViewById(R.id.DisplayEmail);
        final TextView AgeTextView = (TextView) findViewById(R.id.DisplayAge);
        editprofile = (Button) findViewById(R.id.editprofile);
        editprofile.setOnClickListener(this);

        DocumentReference docRef = db.collection("Users").document(UserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nameTextView.setText(document.getString("Name"));
                        EmailTextView.setText(document.getString("Email"));
                        AgeTextView.setText(document.getString("Age"));
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        ListView ListView = findViewById(R.id.listview);
        ArrayAdapter<Ticket> adapter = new ArrayAdapter<Ticket>(
                this,android.R.layout.simple_list_item_1,new ArrayList<Ticket>()
        );
        ListView.setAdapter(adapter);
        db.collection("UserTickets")
                .whereEqualTo("UserID",UserID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Ticket temp = document.toObject(Ticket.class);
                            temp.setTicketID(document.getId());
                            TicketList.add(temp);

                        }
                        adapter.clear();
                        adapter.addAll(TicketList);

                    }
                });
       ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ticket temp = TicketList.get(i);
                Intent myIntent = new Intent(ProfilePage.this, userticket.class);
                myIntent.putExtra("BusinessName", temp.getBusinessName());
                myIntent.putExtra("Location", temp.getLocation());
                myIntent.putExtra("TicketID", temp.getTicketID());
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editprofile:
                startActivity(new Intent(this, EditUserProfile.class));
                break; }
    }
}