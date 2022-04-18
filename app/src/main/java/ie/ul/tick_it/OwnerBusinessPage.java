package ie.ul.tick_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OwnerBusinessPage extends AppCompatActivity implements View.OnClickListener {
    private Button addticket;
    String name;
    String Location;
    String BusinessType;
    String BusinessEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_business_page);
        addticket = (Button) findViewById(R.id.addticket);
        addticket.setOnClickListener(this);
        Intent mIntent = getIntent();
        name = mIntent.getStringExtra("Name");
        Location = mIntent.getStringExtra("Location");
        BusinessType = mIntent.getStringExtra("BusinessType");
        BusinessEmail = mIntent.getStringExtra("BusinessEmail");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.addticket:
                goaddticket();
                break;
        }

    }

    private void goaddticket(){
        Intent myIntent = new Intent(OwnerBusinessPage.this, OwnerAddticket.class);
        myIntent.putExtra("Name", name);
        startActivity(myIntent);
    }


}