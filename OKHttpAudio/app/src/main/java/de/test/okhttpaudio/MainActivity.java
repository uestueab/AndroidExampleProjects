package de.test.okhttpaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    byte[] audioBytes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        textView = (TextView) findViewById(R.id.textview);

        try {
            getAudioBytes("https://ssl.gstatic.com/dictionary/static/sounds/20200429/company--_gb_1.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(audioBytes != null)
            playMp3(audioBytes);
        else
            textView.setText("Request failed :(");
    }

    public void getAudioBytes(String audio_url) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(audio_url).build();
        Response response = client.newCall(request).execute();
        audioBytes = Objects.requireNonNull(response.body()).bytes();
    }

    private void playMp3(byte[] audioBytes) {
        try {
            String audioPath = getCacheDir()+"/tmpAudio.mp3";
            File file = new File(audioPath);
            file.deleteOnExit();

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(audioBytes);
            fos.close();

            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();

            FileInputStream fis = new FileInputStream(file);
            mediaPlayer.setDataSource(fis.getFD());

            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}