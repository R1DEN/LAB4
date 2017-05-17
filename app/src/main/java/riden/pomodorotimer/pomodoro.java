package riden.pomodorotimer;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


public class pomodoro extends AppCompatActivity {

    private Button TimerBtn;
    private EditText WorkTime, RestTime;
    private TextView TimeLeft;
    private ProgressBar Bar;
    static CountDownTimer PomTimer;
    static boolean isWork = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);

        TimerBtn = (Button) findViewById(R.id.TimerButton);
        WorkTime = (EditText) findViewById(R.id.WorkTime);
        RestTime = (EditText) findViewById(R.id.RestTime);
        TimeLeft = (TextView) findViewById(R.id.TimeLeft);
        Bar = (ProgressBar) findViewById(R.id.progressBar);

        TimerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int time;
                if (isWork) {
                    time = (Integer.parseInt(WorkTime.getText().toString())) * 1000 + 100;
                } else {
                    time = (Integer.parseInt(RestTime.getText().toString())) * 1000 + 100;
                }
                if (PomTimer == null) {
                    Bar.setMax((time/1000)+1);
                    Bar.setProgress((time/1000)+1);
                    PomTimer = new CountDownTimer(time, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                            TimeLeft.setText(String.format("%02d:%02d",(millisUntilFinished / 60000) % 60,(millisUntilFinished / 1000) % 60));
                            Bar.incrementProgressBy(-1);
                        }

                        @Override
                        public void onFinish() {
                            final String out;
                            out = isWork?"Finish Work!":"Finish Rest!";
                            Bar.setProgress((time/1000)+1);
                            isWork = !isWork;
                            TimeLeft.setText(out);
                            TimerBtn.setText("Start Timer");
                            PomTimer = null;
                            Bar.setProgress(0);
                            try {
                                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                                r.play();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    TimerBtn.setText("Stop Timer");
                } else {
                    PomTimer.cancel();
                    Bar.setProgress(0);
                    isWork = true;
                    TimeLeft.setText(null);
                    TimerBtn.setText("Start Timer");
                    PomTimer = null;
                }
            }
        });
    }

    public void updateBar(String time){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
