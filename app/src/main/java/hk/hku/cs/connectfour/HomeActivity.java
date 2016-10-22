package hk.hku.cs.connectfour;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    Button start_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        start_button = (Button)findViewById(R.id.start_button);

        start_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.start_button){

            Intent intent = new Intent(getBaseContext(), GameActivity.class);
            startActivity(intent);
        }
    }
}
