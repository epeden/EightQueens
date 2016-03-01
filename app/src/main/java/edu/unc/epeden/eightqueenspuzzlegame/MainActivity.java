package edu.unc.epeden.eightqueenspuzzlegame;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static edu.unc.epeden.eightqueenspuzzlegame.R.id.myGridLayout;

public class MainActivity extends AppCompatActivity {

    private EightQueens game;
    private ImageButton board[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        game = new EightQueens();

        board = new ImageButton[8][8];
        GridLayout mylayout = (GridLayout) findViewById(myGridLayout);


        for(int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                board[x][y] = new ImageButton(MainActivity.this);
                board[x][y].setId((10 * x) + y);
                board[x][y].setMinimumWidth(120);
                board[x][y].setMinimumHeight(120);
                board[x][y].setMaxHeight(120);
                board[x][y].setMaxWidth(120);
                board[x][y].setTag(R.string.not_selected);
                if ((x + y) % 2 == 0) {
                    board[x][y].setBackgroundColor(Color.WHITE);
                } else {
                    board[x][y].setBackgroundColor(Color.BLACK);
                }
                board[x][y].setPadding(1, 1, 1, 1);
                mylayout.addView(board[x][y]);
            }
        }

        for(int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {

                board[x][y].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        int a = v.getId();
                        int y = (v.getId()) % 10;
                        int x = (v.getId() - y) / 10;
                        int bx = 0;
                        int by = 0;
                        TextView tv = (TextView) findViewById(R.id.bottomTextview);
                        tv.setText("Clicked: "+x+", "+y);
                        TextView tv2 = (TextView) findViewById(R.id.bottomTextview2);

                        String s = game.move(x,y);

                        if (s == "wrong_column") {
                            //Toast.makeText(getBaseContext(), "Click the next column to continue solving the puzzle!", Toast.LENGTH_SHORT).show();
                            tv2.setText("Click the leftmost empty column to continue");
                        } else if (s == "success") {
                            board[x][y].setImageResource(R.drawable.queen2);
                            board[x][y].setScaleType(ImageView.ScaleType.FIT_CENTER);
                            board[x][y].setAdjustViewBounds(true);
                            board[x][y].setTag(R.string.selected);
                            tv2.setText("Queen " +(x+1)+ " moved to row "+ y);
                        } else if (s == "bad_move"){
                            Toast.makeText(getBaseContext(),"Bad Move", Toast.LENGTH_SHORT).show();
                            board[x][y].setTag(R.string.selected);
                            board[x][y].setImageResource(R.drawable.queen2);
                            board[x][y].setScaleType(ImageView.ScaleType.FIT_CENTER);
                            board[x][y].setAdjustViewBounds(true);
                            bx = game.getBadX();
                            by = game.getBadY();
                            board[bx][by].setImageResource(R.drawable.red_queen);
                            board[bx][by].setAdjustViewBounds(true);
                            board[bx][by].setScaleType(ImageView.ScaleType.FIT_CENTER);
                            tv2.setText("Error from queen at " + bx + ", " + by);
                            game.setBoardStuck(true);
                        } else if (s == "last_column") {
                            if (board[x][y].getTag().equals(R.string.selected)) {
                                board[x][y].setImageResource(0);
                                board[x][y].setTag(R.string.not_selected);
                                game.backtrace();
                                resetReds();
                                tv2.setText("Removed queen from "+x+", "+y);
                            }
                        } else if (s == "board_stuck") {
                            tv2.setText("Correct the previous error before continuing");
                        }

                    }
                });
            }
        }

    }

    public void resetReds() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (board[x][y].getTag().equals(R.string.selected)) {
                    board[x][y].setImageResource(R.drawable.queen2);
                    board[x][y].setScaleType(ImageView.ScaleType.FIT_CENTER);
                    board[x][y].setAdjustViewBounds(true);
                }
            }
        }
    }

    public void solvePuzzle(View v) {
        int[] solution = game.solve();
        resetBoard(board[0][0]);
        for (int x = 0; x < 8; x++) {
            board[x][solution[x]].setImageResource(R.drawable.queen2);
            board[x][solution[x]].setScaleType(ImageView.ScaleType.FIT_CENTER);
            board[x][solution[x]].setAdjustViewBounds(true);
        }
        TextView tv2 = (TextView) findViewById(R.id.bottomTextview2);
        tv2.setText("Puzzle Solved");
    }

    public void solveAll(View v) {
        resetBoard(board[0][0]);
        String s;
        for (int i = 0; i < 92; i++) {
            s = game.solveAllBoards();
            TextView tv3 = (TextView) findViewById(R.id.textView2);
            tv3.setText(s);
        }
    }
    public void resetBoard(View v) {
        game = new EightQueens();
        TextView tv2 = (TextView) findViewById(R.id.bottomTextview2);
        tv2.setText("Board Reset");
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                board[x][y].setTag(R.string.not_selected);
                board[x][y].setImageResource(0);
                if ((x + y) % 2 == 0) {
                    board[x][y].setBackgroundColor(Color.WHITE);
                } else {
                    board[x][y].setBackgroundColor(Color.BLACK);
                }
            }

        }
    }

}
