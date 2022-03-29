package ie.ul.tick_it;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomePage extends AppCompatActivity  implements View.OnClickListener{
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private Button Profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ArrayList<Business> BusinessList = new ArrayList<>();
        Profile = (Button) findViewById(R.id.profile);
        Profile.setOnClickListener((View.OnClickListener) this);
        ListView BusinessListView = findViewById(R.id.ListView);
        ArrayAdapter<Business> adapter = new ArrayAdapter<Business>(
                this,android.R.layout.simple_list_item_1,new ArrayList<Business>()
        );
        BusinessListView.setAdapter(adapter);
        DB.collection("Restaurants")
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
        DB.collection("NightClubs")
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.profile:
                startActivity(new Intent(this,ProfilePage.class));
                break;


        }
    }



}
