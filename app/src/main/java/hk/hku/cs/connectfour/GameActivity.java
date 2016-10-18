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
    int[][] drawBoard = new int[6][7];
    ArrayList<int[]> historical = new ArrayList<int[]>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        new_game = (Button)findViewById(R.id.new_game);
        retract = (Button)findViewById(R.id.retract);
        board = (TableLayout) findViewById(R.id.board);
        board.setShrinkAllColumns(true);


        //set OnClickListener for the entire board
        // add each ImageView ID to corresponding 2D array location
        for (int i=0; i<board.getChildCount(); i++){
            LinearLayout l = (LinearLayout) board.getChildAt(board.getChildCount()-1-i);
            for (int j=0; j<l.getChildCount(); j++){
                TableRow r = (TableRow) l.getChildAt(j);
                for (int k=0; k<r.getChildCount(); k++){
                    drawBoard[i][k] = r.getChildAt(k).getId();
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
            gameState = new String[6][7];
            historical = new ArrayList<int[]>();
            drawBoard(gameState);
        }
        if (v instanceof ImageView){
            String playerMove = v.getResources().getResourceName(v.getId());
            int col = Character.getNumericValue(playerMove.charAt(playerMove.length()-1));
            int row = Character.getNumericValue(playerMove.charAt(playerMove.length()-2));
            int[] newMove = new int[2];
            newMove[0] = row;
            newMove[1] = col;
            historical.add(newMove);

            if (TURN == "RED"){
                ImageView redMove;
                System.out.println("ZOMG!\n");
                System.out.println("what row did we press!?  " + row);
                System.out.println("what col did we press!?  " + col);
                System.out.println("WHOSE TURN: " + TURN);
                System.out.println("####################\n\n");
                gameState[row][col] = "r";
                System.out.println(Arrays.deepToString(gameState));
                System.out.println("####################\n\n");
                // TODO make new method
                redMove = (ImageView) findViewById(v.getId());
                redMove.setImageResource(R.drawable.red_t);
                TURN = "GREEN";
            } else if (TURN == "GREEN"){
                ImageView greenMove;
                gameState[row][col] = "g";
                greenMove = (ImageView) findViewById(v.getId());
                greenMove.setImageResource(R.drawable.green_t);
                System.out.println("WHOSE TURN: " + TURN);
                TURN = "RED";
            }
        }
        if (v.getId() == R.id.retract && historical.size() > 0){
            retractMove(gameState, historical);
            drawBoard(gameState);
            System.out.println("game state after retract is: " + Arrays.deepToString(gameState));
            System.out.println("drawBoard after retract is: " + Arrays.deepToString(drawBoard));
            System.out.println("game state after draw is: " + Arrays.deepToString(gameState));
        }
    }

    public void drawBoard(String[][] gameState){
        for (int j=0; j<gameState.length; j++){
            for(int k=0; k<gameState[j].length; k++){
                ImageView setMove = (ImageView) findViewById(drawBoard[j][k]);
                setMove.setImageResource(R.drawable.empty_t);
                if (gameState[j][k] == "r"){
                    setMove = (ImageView) findViewById(drawBoard[j][k]);
                    setMove.setImageResource(R.drawable.red_t);
                } else if (gameState[j][k] == "g"){
                    setMove = (ImageView) findViewById(drawBoard[j][k]);
                    setMove.setImageResource(R.drawable.green_t);

                }
            }
        }
    }

    public void checkWin(String [][] gameState, ImageView[][] drawBoard){
        // if win change the image of winning pieces
    }

    public void retractMove(String[][] gameState, ArrayList<int[]> historical){
        //retract move
        int[] move = historical.remove(historical.size() - 1);
        gameState[move[0]][move[1]] = null;
    }
}
