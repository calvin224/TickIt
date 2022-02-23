package ie.ul.tick_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner, RegisterUser;
    private EditText Name, Age, EmailAddress, Password;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth = FirebaseAuth.getInstance();
        RegisterUser = (Button) findViewById(R.id.RegisterUser);
        RegisterUser.setOnClickListener(this);

        Name = (EditText) findViewById(R.id.Name);
        Age = (EditText) findViewById(R.id.Age);
        EmailAddress = (EditText) findViewById(R.id.EmailAddress);
        Password = (EditText) findViewById(R.id.Password);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegisterUser:
                RegisterUser();
                System.out.println("it works");
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
                            User user = new User(name, age, Email);

                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this, "User Has been added!", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(RegisterUser.this, "Failed! try again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }

                });

    }
}
