package hk.hku.cs.connectfour;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleAdapter;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button new_game;
    Button retract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        new_game = (Button)findViewById(R.id.new_game);
        retract = (Button)findViewById(R.id.retract);

        new_game.setOnClickListener(this);
        retract.setOnClickListener(this);




        Intent intent = this.getIntent();
        int[][] board = new int[6][7];


    }

    public void onClick(View v){
        if (v.getId() == R.id.new_game){
            // do sth
            System.out.println("################\n" +
                                "AGAIN AGAIN!\n" +
                                "################");
        }

        if (v.getId() == R.id.retract){
            // retract moves nonstop
            System.out.println("$$$$$$$$$$$$\n" +
                                "GG NO RE\n" +
                                "$$$$$$$$$$$$\n");
        }
    }
}
