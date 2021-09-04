package de.test.soundexample;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundpool;
    private int soundExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new  AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundpool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundpool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
        soundExample = soundpool.load(this,R.raw.du_hast_eine_nachricht,1);

    }

    public void playSound(View view){
        soundpool.play(soundExample,1,1,0,0,1);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        soundpool.release();
        soundpool = null;
    }
}