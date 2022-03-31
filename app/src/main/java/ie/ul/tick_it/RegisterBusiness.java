package ie.ul.tick_it;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterBusiness extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private TextView banner, RegisterBusiness;
    private EditText Name, Type, EmailAddress, Location, Image;
    private FirebaseUser user;
    private String UserID;
    private ProgressBar progressBar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_business);
        mAuth = FirebaseAuth.getInstance();
        RegisterBusiness = (Button) findViewById(R.id.RegisterBusiness);
        RegisterBusiness.setOnClickListener(this);
        Name = (EditText) findViewById(R.id.Name);
        Type = (EditText) findViewById(R.id.BusinessType);
        EmailAddress = (EditText) findViewById(R.id.EmailAddress);
        Location = (EditText) findViewById(R.id.BusinessAddress);
        Image = (EditText) findViewById(R.id.BusinessImage);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.RegisterBusiness:
                RegisterBusiness();
                System.out.println("it works");
                startActivity(new Intent(this,OwnerHomePage.class));
                break;

        }
    }

    private void RegisterBusiness() {
        String name = Name.getText().toString().trim();
        String type = Type.getText().toString().trim();
        String Email = EmailAddress.getText().toString().trim();
        String location = Location.getText().toString().trim();
        String image = Image.getText().toString().trim();
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
            Map<String, Object> businessMap = new HashMap<>();
            businessMap.put("Name", name);
            businessMap.put("Type", type);
            businessMap.put("Email", Email);
            businessMap.put("Location", location);
            businessMap.put("Image", image);
            businessMap.put("OwnerID", UserID);
            db.collection("Business").document()
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
            Business business = new Business(name, location, image, type, UserID);
    }
}
