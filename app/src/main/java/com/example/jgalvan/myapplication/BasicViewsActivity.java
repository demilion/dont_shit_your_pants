/**
 * Created by jgalvan on 07/12/2017.
 */

package com.example.jgalvan.myapplication;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;
import android.support.v7.app.AlertDialog;


public class BasicViewsActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private boolean timerHasStarted = false;
    public TextView text;
    private final long startTime = 41*1000;//41 * 1000;
    private final long interval = 1 * 1000;
    boolean isPaused = false;
    boolean endGame = false;

    public TextView txtComments;
    private long remainTime =0;
    private AdView mAdView;

    String statusCode = "001";
    String havePants = "y";
    String doorClose = "y";
    String isStanding = "y";
    String notFart = "y";
    String responseText = "";

    String[][] option = {

            {"fart soft", "You farted lightly. Relief!", "0011"},
            {"fart lightly", "You farted lightly. Relief!", "0012"},
            {"fart lightly", "You farted already. Another one will stain your pants.","0013"},
            {"open door","You try PUSHING the door open but it won't budge","0014"},

            {"take pills","Hopefully they'll start working in time.","101"},
            {"101", "The pills worked! You didn't shit your pants. Congratulation!","102"},
            {"102", "Uh oh. Wait a minute...","103"},
            {"103", "Awww, you just shit your pants! Maybe they didn't work so well...","104"},

            {"pull door","Oh, right..","112"},
            {"pull door","Oh, right..","102"},
            {"take off pants","You remove your pants","1120"},
            {"take off pants","You remove your pants","012"},

            {"sit on toilet","you sit on the toilet.","003"},
            {"enter bathroom","you sit on the toilet.","003"},
            {"go in bathroom","you sit on the toilet.","003"},
            {"sit on toilet","you sit on the toilet.","013"},
            {"enter bathroom","you sit on the toilet.","013"},
            {"go in bathroom","you sit on the toilet.","013"},

            {"look", "You're on a room with a door. You're wearing a shirt and pants. You have no hair.","00190"},
            {"look", "You're on a room with a door. You're wearing a shirt and not pants. You have no hair.","00180"},
            {"look ground", "You're on a room with a door. You're wearing a shirt and pants. You have no hair.","00170"},
            {"look ground", "You're on a room with a door. You're wearing a shirt and not pants. You have no hair.","00160"},
            {"look your pants", "","00150"},
            {"look at door","it's a door","00130"},
            {"look behind door", "it's a door","00120"},

            {"shit", "Your forgot to take your pants off!! You just shit your pants! Game Over!","104"},
            {"shit pants", "You just shit your pants! Game Over!","004"},
            {"shit", "You just shit the floor! Congratulation!","006"},
            {"shit", "You just shit the toilet! Congratulation!","007"},

            //{"shit","The game hasn't started yet but you just couldn't help yourself. You just shit your pants. Game Over","998"}
           // {"don't shit", "You shit your pants anyway! Game Over!","1001"},
            {"fart","You farted too hard and shit your pants! Maybe next time you shouldn't push it so hard","0002"},
            {"smash the door in","The exertion cause you to shit your pants! Game Over!","0003"},
            {"kick down door","The exertion cause you to shit your pants! Game Over!","0004"},

            {"die","your vision fodes and your hear a soft 'pdfffff' as you shit your pants. Game over.","999"},
            {"suicide","your vision fodes and your hear a soft 'pdfffff' as you shit your pants. Game over.","999"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtComments = (TextView) this.findViewById(R.id.textView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {

         String text =  ((TextView) findViewById(R.id.editText1)).getText().toString();

                /*
               Snackbar.make(view, setAction(text), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                ((TextView) findViewById(R.id.editText1)).setText("");
*/


                                       txtComments.setText(setAction(text));
                                       ((TextView) findViewById(R.id.editText1)).setText("");
                                   }
                               }
        );

        text = (TextView) this.findViewById(R.id.timer);
        countDownTimer = new MyCountDownTimer(startTime, interval);
        text.setText( text.getText() + String.valueOf(startTime / 1000));


        if (!timerHasStarted) {
            countDownTimer.start();
            timerHasStarted = true;
        } else {
            countDownTimer.cancel();
            timerHasStarted = false;
        }
    }

    public class MyCountDownTimer extends CountDownTimer {
        public MyCountDownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onFinish() {
            text.setText("Time's up!");
            gameOver();
        }

        @Override
        public void onTick(long millisUntilFinished) {

            if(isPaused || endGame ==true)
            {
                //If the user request to cancel or paused the
                //CountDownTimer we will cancel the current instance
                remainTime = millisUntilFinished;
                cancel();
            }
            else {
                //Display the remaining seconds to app interface
                //1 second = 1000 milliseconds
                text.setText("Timer: "  + millisUntilFinished / 1000);


                if((millisUntilFinished / 1000)==15)
                {
                    txtComments.setText("You're runnig out of time. You need to find a way to reduce the pressure in your gut.");
                }


            }
        }
    }
    private void gameOver() {
        //{"shit", "Your forgot to take your pants off!! You just shit your pants! Game Over!","104"},
        //{"shit pants", "You just shit your pants! Game Over!","004"},
        //{"take off pants","You remove your pants","1120"},
        //{"take off pants","You remove your pants","012"},
        //{"shit", "You just shit the floor! Congratulation!","006"},
        //{"shit", "You just shit the toilet! Congratulation!","007"},

        switch (statusCode) {
            case "012":
                setStatusCode("006");
                sendImageRequest(statusCode);
                txtComments.setText(reviewTextResponse(getStatusCode()));
                endGame = true;
                break;
            case "1120":
                setStatusCode("006");
                sendImageRequest(statusCode);
                txtComments.setText(reviewTextResponse(getStatusCode()));
                endGame = true;
                break;

            default:
                setStatusCode("004");
                sendImageRequest(statusCode);
                txtComments.setText(reviewTextResponse(getStatusCode()));
                endGame = true;
                break;
        }

    }

    private String setAction(String text) {
        String respuesta = "";
        if(endGame ==true) {
            txtComments.setText("Let's Play again");
            Intent intent = new Intent(BasicViewsActivity.this, MainMenu.class);
            intent.setAction(Intent.ACTION_SEND);
            startActivity(intent);

        }
        if(text.length()>0) {
            respuesta  = reviewTextRequest(text);
            sendImageRequest(statusCode);


                if (text.startsWith("die")||text.startsWith("Die")) {
                    Intent intent = new Intent(this, Category.class);
                    startActivity(intent);
                }

        }
        return respuesta;
    }

    private String reviewTextRequest(String text) {

        responseText = "I don't know how to " + text;

        for(int i= 0; i < option.length; i++) {
            if(text.contentEquals(option[i][0])) {
                if(reviewChangesStatus(option[i][2])==true) {
                    if(GameOver(getStatusCode())!=true) {

                        responseText = reviewTextResponse(getStatusCode());//option[i][1];
                        i = option.length;
                        break;
                    }else{
                        responseText = reviewTextResponse(getStatusCode());
                        i = option.length;
                        break;
                    }
                }else
                {
                    //saveStatus(statusCode);
                    i = option.length;
                    responseText = "Game Over!!";
                    endGame=true;
                    gameOver();
                    remainTime = 0;
                    return responseText;
                }
            }
        }
        return responseText;
    }

    private String reviewTextResponse(String txtCode) {

        String response = "";
        for (int i = 0; i < option.length; i++) {

            if (txtCode.contentEquals(option[i][2])) {
                response = option[i][1].toString();
            }
        }
        return response;
    }

    private boolean GameOver(String s) {

        switch (statusCode) {
            case "104":
                return true;
            case "013":
                return true;
            case "004":
                return true;
            default: return false;
        }

    }

    private void setStatusCode(String status) {
        statusCode = status;
    }
    private String getStatusCode() {
        return statusCode;
    }

    private boolean reviewChangesStatus(String _status) {

        boolean _return = false;
        switch (_status) {

            case "0011":
                if (havePants == "y" && doorClose == "y" && isStanding == "y" && notFart == "y") {
                    setStatusCode("0011");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "y" && doorClose == "n" && isStanding == "y" && notFart == "y") {
                    setStatusCode("102");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "n" && doorClose == "y" && isStanding == "y" && notFart == "y") {
                    setStatusCode("0011");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "n" && doorClose == "n" && isStanding == "y" && notFart == "y") {
                    setStatusCode("102");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "y" && doorClose == "y" && isStanding == "n" && notFart == "y") {
                    setStatusCode("0011");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "y" && doorClose == "n" && isStanding == "n" && notFart == "y") {
                    setStatusCode("102");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "n" && doorClose == "y" && isStanding == "n" && notFart == "y") {
                    setStatusCode("0011");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "n" && doorClose == "n" && isStanding == "y" && notFart == "y") {
                    setStatusCode("102");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
/**/
                if (havePants == "y" && doorClose == "y" && isStanding == "y" && notFart == "n") {
                    setStatusCode("0013");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "y" && doorClose == "n" && isStanding == "y" && notFart == "n") {
                    setStatusCode("1023");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "n" && doorClose == "y" && isStanding == "y" && notFart == "n") {
                    setStatusCode("0123");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "n" && doorClose == "n" && isStanding == "y" && notFart == "n") {
                    setStatusCode("1023");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "y" && doorClose == "n" && isStanding == "n" && notFart == "n") {
                    setStatusCode("1024");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "n" && doorClose == "y" && isStanding == "n" && notFart == "n") {
                    setStatusCode("0123");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "n" && doorClose == "n" && isStanding == "y" && notFart == "n") {
                    setStatusCode("1024");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
/*second fart*/
                if (havePants == "n" && notFart == "f") {
                    setStatusCode("006");
                    _return = true;
                    break;
                }

                if (havePants == "y" && notFart == "f") {
                    setStatusCode("004");
                    break;
                }
            case "0012":
                if (havePants == "y" && doorClose == "y" && isStanding == "y" && notFart == "y") {
                    setStatusCode("0011");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "y" && doorClose == "n" && isStanding == "y" && notFart == "y") {
                    setStatusCode("102");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "n" && doorClose == "y" && isStanding == "y" && notFart == "y") {
                    setStatusCode("0011");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "n" && doorClose == "n" && isStanding == "y" && notFart == "y") {
                    setStatusCode("102");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "y" && doorClose == "y" && isStanding == "n" && notFart == "y") {
                    setStatusCode("0011");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "y" && doorClose == "n" && isStanding == "n" && notFart == "y") {
                    setStatusCode("102");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "n" && doorClose == "y" && isStanding == "n" && notFart == "y") {
                    setStatusCode("0011");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
                if (havePants == "n" && doorClose == "n" && isStanding == "y" && notFart == "y") {
                    setStatusCode("102");
                    _return = true;
                    resetTimer();
                    notFart = "n";
                    break;
                }
/**/
                if (havePants == "y" && doorClose == "y" && isStanding == "y" && notFart == "n") {
                    setStatusCode("0013");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "y" && doorClose == "n" && isStanding == "y" && notFart == "n") {
                    setStatusCode("1023");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "n" && doorClose == "y" && isStanding == "y" && notFart == "n") {
                    setStatusCode("0123");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "n" && doorClose == "n" && isStanding == "y" && notFart == "n") {
                    setStatusCode("1023");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "y" && doorClose == "n" && isStanding == "n" && notFart == "n") {
                    setStatusCode("1024");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "n" && doorClose == "y" && isStanding == "n" && notFart == "n") {
                    setStatusCode("0123");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
                if (havePants == "n" && doorClose == "n" && isStanding == "y" && notFart == "n") {
                    setStatusCode("1024");
                    _return = true;
                    resetTimer();
                    notFart = "f";
                    break;
                }
/*second fart*/
                if (havePants == "n" && notFart == "f") {
                    setStatusCode("006");
                    text.setText("Timer: "  + 00 );
                    _return = true;
                    break;
                }

                if (havePants == "y" && notFart == "f") {
                    setStatusCode("004");
                    text.setText("Timer: "  + 00 );
                    break;
                }

            case "0013":
                if (havePants == "y" && doorClose == "y" && isStanding == "y") {
                    setStatusCode("0012");
                    _return = true;
                    break;
                }
            case "0014":
                if (havePants == "y" && doorClose == "y" && isStanding == "y") {
                    setStatusCode("0014");
                    _return = true;
                    break;
                }
            case "003"://{"sit on toile","you sit on the toilet.","003"},
                if (havePants == "n" && doorClose == "y") {
                    _return = false;
                    break;
                }
                if (havePants == "y" && doorClose == "n") {
                    setStatusCode("003");
                    isStanding = "n";
                    _return = true;
                    break;
                }
                if (havePants == "n" && doorClose == "n") {
                    setStatusCode("013");
                    isStanding = "n";
                    _return = true;
                    break;
                }
                if (havePants == "y" && doorClose == "y") {
                    _return = false;
                    break;
                }

            case "104":// {"shit", "Your forgot to take your pants off!! You just shit your pants! Game Over!","104"},
                if (havePants == "y" && doorClose == "n" && isStanding == "n") {
                    setStatusCode("104");
                    endGame = true;
                    _return = true;

                    break;
                }
                if (havePants == "y" && doorClose == "n") {
                    setStatusCode("003");
                    isStanding = "n";
                    _return = true;
                    break;
                }
                if (havePants == "n" && doorClose == "n" && isStanding == "n") {
                    setStatusCode("007");//win
                    reviewChangesStatus("007");
                    endGame = true;
                    _return = true;
                    break;
                }
                if (havePants == "y" && doorClose == "y") {
                    _return = false;
                    break;
                }


            case "004":
                if (havePants == "y")
                    setStatusCode("004");
                endGame = true;
                    _return = true;
                break;
            case "007":
                statusCode = "007";
                endGame = true;
                _return = true;
                break;
            case "012":
                if (havePants == "y" && doorClose == "y") {
                    statusCode = "012";
                    havePants = "n";

                    _return = true;
                    break;
                }
            case "112"://{"pull door","Oh, right..","112"},
                if (havePants == "n" && doorClose == "y") {
                    doorClose = "n";
                    statusCode = "112";
                    endGame = true;
                    _return = true;
                    break;
                }
                if (havePants == "y" && doorClose == "n") {
                    doorClose = "n";
                    statusCode = "102";

                    _return = true;
                    break;
                }
                if (havePants == "n" && doorClose == "n") {
                    break;
                }
                if (havePants == "y" && doorClose == "y") {
                    doorClose = "n";
                    statusCode = "102";

                    _return = true;
                    break;
                }

            case "1120"://{"take off pants","You remove your pants","1120"}
                if (havePants == "y" && doorClose == "y") {
                    havePants = "n";
                    statusCode = "012";
                    _return = true;
                    break;
                }
                if (havePants == "n" && doorClose == "y") {
                    havePants = "n";
                    statusCode = "012";
                    _return = true;
                    break;
                }
                if (havePants == "y" && doorClose == "n") {
                    havePants = "n";
                    statusCode = "112";
                    _return = true;
                    break;
                }
                if (havePants == "n" && doorClose == "n") {
                    havePants = "n";
                    statusCode = "112";
                    _return = true;
                    break;
                }


/*
            {"look your pants", "","00150"},
            {"look at door","it's a door","00130"},
            {"look behind door", "it's a door","00120"},
*/

            case "00150":
                    setStatusCode("00150");
                    _return = true;
                    break;

            case "00130":
                    setStatusCode("00130");
                    _return = true;
                    break;

            case "00120":
                    setStatusCode("00120");
                    _return = true;
                    break;

                    /*

                     {"look", "You're on a room with a door. You're wearing a shirt and pants. You have no hair.","00190"},
            {"look", "You're on a room with a door. You're wearing a shirt and not pants. You have no hair.","00180"},
            {"look ground", "You're on a room with a door. You're wearing a shirt and pants. You have no hair.","00170"},
            {"look ground", "You're on a room with a door. You're wearing a shirt and not pants. You have no hair.","00160"},

                     */
            case "00180":
            if (havePants == "n" ) {
                setStatusCode("012");
                _return = true;
                break;
            }
            if (havePants == "y") {
                setStatusCode("00190");
                _return = true;
                break;
            }
        case "00190":
            if (havePants == "n" ) {
                setStatusCode("012");
                _return = true;
                break;
            }
            if (havePants == "y") {
                setStatusCode("00190");
                _return = true;
                break;
            }


            case "00170":
            if (havePants == "n") {
                setStatusCode("00160");
                _return = true;
                break;
            }
            if (havePants == "y") {
                setStatusCode("00170");
                _return = true;
                break;
            }
            case "00160":
            if (havePants == "n") {
                setStatusCode("00160");
                _return = true;
                break;
            }
            if (havePants == "y") {
                setStatusCode("00170");
                _return = true;
                break;
            }

            case "1001":
            if (havePants == "y") {
                setStatusCode("1001");
                _return =true;
                break;
            }
            if (havePants == "n") {
                setStatusCode("006");
                endGame = true;
                _return =true;
                break;
            }

            case "0002":
            if (havePants == "y") {
                setStatusCode("004");
                _return= true;
                break;
            }
            if (havePants == "n") {
                setStatusCode("006");
                _return =true;
                text.setText("Timer: "  + 00 );
                break;
            }
        case "0003":
            if (havePants == "y") {
                setStatusCode("0003");
                _return =true;
                break;
            }
            if (havePants == "n") {
                setStatusCode("006");
                _return =true;
                break;
            }

            case "999":
            if (havePants == "y") {
                setStatusCode("999");
                _return =true;
                endGame = true;
                break;
            }
            if (havePants == "n") {
                setStatusCode("0061");
                _return =true;
                break;
            }



        }
        return _return;
    }

    private void resetTimer() {
       if( notFart == "y") {
           remainTime += 121 * 1000;
           isPaused = false;
           countDownTimer.cancel();
           countDownTimer = new MyCountDownTimer(remainTime, interval);
           countDownTimer.start();
           timerHasStarted = true;
       }
    }

    private void sendImageRequest(String text) {
        text = text.substring(0,3).toString() ;
        switch (text) {

            case "001":
                ImageView img001 = (ImageView)findViewById(R.id.imageView1);
                img001.setImageResource(R.drawable.img001);
                break;
            case "003":
                ImageView img003 = (ImageView)findViewById(R.id.imageView1);
                img003.setImageResource(R.drawable.img003);
                break;
            case "004":
                ImageView img004 = (ImageView)findViewById(R.id.imageView1);
                img004.setImageResource(R.drawable.img004);

                break;
            case "006":
                ImageView img006 = (ImageView)findViewById(R.id.imageView1);
                img006.setImageResource(R.drawable.img006);
                break;
            case "007":
                ImageView img007 = (ImageView)findViewById(R.id.imageView1);
                img007.setImageResource(R.drawable.img007);
                break;
            case "012":
                ImageView img012 = (ImageView)findViewById(R.id.imageView1);
                img012.setImageResource(R.drawable.img012);
                break;
            case "013":
                ImageView img013 = (ImageView)findViewById(R.id.imageView1);
                img013.setImageResource(R.drawable.img013);
                break;
            case "102":
                ImageView img102 = (ImageView)findViewById(R.id.imageView1);
                img102.setImageResource(R.drawable.img102);
                break;
            case "104":
                ImageView img104 = (ImageView)findViewById(R.id.imageView1);
                img104.setImageResource(R.drawable.img104);
                break;
            case "112":
                ImageView img112 = (ImageView)findViewById(R.id.imageView1);
                img112.setImageResource(R.drawable.img112);
                break;
            case "999":
                ImageView img999 = (ImageView)findViewById(R.id.imageView1);
                img999.setImageResource(R.drawable.img999);
                break;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {
        BasicViewsActivity.super.onBackPressed();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_games:
                getPlayStore();
                return true;
            case R.id.menu_restart:
                restartGame();
                return true;
            case R.id.menu_pause:
                pauseGame();
                return true;
            /*case R.id.menu_ad:
                getVideoAd();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getVideoAd() {
    }

    private void pauseGame() {
        isPaused=true;

        new AlertDialog.Builder(this)
                .setTitle("Pause")
                .setMessage("Continue?")
                //.setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0, int arg1) {
                                isPaused=false;
                                countDownTimer = new MyCountDownTimer(remainTime, interval);
                                countDownTimer.start();
                                timerHasStarted = true;
                            }
                        }).create().show();

    }

    private void restartGame() {
        onBackPressed();
    }

    private void getPlayStore() {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:\"Demilion" )));

    }

}

