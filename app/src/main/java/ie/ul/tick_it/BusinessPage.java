package ie.ul.tick_it;

import static android.content.ContentValues.TAG;

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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class BusinessPage extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private Button locationbutton;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_page);
        Intent mIntent = getIntent();
        String name = mIntent.getStringExtra("Name");
        location = mIntent.getStringExtra("Location");
        final TextView businessname = (TextView) findViewById(R.id.businessname);
        final TextView businesslocation = (TextView) findViewById(R.id.businesslocation);
        final TextView businessimage = (TextView) findViewById(R.id.businessimage);
        locationbutton = (Button) findViewById(R.id.BPagelocation );
        locationbutton.setOnClickListener(this);
        businessname.setText(name);
        businesslocation.setText(name);
        businessimage.setText(name);
        ArrayList<Ticket> TicketList = new ArrayList<>();

        ListView BusinessListView = findViewById(R.id.listview);
        ArrayAdapter<Ticket> adapter = new ArrayAdapter<Ticket>(
                this,android.R.layout.simple_list_item_1,new ArrayList<Ticket>()
        );
        BusinessListView.setAdapter(adapter);
        DB.collection("Tickets")
                .whereEqualTo("Name",name)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Ticket temp = document.toObject(Ticket.class);
                            TicketList.add(temp);

                        }
                        adapter.clear();
                        adapter.addAll(TicketList);

                    }
                });

        BusinessListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Ticket temp = TicketList.get(i);
                Intent myIntent = new Intent(BusinessPage.this, TicketPage.class);
                myIntent.putExtra("Name", temp.getName());
                myIntent.putExtra("Location", temp.getLocation());
                myIntent.putExtra("EventName", temp.getEventName());
                startActivity(myIntent);
            }
        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BPagelocation:
                Intent myIntent = new Intent(BusinessPage.this, Maps.class);
                myIntent.putExtra("location",location);
                startActivity(myIntent);
                break;
        }
    }
}