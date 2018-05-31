package com.example.francisco.proj_kcs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ViewPager viewPager;
    private CustomScroller customScroller;
    private AlphaAnimation buttonClick = new AlphaAnimation(2F, 0.8F);
    public static final String EXTRA_MESSAGE = "MESSAGE";
    private boolean login = false;
    private static String userLogin;
    DatabaseHelper helper;
    EditText user, passwd;
    CircularProgressButton circularProgressButton;
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        user = (EditText) findViewById(R.id.userEditText);
        passwd = (EditText) findViewById(R.id.passwdEditText);
        //getApplicationContext().deleteDatabase("kids.db");

        circularProgressButton = (CircularProgressButton) findViewById(R.id.progressBar);


        helper = new DatabaseHelper(this);
        user.setText("xico");
        passwd.setText("1234");
        Field mScroller = null;

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        setCustomScrollerToViewPager();
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        btn = (Button) this.findViewById(R.id.buttonLogin);
        btn.setOnClickListener(this);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                viewPager.setCurrentItem(viewPager.getCurrentItem());
                return false;
            }
        });


        passwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;
                if (i == EditorInfo.IME_ACTION_SEND) {
                    goToNextView(textView.getRootView());
                    handled = true;
                }
                return handled;
            }
        });
    }

    @Override
    public void onClick(final View view) {
        view.startAnimation(buttonClick);
        AsyncTask<String, String, String> progress = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "done";
            }

            @Override
            protected void onPostExecute(String s) {
                if (s.equals("done")) {
                    goToNextView(view);
                    if (!login) {
                        this.cancel(true);
                        circularProgressButton.setVisibility(View.INVISIBLE);
                        btn.setEnabled(true);
                        circularProgressButton.clearAnimation();
                    }
                }
            }
        };
        circularProgressButton.setVisibility(View.VISIBLE);
        circularProgressButton.startAnimation();
        btn.setEnabled(false);
        progress.execute();
    }


    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            LoginActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else if (viewPager.getCurrentItem() == 2) {
                        viewPager.setCurrentItem(3);
                    } else if (viewPager.getCurrentItem() == 3) {
                        viewPager.setCurrentItem(4);
                    } else if (viewPager.getCurrentItem() == 4) {
                        viewPager.setCurrentItem(5);
                    } else if (viewPager.getCurrentItem() == 5) {
                        viewPager.setCurrentItem(6);
                    } else if (viewPager.getCurrentItem() == 6) {
                        viewPager.setCurrentItem(7);
                    } else {
                        viewPager.setCurrentItem(0);
                    }

                }
            });

        }

    }

    private void setCustomScrollerToViewPager() {
        setCustomScrollerToViewPager(new LinearInterpolator(), 500);
    }

    private void setCustomScrollerToViewPager(Interpolator interpolator, int duration) {
        try {
            customScroller = new CustomScroller(this, interpolator, duration);
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(viewPager, customScroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void goToNextView(View view) {


        if (helper.verifiyLogin(user.getText().toString(), passwd.getText().toString())) {

            final Intent intent = new Intent(this, MainActivity.class);
            login = true;
            Toast.makeText(getApplicationContext(), R.string.successfulLogin, Toast.LENGTH_SHORT).show();
            userLogin = user.getText().toString();

            //intent.putExtra(EXTRA_MESSAGE, userLogin);
            finish();
            startActivity(intent);
            overridePendingTransition(R.anim.slide_esquerda, R.anim.slide_esquerdaout);

        } else {
            Toast.makeText(getApplicationContext(), R.string.wrongLogin, Toast.LENGTH_SHORT).show();
            login = false;

        }

    }


    public void goToRegisterPage(View view ){
        Intent intent = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    public static String getUserLogin(){return userLogin;}



}
