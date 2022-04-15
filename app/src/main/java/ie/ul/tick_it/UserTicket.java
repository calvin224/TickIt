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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserTicket extends AppCompatActivity {
    private TextView Businessname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_ticket);
        Intent mIntent = getIntent();
        String Businessname = mIntent.getStringExtra("BusinessName");
        final TextView businessname = (TextView) findViewById(R.id.Businessname);
        businessname.setText(Businessname);



    }
}