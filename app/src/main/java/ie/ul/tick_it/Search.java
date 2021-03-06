package ie.ul.tick_it;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class Search extends AppCompatActivity  implements View.OnClickListener{
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private Button Profile,restaurants,nightclubs,search,reset;
    private EditText SearchText;
    private Business temp;
    private String filter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ArrayList<Business> BusinessList = new ArrayList<>();
        Profile = (Button) findViewById(R.id.profile);
        Profile.setOnClickListener(this);
        restaurants = (Button) findViewById(R.id.restaurants);
        restaurants.setOnClickListener( this);
        nightclubs = (Button) findViewById(R.id.nightclubs);
        nightclubs.setOnClickListener(this);
        SearchText = (EditText) findViewById(R.id.SearchText);
        search = (Button) findViewById(R.id.search);
        search.setOnClickListener(this);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(this);
        Intent mIntent = getIntent();
        filter = mIntent.getStringExtra("Filter");
        ListView BusinessListView = findViewById(R.id.ListView);
        ArrayAdapter<Business> adapter = new ArrayAdapter<Business>(
                this,R.layout.row,new ArrayList<Business>()
        );
        BusinessListView.setAdapter(adapter);
        DB.collection("Business")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Business temp = document.toObject(Business.class);
                            if (temp.getName().toLowerCase().contains(filter.toLowerCase())) {
                                BusinessList.add(temp);
                            } else{
                                continue;
                            }

                        }
                        adapter.clear();
                        adapter.addAll(BusinessList);

                    }
                });
        BusinessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Business temp = BusinessList.get(i);
                Intent myIntent = new Intent(Search.this, BusinessPage.class);
                myIntent.putExtra("Name", temp.getName());
                myIntent.putExtra("Location", temp.getLocation());
                startActivity(myIntent);

            }
        });


    }

    @Override
    public void onClick(View v) {
        Intent FilteredHomePage = new Intent(Search.this, FilteredHomePage.class);
        Intent Search = new Intent(Search.this, Search.class);
        switch (v.getId()){
            case R.id.profile:
                startActivity(new Intent(this,ProfilePage.class));
                break;
            case R.id.restaurants:
                FilteredHomePage.putExtra("Filter", "Restaurant");
                startActivity(FilteredHomePage);
                break;
            case R.id.nightclubs:
                FilteredHomePage.putExtra("Filter", "NightClub");
                startActivity(FilteredHomePage);
                break;
            case R.id.search:
                String searchtext = SearchText.getText().toString().trim();
                Search.putExtra("Filter", searchtext);
                startActivity(Search);
                break;
            case R.id.reset:
                startActivity(new Intent(this,HomePage.class));
                break;
        }
    }



}