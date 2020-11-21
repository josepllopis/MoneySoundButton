package apps.ejemplo.moneysoundbutton;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import hotchemi.android.rate.AppRate;

public class MainActivity extends AppCompatActivity {
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //ANUNCIO INTERSTITIAL

        interstitial = new InterstitialAd(MainActivity.this);

        interstitial.setAdUnitId(("ca-app-pub-8540988746160351/8824998461"));

        interstitial.loadAd(adRequest);

        interstitial.setAdListener(new AdListener(){
            public void onAdLoaded(){
                displayInterstitial();
            }
        });
        //ANUNCIO BANNER
        MobileAds.initialize(this, "ca-app-pub-3940256099942544-3347511713");
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest1);

        mAdView.setAdListener(new AdListener(){

            @Override
            public void onAdLoaded(){ Log.d("Ad Test", "Add Finishes Loading");}

            @Override
            public void onAdFailedToLoad(int i) { Log.d("Ad Test", "Add Loading Failed");}

            @Override
            public void onAdLeftApplication(){ Log.d("Ad Test","User left the app");}

            @Override
            public void onAdClosed(){
                Log.d("Ad Test","User return back to the app after tapping on ad");
            }
        });


        //AUDIO
        final MediaPlayer machineSoundMP = MediaPlayer.create(this, R.raw.sonido);

        Button play_sound = (Button) this.findViewById(R.id.custom_button);

        play_sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                machineSoundMP.start();
            }
        });

    }
    public void displayInterstitial(){
        if(interstitial.isLoaded()){
            interstitial.show();
        }
    }

    //VALORAR
    public void rateMe(View v){
        try{
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=" +getPackageName())));
        }catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=" +getPackageName())));
        }
    }

    //MAS APPS
    public void moreApps(View v){
        try{
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/developer?id=ElGranLloso&gl=ES")));
        }catch(ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/developer?id=ElGranLloso&gl=ES")));
        }

    }

    //COMPARTIR
    public void compartir(View v){
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=apps.ejemplo.moneysoundbutton";
        String shareSub = "Nuclear Alarm Button";
        myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(myIntent,"Compartir"));
    }

    //PRIVACIDAD
    public void privacidad(View v){
        Intent myLink = new Intent(Intent.ACTION_VIEW);
        myLink.setData(Uri.parse("https://docs.google.com/document/d/1zZyxV-eKl8NSxukdW1spnv7y4QflppV5b5NOnkqbD3g/edit?usp=sharing"));
        startActivity(myLink);
    }
}


