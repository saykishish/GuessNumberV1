package com.example.asus.guessnumberv1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private Button guess;
    private EditText input;
    private TextView log, mesg;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guess = findViewById(R.id.guess);
        input = findViewById(R.id.input);
        log = findViewById(R.id.log);

        guess.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                doGuess();
            }
        });//guess setOnClick

    }//onCreate

    private void doGuess()
    {
        //取得 輸入 顯示於textView中
        String strInput = input.getText().toString();
        log.setText(strInput);

    }//doGuess()


}//CLASS
