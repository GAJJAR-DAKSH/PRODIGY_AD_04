package com.example.prodigy_ad_04;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button[] buttons = new Button[9];
    TextView txtStatus;
    Button btnReset;

    boolean playerXTurn = true;
    int moveCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtStatus = findViewById(R.id.txtStatus);
        btnReset = findViewById(R.id.btnReset);

        for (int i = 0; i < 9; i++) {
            String buttonID = "btn" + i;
            int resID = getResources().getIdentifier(
                    buttonID,
                    "id",
                    getPackageName()
            );

            buttons[i] = findViewById(resID);

            int finalI = i;

            buttons[i].setOnClickListener(v -> {
                if (!buttons[finalI].getText().toString().equals(""))
                    return;

                if (playerXTurn) {
                    buttons[finalI].setText("X");
                    txtStatus.setText("Player O Turn");
                } else {
                    buttons[finalI].setText("O");
                    txtStatus.setText("Player X Turn");
                }

                moveCount++;

                if (checkWinner()) {
                    String winner = playerXTurn ? "X" : "O";
                    txtStatus.setText("Player " + winner + " Wins!");
                    disableButtons();
                } else if (moveCount == 9) {
                    txtStatus.setText("Match Draw!");
                }

                playerXTurn = !playerXTurn;
            });
        }

        btnReset.setOnClickListener(v -> resetGame());
    }

    private boolean checkWinner() {

        String[][] wins = {
                {buttons[0].getText().toString(), buttons[1].getText().toString(), buttons[2].getText().toString()},
                {buttons[3].getText().toString(), buttons[4].getText().toString(), buttons[5].getText().toString()},
                {buttons[6].getText().toString(), buttons[7].getText().toString(), buttons[8].getText().toString()},
                {buttons[0].getText().toString(), buttons[3].getText().toString(), buttons[6].getText().toString()},
                {buttons[1].getText().toString(), buttons[4].getText().toString(), buttons[7].getText().toString()},
                {buttons[2].getText().toString(), buttons[5].getText().toString(), buttons[8].getText().toString()},
                {buttons[0].getText().toString(), buttons[4].getText().toString(), buttons[8].getText().toString()},
                {buttons[2].getText().toString(), buttons[4].getText().toString(), buttons[6].getText().toString()}
        };
        for (String[] win : wins) {
            if (!win[0].equals("") &&
                    win[0].equals(win[1]) &&
                    win[1].equals(win[2])) {
                return true;
            }
        }

        return false;
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

    private void resetGame() {

        for (Button button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }

        moveCount = 0;
        playerXTurn = true;

        txtStatus.setText("Player X Turn");
    }
}
