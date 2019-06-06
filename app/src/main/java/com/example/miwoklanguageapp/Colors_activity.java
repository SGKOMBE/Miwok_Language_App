package com.example.miwoklanguageapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class Colors_activity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener mChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                mMediaPlayer.start();
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
                releaseMediaPlayer();
        }
    };
    private MediaPlayer.OnCompletionListener onComplete = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException n){
            System.out.println(n);
        }

        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> color = new ArrayList<Word>();

        //Data
        color.add(new Word("weṭeṭṭi", "red", R.drawable.color_red, R.raw.color_red));
        color.add(new Word("chokokki", "green", R.drawable.color_green, R.raw.color_green));
        color.add(new Word("ṭakaakki", "brown", R.drawable.color_brown, R.raw.color_brown));
        color.add(new Word("ṭopoppi", "gray", R.drawable.color_gray, R.raw.color_gray));
        color.add(new Word("kululli", "black", R.drawable.color_black, R.raw.color_black));
        color.add(new Word("kelelli", "white", R.drawable.color_white, R.raw.color_white));
        color.add(new Word("ṭopiisә", "dusty yellow", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        color.add(new Word("chiwiiṭә", "mustard yellow", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        //Adapter
        WordAdapter adapter2 = new WordAdapter(this, color, R.color.category_colors);

        //Object of ListView
        ListView list2 = (ListView) findViewById(R.id.list);

        //Setting the Adapter
        list2.setAdapter(adapter2);

        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = color.get(position);

                releaseMediaPlayer();

                int focus = mAudioManager.requestAudioFocus(mChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(Colors_activity.this, word.getmMusic());
                    mMediaPlayer.start();
                }

                //Memory de-Allocation.
                mMediaPlayer.setOnCompletionListener(onComplete);
            }
        });
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
