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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button new_game;
    Button retract;
    TableLayout board;
    String TURN = "RED";
    String gameStatus = "GO";
    ImageView colorTurn;
    TextView turnText;
    String[][] gameState = new String[6][7];
    String[][] copyGameState = new String[6][7];
    int[][] drawBoard = new int[6][7];
    ArrayList<int[]> historical = new ArrayList<int[]>();
    ArrayList<int[]> winners = new ArrayList<>();


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
            copyGameState = new String[6][7];
            historical = new ArrayList<int[]>();
            turnText = (TextView) findViewById(R.id.turnText);
            gameStatus = "GO";
            draw(gameState);
            turnText.setText(R.string.turn);
            TURN = "RED";
            checkTurnColor();
        }
        if (gameStatus == "GO"){

            if (v instanceof ImageView){
                String playerMove = v.getResources().getResourceName(v.getId());
                int col = Character.getNumericValue(playerMove.charAt(playerMove.length()-1));
                int row = Character.getNumericValue(playerMove.charAt(playerMove.length()-2));
                int[] newMove = new int[2];
                newMove[0] = row;
                newMove[1] = col;

                if (TURN == "RED"){
                    System.out.println("what row did we press!?  " + row);
                    System.out.println("what col did we press!?  " + col);
                    if (checkLegalMove(newMove,historical,TURN)){
                        checkWin(gameState,drawBoard, historical);
                        draw(gameState);
                        TURN = "GREEN";
                        checkTurnColor();
                    }
                    System.out.println(Arrays.deepToString(gameState));
                } else if (TURN == "GREEN"){
                    if (checkLegalMove(newMove,historical,TURN)){
                        checkWin(gameState,drawBoard, historical);
                        draw(gameState);
                        TURN = "RED";
                        checkTurnColor();
                    }
                    System.out.println("WHOSE TURN: " + TURN);
                }

            }
            if (v.getId() == R.id.retract && historical.size() > 0){
                retractMove(gameState, historical);
                checkTurnColor();
                draw(gameState);
                gameStatus = "GO";
            }
        }
    }

    public void draw(String[][] gameState){
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
                } else if (gameState[j][k] == "rw"){
                    setMove = (ImageView) findViewById(drawBoard[j][k]);
                    setMove.setImageResource(R.drawable.red_wint);
                } else if (gameState[j][k] == "gw"){
                    setMove = (ImageView) findViewById(drawBoard[j][k]);
                    setMove.setImageResource(R.drawable.green_wint);
                } else if (gameState[j][k] == "w" && TURN == "RED") {
                    setMove = (ImageView) findViewById(drawBoard[j][k]);
                    setMove.setImageResource(R.drawable.red_wint);
                } else if (gameState[j][k] == "w" && TURN == "GREEN"){
                    setMove = (ImageView) findViewById(drawBoard[j][k]);
                    setMove.setImageResource(R.drawable.green_wint);
                }
            }
        }
    }



    public void retractMove(String[][] gameState, ArrayList<int[]> historical){
        //retract move
        int[] move = historical.remove(historical.size() - 1);
        gameState[move[0]][move[1]] = null;
        copyGameState[move[0]][move[1]] = null;
        if (TURN == "RED")
            TURN = "GREEN";
        else TURN = "RED";
    }

    public void checkTurnColor(){
        colorTurn = (ImageView) findViewById(R.id.turn);
        turnText = (TextView) findViewById(R.id.turnText);
        if (gameStatus == "END"){
            turnText.setText(R.string.winner);
            if (TURN == "RED"){
                colorTurn.setImageResource(R.drawable.green_t);
            }
            if (TURN == "GREEN"){
                colorTurn.setImageResource(R.drawable.red_t);
            }
        }
        else if (TURN == "RED" && gameStatus == "GO"){
            colorTurn.setImageResource(R.drawable.red_t);
        }
        else if (TURN == "GREEN" && gameStatus == "GO"){
            colorTurn.setImageResource(R.drawable.green_t);
        }

    }

    public boolean checkLegalMove(int[] newMove, ArrayList<int[]> historical, String TURN){
        int col = newMove[1];
        colorTurn = (ImageView) findViewById(R.id.turn);

        if (!Arrays.asList(historical).contains(newMove)){
            for (int i=0; i<gameState.length; i++){
                if (gameState[i][col] == null){
                    if (TURN == "RED"){
                        gameState[i][col] = "r";
                        copyGameState[i][col] = "r";
                        newMove[0] = i;
                        historical.add(newMove);
                        return true;
                    }
                    else if (TURN == "GREEN"){
                        gameState[i][col] = "g";
                        copyGameState[i][col] = "g";
                        newMove[0] = i;
                        historical.add(newMove);
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    public String checkWin(String [][] gameState, int[][] drawBoard, ArrayList<int[]> historical){
        // if win change the image of winning pieces
        if (historical.size() > 0) {
            gameStatus = "GO";
            int[] move = historical.get(historical.size() - 1);
            int row = move[0];
            int col = move[1];
            String lastColor = copyGameState[row][col];
            System.out.println("CHECKING FOR WIN : " + lastColor);
            //vertical
            if (row>2 && copyGameState[row-1][col]==lastColor &&
                    copyGameState[row-2][col]==lastColor && copyGameState[row-3][col]==lastColor){
                gameState[row][col] = "w";
                gameState[row-1][col] = "w";
                gameState[row-2][col] = "w";
                gameState[row-3][col] = "w";
                gameStatus = "END";

            }
            //horizontal xoxx
            System.out.println("lastColor = " +lastColor);
            System.out.println(Arrays.deepToString(gameState));
            System.out.println("copy: " + Arrays.deepToString(copyGameState));
            if (col>0 && col<5 && copyGameState[row][col+1]== lastColor &&
                    copyGameState[row][col+2] == lastColor && copyGameState[row][col-1] == lastColor){
                gameState[row][col] = "w";
                gameState[row][col+1] = "w";
                gameState[row][col+2] = "w";
                gameState[row][col-1] = "w";
                gameStatus = "END";

            }
            //horizontal xxox
            System.out.println("lastColor = " +lastColor);
            System.out.println(Arrays.deepToString(gameState));
            System.out.println("copy: " + Arrays.deepToString(copyGameState));
            if (col<6 && col>1 && copyGameState[row][col-1]== lastColor &&
                    copyGameState[row][col-2] == lastColor && copyGameState[row][col+1] == lastColor){
                gameState[row][col] = "w";
                gameState[row][col-1] = "w";
                gameState[row][col-2] = "w";
                gameState[row][col+1] = "w";
                gameStatus = "END";
            }
            // horizontal oxxx
            System.out.println("lastColor = " +lastColor);
            System.out.println(Arrays.deepToString(gameState));
            System.out.println("copy: " + Arrays.deepToString(copyGameState));
            if (col<4 && copyGameState[row][col+1]== lastColor &&
                    copyGameState[row][col+2] == lastColor && copyGameState[row][col+3] == lastColor){
                gameState[row][col] = "w";
                gameState[row][col+1] = "w";
                gameState[row][col+2] = "w";
                gameState[row][col+3] = "w";
                gameStatus = "END";
            }
            // horizontal xxxo
            System.out.println("lastColor = " +lastColor);
            System.out.println(Arrays.deepToString(gameState));
            System.out.println("copy: " + Arrays.deepToString(copyGameState));
            if (col>2 && copyGameState[row][col-1]== lastColor &&
                    copyGameState[row][col-2] == lastColor && copyGameState[row][col-3] == lastColor){
                gameState[row][col] = "w";
                gameState[row][col-1] = "w";
                gameState[row][col-2] = "w";
                gameState[row][col-3] = "w";
                gameStatus = "END";

            }
            //diagonal right xoxx
            if (row<4 && row>0 && col>0 && col<5 &&
                    copyGameState[row+1][col+1]== lastColor &&
                    copyGameState[row+2][col+2] == lastColor &&
                    copyGameState[row-1][col-1] == lastColor){
                gameState[row][col] = "w";
                gameState[row+2][col+2] = "w";
                gameState[row+1][col+1] = "w";
                gameState[row-1][col-1] = "w";
                gameStatus = "END";

            }
            // diagonal right xxox
            if (row<5 && row>1 && col>1 && col<6 &&
                    copyGameState[row+1][col+1]== lastColor &&
                    copyGameState[row-1][col-1] == lastColor &&
                    copyGameState[row-2][col-2] == lastColor){
                gameState[row][col] = "w";
                gameState[row+1][col+1] = "w";
                gameState[row-1][col-1] = "w";
                gameState[row-2][col-2] = "w";
                gameStatus = "END";

            }
            //diagonal right oxxx
            if (row<3 && col<4 &&
                    copyGameState[row+1][col+1]== lastColor &&
                    copyGameState[row+2][col+2] == lastColor &&
                    copyGameState[row+3][col+3] == lastColor){
                gameState[row][col] = "w";
                gameState[row+1][col+1] = "w";
                gameState[row+2][col+2] = "w";
                gameState[row+3][col+3] = "w";
                gameStatus = "END";
            }
            // diagonal right xxxo
            if (row>2 && col>2 &&
                    copyGameState[row-1][col-1]== lastColor &&
                    copyGameState[row-2][col-2] == lastColor &&
                    copyGameState[row-3][col-3] == lastColor){
                gameState[row][col] = "w";
                gameState[row-1][col-1] = "w";
                gameState[row-2][col-2] = "w";
                gameState[row-3][col-3] = "w";
                gameStatus = "END";
            }
            //diagonal left xxox
            if (row<4 && row>0 && col>1 && col<5 &&
                    copyGameState[row+1][col-1]== lastColor &&
                    copyGameState[row+2][col-2] == lastColor &&
                    copyGameState[row-1][col+1] == lastColor){
                gameState[row][col] = "w";
                gameState[row+1][col-1] = "w";
                gameState[row+2][col-2] = "w";
                gameState[row-1][col+1] = "w";
                gameStatus = "END";
            }
            //diagonal left xoxx
            if (row<5 && row>1 && col>0 && col<5 &&
                    copyGameState[row+1][col-1]== lastColor &&
                    copyGameState[row-1][col+1] == lastColor &&
                    copyGameState[row-2][col+2] == lastColor){
                gameState[row][col] = "w";
                gameState[row+1][col-1] = "w";
                gameState[row-2][col+2] = "w";
                gameState[row-1][col+1] = "w";
                gameStatus = "END";
            }
            // diagonal left oxxx
            if (row>2 && col<4 &&
                    copyGameState[row-1][col+1]== lastColor &&
                    copyGameState[row-2][col+2] == lastColor &&
                    copyGameState[row-3][col+3] == lastColor){
                gameState[row][col] = "w";
                gameState[row-1][col+1] = "w";
                gameState[row-2][col+2] = "w";
                gameState[row-3][col+3] = "w";
                gameStatus = "END";
            }
            // diagonal left xxxo
            if (row<3 && col>2 &&
                    copyGameState[row+1][col-1]== lastColor &&
                    copyGameState[row+2][col-2] == lastColor &&
                    copyGameState[row+3][col-3] == lastColor){
                gameState[row][col] = "w";
                gameState[row+1][col-1] = "w";
                gameState[row+2][col-2] = "w";
                gameState[row+3][col-3] = "w";
                gameStatus = "END";
            }


        }
        return gameStatus;
    }

}
