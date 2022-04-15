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

public class OwnerHomePage extends AppCompatActivity implements View.OnClickListener{
    private Button Logout;
    private TextView banner, NewBusiness;
    private FirebaseUser user;
    private String UserID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_home_page);
        NewBusiness = (Button) findViewById(R.id.NewBusiness);
        NewBusiness.setOnClickListener(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
        UserID = user.getUid();
        final TextView nameTextView = (TextView) findViewById(R.id.displayownername);
        final TextView EmailTextView = (TextView) findViewById(R.id.displayowneremail);
        final TextView AgeTextView = (TextView) findViewById(R.id.displayownerage);
        ArrayList<Business> BusinessList = new ArrayList<>();
        ListView BusinessListView = findViewById(R.id.ListView);
        ArrayAdapter<Business> adapter = new ArrayAdapter<Business>(
                this,android.R.layout.simple_list_item_1,new ArrayList<Business>()
        );

        BusinessListView.setAdapter(adapter);
        db.collection("Business")
                .whereEqualTo("OwnerID",UserID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Business temp = document.toObject(Business.class);
                            BusinessList.add(temp);
                            Log.d(TAG, temp.getName() + " " + temp.getLocation() + " " + temp.getImage());
                        }
                        adapter.clear();
                        adapter.addAll(BusinessList);

                    }
                });

        BusinessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Business temp = BusinessList.get(i);
                Intent myIntent = new Intent(OwnerHomePage.this, BusinessPage.class);
                myIntent.putExtra("Name", temp.getName());
                myIntent.putExtra("Location", temp.getLocation());
                myIntent.putExtra("", temp.getName());
                startActivity(myIntent);

            }
        });

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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.NewBusiness:
                startActivity(new Intent(this,RegisterBusiness.class));
                break;

        }
    }
}
