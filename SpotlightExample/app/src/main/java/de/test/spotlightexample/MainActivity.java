package de.test.spotlightexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.takusemba.spotlight.CustomTarget;
import com.takusemba.spotlight.OnSpotlightEndedListener;
import com.takusemba.spotlight.OnSpotlightStartedListener;
import com.takusemba.spotlight.OnTargetStateChangedListener;
import com.takusemba.spotlight.SimpleTarget;
import com.takusemba.spotlight.Spotlight;
import com.takusemba.spotlight.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView tv_first;
    private TextView tv_second;

    private ArrayList<View> spotlightViews = new ArrayList<View>();
    private SimpleTarget[] targets;
    private Map<String, List<String>> targetInfo = new HashMap<String, List<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_first = findViewById(R.id.first);
        tv_second = findViewById(R.id.second);

        createSpotlightView(tv_first,
                "Welcome",
                "Hope you'll have fun with the app!");
        createSpotlightView(tv_second,
                "Perfect",
                "See how easy it is?\n" +
                        "Thats it! Everything is set up for your journey.");

        final ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.mainlayout);
        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout.getViewTreeObserver()
                            .removeOnGlobalLayoutListener(this);
                } else {
                    layout.getViewTreeObserver()
                            .removeGlobalOnLayoutListener(this);
                }

                buildTargets(spotlightViews);
                startSpotlight(targets);
            }
        });



    }

    private void createSpotlightView(View view,String title, String desc){
        if(targetInfo.get("title") == null)
            targetInfo.put("title", new ArrayList<String>());

        if(targetInfo.get("desc") == null)
            targetInfo.put("desc", new ArrayList<String>());

        spotlightViews.add(view);
        targetInfo.get("title").add(title);
        targetInfo.get("desc").add(desc);
    }


    private void buildTargets(ArrayList<View> spotlightViews){
        // target array, based on view count
        targets = new SimpleTarget[spotlightViews.size()];

        // for every view you want to spotlight
        for (int i=0; i<spotlightViews.size(); i++){
            Rect rectf = new Rect();

            View currentView = spotlightViews.get(i);
            currentView.getLocalVisibleRect(rectf);
            currentView.getGlobalVisibleRect(rectf);

            //get their location coordinates from screen.
            //the spotlight radius depends on the view width
            float currentView_x = rectf.exactCenterX();
            float currentView_y = rectf.exactCenterY();
            float currentView_radius = (float) (rectf.width() / 2 ) + 10;

            // build targets one by one
            // title and desc information will be taken from 'targetsMap' when calling createSpotlightView(...)
            targets[i] = new SimpleTarget.Builder(MainActivity.this)
                    .setPoint(currentView_x,currentView_y) // position of the Target. setPoint(Point point), setPoint(View view) will work too.
                    .setRadius(currentView_radius)// radius of the Target
                    .setTitle(targetInfo.get("title").get(i)) // title
                    .setDescription(targetInfo.get("desc").get(i)) // description
                    .setOnSpotlightStartedListener(new OnTargetStateChangedListener<SimpleTarget>() {
                        @Override
                        public void onStarted(SimpleTarget target) {
                            // do something
                        }
                        @Override
                        public void onEnded(SimpleTarget target) {
                            // do something
                        }
                    })
                    .build();

        }
    }

    // this will finally start the spotlight
    // when all targets are created..
    private void startSpotlight(Target[] targets){
        Spotlight spotlight = Spotlight.with(MainActivity.this)
                .setOverlayColor(ContextCompat.getColor(MainActivity.this, R.color.background)) // background overlay color
                .setDuration(500L) // duration of Spotlight emerging and disappearing in ms. (default, 1000ml)
                .setAnimation(new DecelerateInterpolator(2f)) // animation of Spotlight
                .setTargets(targets) // set targets. see below for more info
                .setClosedOnTouchedOutside(true) // set if target is closed when touched outside
                .setOnSpotlightStartedListener(new OnSpotlightStartedListener() { // callback when Spotlight starts
                    @Override
                    public void onStarted() {
                        Toast.makeText(MainActivity.this, "Let's explain a few things first...", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnSpotlightEndedListener(new OnSpotlightEndedListener() { // callback when Spotlight ends
                    @Override
                    public void onEnded() {
                        Toast.makeText(MainActivity.this, "Walkthrough has ended. You can now start the app!", Toast.LENGTH_SHORT).show();
                    }
                });

        spotlight.start();
    }
}