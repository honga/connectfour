package hk.hku.cs.connectfour;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    int COL = 7;
    int ROW = 6;
    Button new_game;
    Button retract;
    TableLayout board;
    ImageView c00,c01,c02,c03,c04,c05,c06,
              c10,c11,c12,c13,c14,c15,c16,
              c20,c21,c22,c23,c24,c25,c26,
              c30,c31,c32,c33,c34,c35,c36,
              c40,c41,c42,c43,c44,c45,c46,
              c50,c51,c52,c53,c54,c55,c56;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        new_game = (Button)findViewById(R.id.new_game);
        retract = (Button)findViewById(R.id.retract);
        board = (TableLayout) findViewById(R.id.board);
        c51 = (ImageView) findViewById(R.id.c51);
        board.setShrinkAllColumns(true);
        int childCount = board.getChildCount();


        new_game.setOnClickListener(this);
        retract.setOnClickListener(this);
        c51.setOnClickListener(this);




        Intent intent = this.getIntent();
        //int[][] board = new int[6][7];


    }

    public void onClick(View v){
        if (v.getId() == R.id.new_game){
            // do sth
            System.out.println("################\n" +
                                "AGAIN AGAIN!\n" +
                                "################");
            c51 = (ImageView) findViewById(R.id.c51);
            c51.setImageResource(R.drawable.green_t);

        }

        if (v.getId() == R.id.retract){
            // retract moves nonstop
            System.out.println("$$$$$$$$$$$$\n" +
                                "GG NO RE\n" +
                                "$$$$$$$$$$$$\n");
            c51 = (ImageView) findViewById(R.id.c51);
            c51.setImageResource(R.drawable.red_t);
        }
        if (v.getId() == R.id.c51){
            c51 = (ImageView) findViewById(R.id.c51);
            c51.setImageResource(R.drawable.red_wint);
        }
    }
}
