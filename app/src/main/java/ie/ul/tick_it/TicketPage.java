package ie.ul.tick_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TicketPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);
        Intent mIntent = getIntent();;
        String name = mIntent.getStringExtra("Name");
        final TextView Ticketname = (TextView) findViewById(R.id.ticketname);
        Ticketname.setText(name);

    }
}