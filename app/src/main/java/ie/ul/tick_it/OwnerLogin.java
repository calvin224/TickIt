package ie.ul.tick_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class OwnerLogin extends AppCompatActivity implements View.OnClickListener  {
    private TextView UserLogin,OwnerSignup;
    private EditText Email, Password;
    private Button signIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);

        UserLogin = (TextView) findViewById(R.id.UserLogin);
        UserLogin.setOnClickListener(this);

        OwnerSignup = (TextView) findViewById(R.id.OwnerSignup);
        OwnerSignup.setOnClickListener(this);

        signIn = (Button) findViewById(R.id.signIn);
        signIn.setOnClickListener(this);
        Email = (EditText) findViewById(R.id.editTextEmail);
        Password = (EditText) findViewById(R.id.editTextPassword);
        mAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Register:
                startActivity(new Intent(this,RegisterUser.class));
                break;
            case R.id.signIn:
                userLogin();
                break;
            case R.id.UserLogin:
                startActivity(new Intent(this,LogInPage.class));
                break;
            case R.id.OwnerSignup:
                startActivity(new Intent(this,OwnerSignup.class));
                break;
        }
    }
    private void userLogin() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(OwnerLogin.this, OwnerHomePage.class));
                }
            }
        });
    }
}