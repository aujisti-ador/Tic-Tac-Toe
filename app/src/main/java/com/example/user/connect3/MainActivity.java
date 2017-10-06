package com.example.user.connect3;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // here cross=0 and circle=1
    int activePlayer = 0;
    boolean gameIsActive = true;

    // 2 means unplayed
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    @SuppressLint("SetTextI18n")
    public void dropIn(View view) {
        ImageView counter = (ImageView) view;
        //System.out.println(counter.getTag().toString());
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        if (gameState[tappedCounter] == 2 && gameIsActive) {
            gameState[tappedCounter] = activePlayer;
            counter.setTranslationY(-1000f);
            //ok
            if (activePlayer == 0) {
                counter.setImageResource(R.drawable.cross);
                //Toast.makeText(MainActivity.this, "Player 2's turn...", Toast.LENGTH_SHORT).show();
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.circle);
                //Toast.makeText(MainActivity.this, "Player 1's turn...", Toast.LENGTH_SHORT).show();
                activePlayer = 0;

            }
            counter.animate().translationYBy(1000f).rotation(360f).setDuration(300);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                        gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                        gameState[winningPosition[0]] != 2) {

                    gameIsActive = false;

                    String winner = "Circle";
                    if (gameState[winningPosition[0]] == 0) {
                        winner = "Cross";
                    }

                    //Someone win
                    TextView winnerTxt = (TextView) findViewById(R.id.winnerTxt);
                    winnerTxt.setText(winner + " has won!!!");
                    LinearLayout layout = (LinearLayout) findViewById(R.id.vrtclLayout);
                    GridLayout boardLayout = (GridLayout) findViewById(R.id.boardLayout);
                    layout.setVisibility(View.VISIBLE);
                    layout.setAlpha((float) 1.0);
                    boardLayout.setVisibility(View.INVISIBLE);
                    boardLayout.setAlpha(0f);
                } else {
                    boolean gameIsOver = true;
                    for (int counterState : gameState) {
                        if (counterState == 2) {
                            gameIsOver = false;
                        }
                    }
                    if (gameIsOver) {
                        TextView winnerTxt = (TextView) findViewById(R.id.winnerTxt);
                        winnerTxt.setText("It's a draw!!!");
                        LinearLayout layout = (LinearLayout) findViewById(R.id.vrtclLayout);
                        layout.setVisibility(View.VISIBLE);
                        layout.setAlpha((float) 1.0);
                        GridLayout boardLayout = (GridLayout) findViewById(R.id.boardLayout);
                        boardLayout.setVisibility(View.INVISIBLE);
                        boardLayout.setAlpha(0f);
                    }
                }
            }
        }
    }

    public void playAgain(View view) {
        gameIsActive = true;
        LinearLayout layout = (LinearLayout) findViewById(R.id.vrtclLayout);
        layout.setVisibility(View.INVISIBLE);

        GridLayout boardLayout = (GridLayout) findViewById(R.id.boardLayout);
        boardLayout.setVisibility(View.VISIBLE);
        boardLayout.setAlpha(1f);

        activePlayer = 0;
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }
        for (int i = 0; i < boardLayout.getChildCount(); i++) {
            ((ImageView) boardLayout.getChildAt(i)).setImageResource(0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void exit(View view) {
        finish();
    }
}
