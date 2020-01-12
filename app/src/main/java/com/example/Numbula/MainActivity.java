package com.example.Numbula;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.*;
import android.widget.*;
import android.view.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int STARTING_UNDOS = 7;
    int numUndos = STARTING_UNDOS;
    int[][] array = {{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};
    int score = 0;
    int highScore = 0;

    ArrayList<Integer> moveList = new ArrayList<>();
    Button[][] grid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieves HighScore
        SharedPreferences prefs = this.getSharedPreferences("Numbula", Context.MODE_PRIVATE);
        highScore = prefs.getInt("highScore", 0); //0 is the default value
        setHighScore(highScore);
        addScore(0);

        ((Button) findViewById(R.id.undoButton)).setText("undo " + "(" + numUndos + ")");

        grid = new Button[][]{{findViewById(R.id.buttona1), findViewById(R.id.buttonb1),
                findViewById(R.id.buttonc1), findViewById(R.id.buttond1)},
                {findViewById(R.id.buttona2), findViewById(R.id.buttonb2),
                        findViewById(R.id.buttonc2), findViewById(R.id.buttond2)},
                {findViewById(R.id.buttona3), findViewById(R.id.buttonb3),
                        findViewById(R.id.buttonc3), findViewById(R.id.buttond3)},
                {findViewById(R.id.buttona4), findViewById(R.id.buttonb4),
                        findViewById(R.id.buttonc4), findViewById(R.id.buttond4)}};
        generate();
        copy();
        check();

        //******************************************************************************
        //******************************************************************************
        //Button Listeners
        //******************************************************************************
        grid[0][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(0, 0);
            }
        });
        grid[0][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(0, 1);
            }
        });
        grid[0][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(0, 2);
            }
        });
        grid[0][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(0, 3);
            }
        });
        //******************************************************************************
        grid[1][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(1, 0);
            }
        });
        grid[1][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(1, 1);
            }
        });
        grid[1][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(1, 2);
            }
        });
        grid[1][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(1, 3);
            }
        });
        //******************************************************************************
        grid[2][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(2, 0);
            }
        });
        grid[2][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(2, 1);
            }
        });
        grid[2][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(2, 2);
            }
        });
        grid[2][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(2, 3);
            }
        });
        //******************************************************************************
        grid[3][0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(3, 0);
            }
        });
        grid[3][1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(3, 1);
            }
        });
        grid[3][2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(3, 2);
            }
        });
        grid[3][3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(3, 3);
            }
        });
        //******************************************************************************
        //******************************************************************************
        (findViewById(R.id.undoButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (moveList.size() > 0) {
                    undo(moveList.get(moveList.size() - 1));
                    copy();
                }
            }
        });

        (findViewById(R.id.rerollButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addScore((int) (-0.5 * score));
                refresh();
            }
        });

        (findViewById(R.id.gameover)).setVisibility(View.GONE);
        (findViewById(R.id.gameover)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (findViewById(R.id.gameover)).setVisibility(View.GONE);
                gameOver();
            }
        });
        //******************************************************************************
    }


    //METHODS
    public void move(int i, int j) {
        if (array[i][j] != 0) {
            add(i, j, -1);
            moveHistory(10 * i + j);
            copy();
            check();
        }
    }

    public void moveHistory(int recentMove) {
        moveList.add(recentMove);
    }

    public void undo(int indices) {
        int row;
        int column;

        if (numUndos > 0) {
            row = indices / 10;
            column = indices % 10;

            add(row, column, 1);
            moveList.remove(moveList.get(moveList.size() - 1));

            addScore(-50);
            numUndos -= 1;
        }
        ((Button) findViewById(R.id.undoButton)).setText("undo " + "(" + numUndos + ")");
        check();
    }

    public void refresh() {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = 0;
            }
        }
        generate();
        copy();
        check();
        moveList.clear();
    }


    //Scoring
    public void addScore(int points) {
        score += points;
        ((TextView) findViewById(R.id.scoreView)).setText("score: " + "\n" + score);

        setHighScore(score);
    }

    public void setHighScore(int newScore) {
        if (newScore >= highScore) {
            highScore = newScore;

            //High Score
            SharedPreferences prefs = this.getSharedPreferences("Numbula", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("highScore", highScore);
            editor.commit();
        }

        ((TextView) findViewById(R.id.highscoreView)).setText("highscore: " + highScore);
    }


    public void generate() {
        double roll;
        int rollAmt = 0;
        double percent = Math.pow(1.006, score / 15 - 40) / 10;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                roll = Math.random();
                if (roll < percent) {
                    rollAmt = 1;
                }

                while (rollAmt > 0) {
                    add(i, j, 1);
                    rollAmt -= 1;
                }
            }
        }

        //Removes score addition if generated an all zero grid
        boolean allZero = true;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] != 0) {
                    allZero = false;
                }
            }
        }
        if (allZero) {
            addScore(-100);
        }
    }

    public void copy() {
        ((Button) findViewById(R.id.buttona1)).setText(Integer.toString(array[0][0]));
        ((Button) findViewById(R.id.buttonb1)).setText(Integer.toString(array[0][1]));
        ((Button) findViewById(R.id.buttonc1)).setText(Integer.toString(array[0][2]));
        ((Button) findViewById(R.id.buttond1)).setText(Integer.toString(array[0][3]));
        ((Button) findViewById(R.id.buttona2)).setText(Integer.toString(array[1][0]));
        ((Button) findViewById(R.id.buttonb2)).setText(Integer.toString(array[1][1]));
        ((Button) findViewById(R.id.buttonc2)).setText(Integer.toString(array[1][2]));
        ((Button) findViewById(R.id.buttond2)).setText(Integer.toString(array[1][3]));
        ((Button) findViewById(R.id.buttona3)).setText(Integer.toString(array[2][0]));
        ((Button) findViewById(R.id.buttonb3)).setText(Integer.toString(array[2][1]));
        ((Button) findViewById(R.id.buttonc3)).setText(Integer.toString(array[2][2]));
        ((Button) findViewById(R.id.buttond3)).setText(Integer.toString(array[2][3]));
        ((Button) findViewById(R.id.buttona4)).setText(Integer.toString(array[3][0]));
        ((Button) findViewById(R.id.buttonb4)).setText(Integer.toString(array[3][1]));
        ((Button) findViewById(R.id.buttonc4)).setText(Integer.toString(array[3][2]));
        ((Button) findViewById(R.id.buttond4)).setText(Integer.toString(array[3][3]));
    }

    public void add(int i, int j, int increment) {
        array[i][j] += increment;

        if (i == 0) {
            array[i + 1][j] += increment;
        } else if (i == array.length - 1) {
            array[i - 1][j] += increment;
        } else {
            array[i + 1][j] += increment;
            array[i - 1][j] += increment;
        }

        if (j == 0) {
            array[i][j + 1] += increment;
        } else if (j == array.length - 1) {
            array[i][j - 1] += increment;
        } else {
            array[i][j + 1] += increment;
            array[i][j - 1] += increment;
        }
    }

    //Checks if the board has been cleared or if a mistake has been made
    public void check() {
        boolean allZero = true;
        boolean anyNegative = false;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                if (array[i][j] != 0) {
                    grid[i][j].setTextScaleX(1);
                    allZero = false;

                    if (array[i][j] < 0) {
                        anyNegative = true;
                    }
                } else {
                    grid[i][j].setTextScaleX(0);
                }
            }
        }

        //Refreshes the board
        if (allZero) {
            addScore(100);
            moveList.clear();
            generate();
            copy();
            check();
        } else if (anyNegative && (numUndos == 0)) {
            (findViewById(R.id.gameover)).setVisibility(View.VISIBLE);
            ((Button) findViewById(R.id.gameover)).setText("game over" + "\n" + "score: " + score + "\n" + "press to try again");
        }
    }

    public void gameOver() {
        numUndos = STARTING_UNDOS;
        score = 0;
        ((TextView) findViewById(R.id.scoreView)).setText("score" + "\n" + score);
        refresh();
    }
}
