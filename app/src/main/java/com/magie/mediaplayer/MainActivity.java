package com.magie.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ViewHolder mViewHolder = new ViewHolder();
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(this, R.raw.teste);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        mViewHolder.buttonPlay = findViewById(R.id.buttonPlay);
        mViewHolder.buttonStop = findViewById(R.id.buttonStop);
        mViewHolder.buttonPause = findViewById(R.id.buttonPause);

        mViewHolder.seekBarVolume = findViewById(R.id.seekBarVolume);

        mViewHolder.seekBarVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
        mViewHolder.seekBarVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        mViewHolder.seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, AudioManager.FLAG_SHOW_UI);
                //If flag is set in 0 there is no change
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mViewHolder.buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer!=null){
                    mediaPlayer.start();
                }
            }
        });
        mViewHolder.buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
            }
        });
        mViewHolder.buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.teste);
                }else{
                    Toast.makeText(MainActivity.this, "A música necessita estar tocando", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!= null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            //libera todos os recursos de memória
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

    public static class ViewHolder{
        Button buttonPlay, buttonPause, buttonStop;
        SeekBar seekBarVolume;
    }
}