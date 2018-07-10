package com.example.jgalvan.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

//import static com.example.jgalvan.myapplication.R.id.adView2;
import java.io.File;

import static com.google.android.gms.ads.MobileAds.*;


public class MainMenu extends Activity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_views);

       /* initialize(this,
                "ca-app-pub-3940256099942544~3347511713");

        mAdView = findViewById(adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
*/
        //audioPlayer(path, fileName)


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       startGame(view);
                                   }
                               }

        );

    }

    public String getText() {
        String text =  ((TextView) findViewById(R.id.editText1)).getText().toString();
        if (text != null) {

            if (text.startsWith("delete")) {
                deleteFile();
                return "Delete file.";
            }
            if (text.startsWith("awards"))
            {
                sendAwards();
                return "0 - Awards";
            }
        } else {
            text = "I can't understand " + text;
        }
        return text;
    }

    private void deleteFile() {

    }

    private void sendAwards() {

    }

    protected void startGame(View view) {

        String text =  ((TextView) findViewById(R.id.editText1)).getText().toString();
        if (text != null) {
            if (!text.startsWith("play")) {

                if (text.startsWith("delete")||text.startsWith("Delete")) {

                    Snackbar.make(view, getText(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    Intent intent = new Intent(MainMenu.this, Delete.class);
                    startActivity(intent);

                }

                if (text.startsWith("die")||text.startsWith("Die")) {
                    Intent intent = new Intent(this, Category.class);
                    startActivity(intent);
                }



                if (text.startsWith("shit")||text.startsWith("Shit")) {
                    ((TextView) findViewById(R.id.editText1)).setText("whats wrong with you!!");
                    ImageView img004 = (ImageView)findViewById(R.id.imageView1);
                    img004.setImageResource(R.drawable.img004);

                }




                if (text.startsWith("menu")||text.startsWith("Menu")) {
                    ((TextView) findViewById(R.id.editText1)).setText("play");
                    ImageView img004 = (ImageView)findViewById(R.id.imageView1);
                    img004.setImageResource(R.drawable.intro);

                }

                if (text.startsWith("award")||text.startsWith("Award")) {
                    Intent intent = new Intent(this, Award.class);
                    startActivity(intent);
                }
            }
            else {
                if (text.startsWith("play")||text.startsWith("Play")||text.startsWith("PLAY")) {
                    Intent intent = new Intent(this, BasicViewsActivity.class);
                    startActivity(intent);

                }
            }
    }

}

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes,

                 new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainMenu.super.onBackPressed();
                    }
        }).create().show();
    }

}
