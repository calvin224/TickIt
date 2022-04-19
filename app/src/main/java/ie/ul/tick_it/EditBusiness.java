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

public class EditBusiness extends AppCompatActivity implements View.OnClickListener {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private EditText NewBusinessName, NewBusinessEmail, NewBusinessAddress;
    private TextView EditBusiness;
    private String BusinessID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business);
        mAuth = FirebaseAuth.getInstance();
        EditBusiness = (Button) findViewById(R.id.EditBusiness);
        EditBusiness.setOnClickListener(this);
        Intent mIntent = getIntent();
        BusinessID = mIntent.getStringExtra("BusinessID");
        NewBusinessName = (EditText) findViewById(R.id.EditBusinessName);
        NewBusinessEmail = (EditText) findViewById(R.id.EditBusinessEmailAddress);
        NewBusinessAddress = (EditText) findViewById(R.id.EditBusinessAddress);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.EditBusiness:
                updateBusiness();
                startActivity(new Intent(this, OwnerHomePage.class));
                break;

        } }

    private void updateBusiness() {
        String newBusinessName = NewBusinessName.getText().toString().trim();
        String newBusinessEmail = NewBusinessEmail.getText().toString().trim();
        String newBusinessAddress = NewBusinessAddress.getText().toString().trim();
        if (!newBusinessName.equals("")) {
            DocumentReference query = db.collection("Business").document(BusinessID);
            query
                    .update("Name", newBusinessName
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
        if (!newBusinessEmail.equals("")) {
            DocumentReference query = db.collection("Business").document(BusinessID);
            query
                    .update("Email", newBusinessEmail
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
        if (!newBusinessAddress.equals("")) {
            DocumentReference query = db.collection("Business").document(BusinessID);
            query
                    .update("Location", newBusinessAddress
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


}

