package com.sid.soundrecorderutils;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sid.util.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {

    private Button mBtnRecordAudio;
    private Button mBtnPlayAudio;
  private Button requestBtn;
  private TextView showData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mBtnRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final RecordAudioDialogFragment fragment = RecordAudioDialogFragment.newInstance();
                fragment.show(getSupportFragmentManager(), RecordAudioDialogFragment.class.getSimpleName());
                fragment.setOnCancelListener(new RecordAudioDialogFragment.OnAudioCancelListener() {
                    @Override
                    public void onCancel() {
                        fragment.dismiss();
                    }
                });
            }
        });

        mBtnPlayAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecordingItem recordingItem = new RecordingItem();
                SharedPreferences sharePreferences = getSharedPreferences("sp_name_audio", MODE_PRIVATE);
                final String filePath = sharePreferences.getString("audio_path", "");
                long elpased = sharePreferences.getLong("elpased", 0);
                recordingItem.setFilePath(filePath);
                recordingItem.setLength((int) elpased);
                PlaybackDialogFragment fragmentPlay = PlaybackDialogFragment.newInstance(recordingItem);
                fragmentPlay.show(getSupportFragmentManager(), PlaybackDialogFragment.class.getSimpleName());
            }
        });
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitUtil.getInstance().testGet(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //  Toast.makeText(MainActivity.this,"subsrc------------"+d.toString(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(String s) {
                        //  Toast.makeText(MainActivity.this,"next------------"+s,Toast.LENGTH_SHORT).show();
                        showData.setText(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        //  Toast.makeText(MainActivity.this,"error------------"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        //    Toast.makeText(MainActivity.this,"completteeee------------",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void initView() {
        mBtnRecordAudio = (Button)findViewById(R.id.main_btn_record_sound);
        mBtnPlayAudio = (Button) findViewById(R.id.main_btn_play_sound);
         requestBtn = (Button)findViewById(R.id.btn_request);
        showData = (TextView) findViewById(R.id.tv_show);
    }
}
