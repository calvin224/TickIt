package ie.ul.tick_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OwnerBusinessPage extends AppCompatActivity implements View.OnClickListener {
    private Button addticket;
    private FirebaseUser user;
    private String UserID;
    String name;
    String Location;
    String BusinessType;
    String BusinessEmail;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_business_page);
        addticket = (Button) findViewById(R.id.addticket);
        addticket.setOnClickListener(this);
        Intent mIntent = getIntent();
        name = mIntent.getStringExtra("Name");
        Location = mIntent.getStringExtra("Location");
        BusinessType = mIntent.getStringExtra("BusinessType");
        BusinessEmail = mIntent.getStringExtra("BusinessEmail");
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        final TextView nameTextView = (TextView) findViewById(R.id.displayownername);
        final TextView EmailTextView = (TextView) findViewById(R.id.displayowneremail);
        final TextView AgeTextView = (TextView) findViewById(R.id.displayownerage);
        DocumentReference docRef = db.collection("Owners").document(UserID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nameTextView.setText(document.getString("Name"));
                        EmailTextView.setText(document.getString("Email"));
                        AgeTextView.setText(document.getString("Age"));
                    }
                } else {

                }
            }
        });
        final TextView BusinessNameTextView = (TextView) findViewById(R.id.name);
        final TextView TypeTextView = (TextView) findViewById(R.id.BusinessType);
        final TextView LocationTextView = (TextView) findViewById(R.id.Location);
        final TextView BusinessEmailTextView = (TextView) findViewById(R.id.BusinessEmail);
        BusinessNameTextView.setText(name);
        TypeTextView.setText(BusinessType);
        LocationTextView.setText(Location);
        BusinessEmailTextView.setText(BusinessEmail);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addticket:
                goaddticket();
                break;
        }

    }

    private void goaddticket(){
        Intent myIntent = new Intent(OwnerBusinessPage.this, OwnerAddticket.class);
        myIntent.putExtra("Name", name);
        startActivity(myIntent);
    }


}