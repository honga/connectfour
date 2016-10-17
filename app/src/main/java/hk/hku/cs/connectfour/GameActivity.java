package hk.hku.cs.connectfour;

import android.content.Intent;
import android.media.Image;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    int COL = 7;
    int ROW = 6;
    Button new_game;
    Button retract;
    TableLayout board;
    String TURN = "RED";
    String[][] gameState = new String[6][7];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        new_game = (Button)findViewById(R.id.new_game);
        retract = (Button)findViewById(R.id.retract);
        board = (TableLayout) findViewById(R.id.board);
        board.setShrinkAllColumns(true);
        LinearLayout l;
        TableRow r;

        //set OnClickListener for the entire board
        for (int i=0;i<board.getChildCount();i++){
            l = (LinearLayout) board.getChildAt(i);
            for (int j=0;j<l.getChildCount();j++){
                r = (TableRow) l.getChildAt(j);
                for (int k=0;k<r.getChildCount();k++){
                    r.getChildAt(k).setOnClickListener(this);
                }
            }
        }

        new_game.setOnClickListener(this);
        retract.setOnClickListener(this);
        Intent intent = this.getIntent();
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

        if (v instanceof ImageView){
            String playerMove = v.getResources().getResourceName(v.getId());
            int col = Character.getNumericValue(playerMove.charAt(playerMove.length()-1));
            int row = Character.getNumericValue(playerMove.charAt(playerMove.length()-2));
            if (TURN == "RED"){
                ImageView redMove;
                System.out.println("ZOMG!\n");
                System.out.println("what row did we press!?  " + row);
                System.out.println("what col did we press!?  " + col);
                System.out.println("WHOSE TURN: " + TURN);
                System.out.println("####################\n\n");
                System.out.println(Arrays.deepToString(gameState));
                System.out.println("####################\n\n");
                // TODO make new method
                redMove = (ImageView) findViewById(v.getId());
                redMove.setImageResource(R.drawable.red_t);
                TURN = "GREEN";
            } else if (TURN == "GREEN"){
                ImageView greenMove;
                greenMove = (ImageView) findViewById(v.getId());
                greenMove.setImageResource(R.drawable.green_t);
                System.out.println("WHOSE TURN: " + TURN);
                TURN = "RED";
            }
        }
    }
}
