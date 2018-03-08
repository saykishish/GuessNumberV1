package com.example.asus.guessnumberv1;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private Button guess;
    private EditText input;
    private TextView log, mesg;
    private String answer;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guess = findViewById(R.id.guess);
        input = findViewById(R.id.input);
        log = findViewById(R.id.log);
        mesg = findViewById(R.id.mesg);

        guess.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                doGuess();
            }
        });//guess setOnClickListener

        mesg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mesg.setVisibility(View.GONE);
            }
        });//mesg setOnClickListener

        initGame();
    }//onCreate

    private void initGame(){
        answer = createAnswer(3);
        input.setText("");
        log.setText("");
        answer = "123"; //解答
        Log.v("brad", answer);
    }//初始化
    
    private void doGuess()
    {
        //取得 輸入 顯示於textView中
        String strInput = input.getText().toString();
        input.setText("");
        //log.setText(strInput);

        String result = checkAB(answer, strInput);
        log.append(strInput + ":" + result + "\n");

        if(result.equals("3A0B")) {
         showDialog();
        }//if

    }//doGuess()

    private void showDialog()
    {
        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Winner");
        builder.setMessage("恭喜答對");
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initGame();
            }
        });//OnClickListener()
        alert = builder.create();
        alert.show();
    }//showDialog

    static String checkAB(String a, String g)
    {
        int A, B; A = B = 0;

        for (int i=0 ;i<a.length(); i++) {
            if (g.charAt(i) == a.charAt(i)) {
                A++;
            }else if (a.indexOf(g.charAt(i)) != -1) {
                B++;
            }
        }

        return A + "A" + B +"B";
    }//檢查正確與否

    static String createAnswer(int d)
    {
        int[] poker = new int[10];
        for (int i=0; i<poker.length; i++) poker[i] = i;

        for (int i=poker.length; i>0; i--) {
            int rand = (int)(Math.random()*i);
            int temp = poker[rand];
            poker[rand] = poker[i-1];
            poker[i-1] = temp;
        }

        String ret = "";
        for (int i=0; i<d; i++) {
            ret += poker[i];
        }

        return ret;
    }//產生謎底  檢查正確與否


    public void end(View view) {
        finish();
    }//end

    public void reset(View view) {
        initGame();
    }//reset
}//CLASS
