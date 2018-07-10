package com.example.jgalvan.myapplication;


import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import static com.google.android.gms.ads.MobileAds.initialize;

public class Intro  extends Activity {
    private String urlPages =""; //"file:///android_asset/tinder.html";//"file:///android_asset/dontpoopyourpants.html";
    Random r = new Random();
    int random = r.nextInt(12 - 1) + 1;
    boolean isMute = false;

    private boolean soundsStatus = false;
    private InterstitialAd mInterstitialAd;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    private AdView mAdView;
    String adUnitId_Test  = "ca-app-pub-3940256099942544~3347511713";

    String adUnitID = adUnitId_Test;// _adUnitId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_tools);

        initialize(this, adUnitID);

        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        final WebView myWebView = (WebView) this.findViewById(R.id.webView);
        myWebView.addJavascriptInterface(new Intro.WebAppInterface(this), "Android");
        myWebView.addJavascriptInterface(new AudioInterface(this), "AndAud");


        // Enable JavaScript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMediaPlaybackRequiresUserGesture(soundsStatus);

        myWebView.setWebViewClient(new MyWebViewClient());
        myWebView.setWebChromeClient(new MyWebChromeClient());






        switch (random) {
            case 1:
                urlPages = "file:///android_asset/dontpoopyourpants.html";
                break;
            case 2:
                urlPages = "file:///android_asset/tinder.html";
                break;
            case 3:
                urlPages ="file:///android_asset/dontpoopyourpants.html";
                break;
            case 4:
                urlPages ="file:///android_asset/dontpoopyourpants.html";
                break;
            case 5:
                urlPages ="file:///android_asset/dontpoopyourpants.html";
                break;
            case 6:
                urlPages ="file:///android_asset/gif.html";
                break;
            case 7:
                urlPages = "file:///android_asset/dontpoopyourpants.html";
                break;
            case 8:
                urlPages = "file:///android_asset/tinder.html";
                break;
            case 9:
                urlPages ="file:///android_asset/storytime.html";
                break;
            case 10:
                urlPages ="file:///android_asset/dontpoopyourpants.html";
                break;
            case 11:
                urlPages ="file:///android_asset/gif.html";
                break;
            case 12:
                urlPages ="file:///android_asset/gif.html";
                break;
             default:
                    urlPages ="file:///android_asset/dontpoopyourpants.html";

        }
        myWebView.loadUrl(urlPages);


        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);


        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                builder.setMessage("Rate Us!!!")
                        .setCancelable(false)
                        .setPositiveButton("OK GO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.demilion.dontpoopyourpants") ) );
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.create()
                        .show();

            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(v.getContext());
                builder.setMessage("Check more apps!!!")
                        .setCancelable(false)
                        .setPositiveButton("OK GO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Demilion") ) );
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                builder.create()
                        .show();

            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked


                String js;

                if (isMute != true) {
                    js = "muteSong();";
                    myWebView.evaluateJavascript("javascript: " + js, null);
                    //myWebView.reload();
                    isMute = true;
                }else {
                    js = "playSong();";
                    myWebView.evaluateJavascript("javascript: " + js, null);
                    //myWebView.reload();
                    isMute = false;
                }

            }
        });





/*
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.

            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.

            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the interstitial ad is closed.

            }
        });

*/

    }


    public void audioPlayer(){
        //set up MediaPlayer
        /*try {
            MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.botwsad);
            mediaPlayer.start(); // no need to call prepare(); create() does that for you
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
        String url = "https://d3cj65qhk7sagp.cloudfront.net/tracks/Unity/Unity+-+Loop.mp3"; // your URL here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(url);
        mediaPlayer.prepare(); // might take long! (for buffering, etc)
        mediaPlayer.start();
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public class WebAppInterface {

        Context context;

        /**
         * Instantiate the interface and set the context
         *
         * @param context
         */
        public WebAppInterface(Context context) {
            this.context = context;
        }

        /**
         * Show a dialog from the web page.
         *
         * @param message message of the dialog
         */
        @JavascriptInterface
        public void showDialog(String message) {

            // Use the Builder class for convenient dialog construction
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this.context);
            builder.setMessage(message).setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            // Create the AlertDialog object and return it
            builder.create().show();
        }

        /*public void storeText(String text) {

        }*/
        @JavascriptInterface
        public void storeText(String text)
        {
            //TODO  save text
            if (text.equals("ok")) {

                Intent intent = new Intent(Intro.this, MainMenu.class);
                intent.setAction(Intent.ACTION_SEND);
                startActivity(intent);
                //Toast.makeText(this.context, "Type play...." + random, Toast.LENGTH_LONG).show();
            }
        }

    }

    public class AudioInterface {
        Context mContext;

        AudioInterface(Context c) {
            mContext = c;
        }

        //Play an audio file from the webpage
        @JavascriptInterface
        public void playAudio(String aud) { //String aud - file name passed
            //from the JavaScript function
            final MediaPlayer mp;

            try {
                AssetFileDescriptor fileDescriptor =
                        mContext.getAssets().openFd(aud);
                mp = new MediaPlayer();
                mp.setDataSource(fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(),
                        fileDescriptor.getLength());
                fileDescriptor.close();
                mp.prepare();
                mp.start();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished (WebView view, String url) {
            //Calling a javascript function in html page
            view.loadUrl("javascript:alert(showVersion('called by Android'))");
        }
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            Log.d("LogTag", message);
            result.confirm();
            return true;
        }
    }


}