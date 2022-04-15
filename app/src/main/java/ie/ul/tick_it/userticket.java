package ie.ul.tick_it;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class userticket extends AppCompatActivity implements View.OnClickListener {
    private Button locationbutton;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userticket);
        locationbutton = (Button) findViewById(R.id.UTPageLocation );
        locationbutton.setOnClickListener(this);
        Intent mIntent = getIntent();
        location = mIntent.getStringExtra("Location");
        String Businessname = mIntent.getStringExtra("BusinessName");
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
        }
    }
}