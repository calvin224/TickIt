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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditUserProfile extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private EditText Name, Age, EmailAddress;
    private TextView UpdateProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);
        mAuth = FirebaseAuth.getInstance();
        UpdateProfile = (Button) findViewById(R.id.RegisterOwner);
        UpdateProfile.setOnClickListener(this);
        Name = (EditText) findViewById(R.id.Name);
        Age = (EditText) findViewById(R.id.Age);
        EmailAddress = (EditText) findViewById(R.id.EmailAddress);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegisterOwner:
                updateProfile();
                startActivity(new Intent(this, ProfilePage.class));
                break;

        } }

        private void updateProfile() {
            String name = Name.getText().toString().trim();
            String age = Age.getText().toString().trim();
            DocumentReference query = db.collection("Users").document(FirebaseAuth.getInstance().getUid());
            query
                    .update("Name", name,
                            "Age",age
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
        }


    }
