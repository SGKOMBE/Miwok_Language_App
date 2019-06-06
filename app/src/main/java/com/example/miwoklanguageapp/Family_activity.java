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

public class Family_activity extends AppCompatActivity {

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

        final ArrayList<Word> family = new ArrayList<Word>();

        //Data.
        family.add(new Word("әpә", "father", R.drawable.family_father, R.raw.family_father));
        family.add(new Word("әṭa", "mother", R.drawable.family_mother, R.raw.family_mother));
        family.add(new Word("angsi", "son", R.drawable.family_son, R.raw.family_son));
        family.add(new Word("tune", "daughter", R.drawable.family_daughter, R.raw.family_daughter));
        family.add(new Word("taachi", "older brother", R.drawable.family_older_brother, R.raw.family_older_brother));
        family.add(new Word("chalitti", "younger brother", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        family.add(new Word("teṭe", "older sister", R.drawable.family_older_sister, R.raw.family_older_sister));
        family.add(new Word("kolliti", "younger sister", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        family.add(new Word("ama", "grandmother", R.drawable.family_grandmother, R.raw.family_grandmother));
        family.add(new Word("paapa", "grandfather", R.drawable.family_grandfather, R.raw.family_grandfather));

        //Adapter
        WordAdapter adapter1 = new WordAdapter(this, family, R.color.category_family);

        //Object of ListView.
        ListView list1 = (ListView) findViewById(R.id.list);

        //Setting the Adapter
        list1.setAdapter(adapter1);

        list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = family.get(position);

                releaseMediaPlayer();

                int focus = mAudioManager.requestAudioFocus(mChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(Family_activity.this, word.getmMusic());
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
