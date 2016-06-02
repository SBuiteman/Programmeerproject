package nl.mprog.project.stijn.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import nl.mprog.project.stijn.R;

public class ProgressActivity extends AppCompatActivity implements View.OnClickListener {

    // fields
    public TextView myProgressTitle;
    public Button homeButton;

    /**
     * TODO
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        // Initializes views
        init();
    }

    /**
     * TODO
     */
    public void init(){

        myProgressTitle = (TextView) findViewById(R.id.myProgressTitle);

        homeButton = (Button) findViewById(R.id.homeButton);

        homeButton.setOnClickListener(this);
    }

    /**
     * TODO
     */
    @Override
    public void onClick(View v) {
        backToHome();
    }

    public void backToHome(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
