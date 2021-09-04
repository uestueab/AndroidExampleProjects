package de.test.shakeview;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioAttributes;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        focusOnInputArea(editText);

        submitBtn = findViewById(R.id.submitBtn);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText.getText().toString().equals("test")){
                    view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    vibrateOnError();
                    editText.startAnimation(shakeError());

                }
            }
        });
    }

    public TranslateAnimation shakeError() {
        TranslateAnimation shake = new TranslateAnimation(0, 20, 0, 0);
        shake.setDuration(500);
        shake.setInterpolator(new CycleInterpolator(4));
        return shake;
    }

    public void vibrateOnError(){
        Vibrator v = (Vibrator) getApplicationContext().getSystemService(VIBRATOR_SERVICE);
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
        VibrationEffect ve = VibrationEffect.createOneShot(100,
                VibrationEffect.DEFAULT_AMPLITUDE);
        v.vibrate(ve, audioAttributes);
    }

    public void focusOnInputArea(EditText et){
        et.setFocusableInTouchMode(true);
        et.requestFocus();

        if(et.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}