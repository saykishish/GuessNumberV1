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


//恩 若玩家沒有輸入 與 setting 對應的碼數 會散退。 所以需要 請輸入正確幾碼的提醒視窗
//喔 在使用者輸入不對的時候 跳出提醒 也就是按猜的時候 在doGuess()中判斷 是否==choiceSettingThreeFourFive

public class MainActivity extends AppCompatActivity
{
    private Button guess, setting;
    private EditText input;
    private TextView log, mesg;
    private String answer;

    //先會做記數的動作  到了第九次 還可以再玩 再猜 沒中 再來第十次 也沒猜中 ，就進入if判斷  else印出
    private int count; //做加加
    //private int initZero; //上限

    private int choiceSettingThreeFourFive;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        guess = findViewById(R.id.guess);
        input = findViewById(R.id.input);
        log = findViewById(R.id.log);
        mesg = findViewById(R.id.mesg);

        setting = findViewById(R.id.setting);


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



        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingDialog();
            }
        });//setting setOnClickListener。 不是UIUX那邊的Button 有onlick過來 java寫方法 就不會nullPointer閃退了嗎?
        //就像老師的 Exit Button 有設置onclick java這邊直接寫 finish()壓?!


        initGame();

        choiceSettingThreeFourFive = 3;  //預設是3 個意思吧 不是
    }//onCreate

    private void initGame(){
        //answer = createAnswer(3);
        answer = createAnswer(choiceSettingThreeFourFive);  //按三時 只有製造三個位數的答案。關鍵在製造答案的時候要與 選擇幾碼吻合
        input.setText("");
        log.setText("");
        //answer = "123"; //解答
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

        //if(count == 10){   //這樣想太快了  先想計數
//            showLoseDialog();
//        }//if


        if(strInput.length() != choiceSettingThreeFourFive){  //提影輸入長度 要等同於設定的碼數
            showWarningDialig();
        }else {


            String result = checkAB(answer, strInput);
            log.append(strInput + ":" + result + "\n");


            //如果 使用者輸入的結果 等同於 3A0B 的話就 贏視窗。  否則 次數尚未等於10還是可以繼續猜，等於10 就輸視窗
            if(result.equals("3A0B")) {
                showWinDialog();
            }else if (count == 10){
                showLoseDialog();
            }//if...else...if


        }//if...else

    }//doGuess()

    private void showWarningDialig() {
        AlertDialog alert = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("你沒有輸入設定時候一樣的碼數壓...XD");
        builder.setPositiveButton("I GOT it!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                initGame();
            }
        });//onClick

        alert = builder.create();
        alert.show();
    }//

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

    public void setting(View view) {
        //用這裡 不用這裡都可以  只是寫全域變數 加獨立方setting方法好維護  都可以 看自己吧
        showSettingDialog();
    }//setting

    private void showSettingDialog(){
        AlertDialog alert = null;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("請選擇要玩幾位數");

        final CharSequence[] choiceSetting = {"3", "4", "5"};
        builder.setSingleChoiceItems(choiceSetting, 1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //喔  -> 當按下去時 做甚麼事。 按三時 只有製造三個位數的答案 依此類推
                //所以將 choiceSetting[]陣列裝i  指向給 全域變數choiceSetting
                choiceSettingThreeFourFive = Integer.parseInt(choiceSetting[i].toString());

            }
        });//onClick()

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //按下ok時  回到一開始 開始玩遊戲
                initGame();
            }
        });

        alert = builder.create();
        alert.show();
    }//showSettingDialog()

}//CLASS
