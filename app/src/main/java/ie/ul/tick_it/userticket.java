package ie.ul.tick_it;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class userticket extends AppCompatActivity implements View.OnClickListener {
    private FirebaseFirestore DB = FirebaseFirestore.getInstance();
    private Button locationbutton;
    private Button deletebutton;
    private String location;
    private String TicketID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userticket);
        locationbutton = (Button) findViewById(R.id.UTPageLocation );
        locationbutton.setOnClickListener(this);
        deletebutton = (Button) findViewById(R.id.DeleteButton );
        deletebutton.setOnClickListener(this);
        Intent mIntent = getIntent();
        location = mIntent.getStringExtra("Location");
        String Businessname = mIntent.getStringExtra("BusinessName");
        TicketID = mIntent.getStringExtra("TicketID");
        final TextView businessname = (TextView) findViewById(R.id.Businessname);
        businessname.setText(Businessname);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.UTPageLocation:
                Intent myIntent = new Intent(userticket.this, Maps.class);
                myIntent.putExtra("location",location);
                startActivity(myIntent);
                break;
            case R.id.DeleteButton:
                DB.collection("UserTickets").document(TicketID).delete();
                startActivity(new Intent(this,ProfilePage.class));
                break;
        }
    }
}