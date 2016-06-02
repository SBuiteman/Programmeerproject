package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nl.mprog.project.stijn.R;

/**
 *  TODO
 */
public class MyScheduleActivity extends AppCompatActivity implements View.OnClickListener{

    // fields
    public Button homeButton;
    public TextView myScheduleTitle;

    /**
     * TODO
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_schedule);

        // Initialize views
        init();
    }

    /**
     * TODO
     */
    public void init(){

        myScheduleTitle = (TextView) findViewById(R.id.myScheduleTitle);

        homeButton = (Button) findViewById(R.id.homeButton);
    }

    /**
     * TODO
     */
    @Override
    public void onClick(View v) {
        backToHome();
    }

    /**
     * TODO
     */
    public void backToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
