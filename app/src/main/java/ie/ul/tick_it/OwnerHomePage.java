package ie.ul.tick_it;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class OwnerHomePage extends AppCompatActivity {
    private Button Logout;
    private FirebaseUser user;
    private String UserID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home_page);

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
                    } else {
                        startActivity(new Intent(OwnerHomePage.this,
                                HomePage.class));
                    }
                } else {

                }
            }
        });
    }
    }
