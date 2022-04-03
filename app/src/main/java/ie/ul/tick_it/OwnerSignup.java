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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class OwnerSignup extends AppCompatActivity implements View.OnClickListener  {
    private FirebaseAuth mAuth;
    private TextView banner, RegisterUser,ownerLogin;
    private EditText Name, Age, EmailAddress, Password;
    private ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_owner_signup);
            mAuth = FirebaseAuth.getInstance();
            RegisterUser = (Button) findViewById(R.id.RegisterOwner);
            RegisterUser.setOnClickListener(this);
            Name = (EditText) findViewById(R.id.Name);
            Age = (EditText) findViewById(R.id.Age);
            EmailAddress = (EditText) findViewById(R.id.About);
            Password = (EditText) findViewById(R.id.Password);
            ownerLogin = (TextView) findViewById(R.id.ownerLogin);
            ownerLogin.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.RegisterOwner:
                    RegisterUser();
                    System.out.println("it works");
                    startActivity(new Intent(this,OwnerLogin.class));
                    break;

                case R.id.ownerLogin:
                    startActivity(new Intent(this,OwnerLogin.class));
                    break;
            }
        }
        private void RegisterUser() {
            String name = Name.getText().toString().trim();
            String age = Age.getText().toString().trim();
            String Email = EmailAddress.getText().toString().trim();
            String password = Password.getText().toString().trim();
            mAuth.createUserWithEmailAndPassword(Email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Map<String, Object> userMap = new HashMap<>();
                                userMap.put("Name", name);
                                userMap.put("Email", Email);
                                userMap.put("Age", age);
                                db.collection("Owners").document(FirebaseAuth.getInstance().getUid())
                                        .set(userMap)
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
                                User user = new User(name, age, Email);
                                FirebaseDatabase.getInstance().getReference("Owners")
                                        .child(FirebaseAuth.getInstance().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(OwnerSignup.this, "User Has been added!", Toast.LENGTH_LONG).show();

                                        } else {
                                            Toast.makeText(OwnerSignup.this, "Failed! try again.", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
        }
    }
