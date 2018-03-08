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
    private Button guess, restart, setting, exit;
    private EditText input;
    private TextView log, mesg;
    private String answer;

    //先會做記數的動作  到了第九次 還可以再玩 再猜 沒中 再來第十次 也沒猜中 ，就進入if判斷  else印出
    private int count; //做加加
    private int initZero; //上限

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guess = findViewById(R.id.guess);
        input = findViewById(R.id.input);
        log = findViewById(R.id.log);
        mesg = findViewById(R.id.mesg);
        //restart = findViewById(R.id.reset);
        setting = findViewById(R.id.setting);
        //exit = findViewById(R.id.exit);

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
        count = 0;

    }//initGame() 初始化

    private void doGuess()
    {
        //取得 輸入 顯示於textView中
        String strInput = input.getText().toString();
        input.setText("");
        //log.setText(strInput);

        count++;

//        if(count == 10){
//            showLoseDialog();
//        }//if

        String result = checkAB(answer, strInput);
        log.append(strInput + ":" + result + "\n");




        //如果 使用者輸入的結果 等同於 3A0B 的話就 贏視窗。  否則 次數尚未等於10還是可以繼續猜，等於10 就輸視窗
        if(result.equals("3A0B")) {
            showWinDialog();
        }else if (count == 10){
            showLoseDialog();
        }

    }//doGuess()

    private void showWinDialog()
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
    }//showWinDialog

    private void showLoseDialog()
    {
        AlertDialog alert = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Loser");
        builder.setMessage("你十次都猜錯搂，請重玩!");
        builder.setCancelable(false);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initGame();
            }
        });//OnClickListener()
        alert = builder.create();
        alert.show();
    }//showLoseDialog()


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
