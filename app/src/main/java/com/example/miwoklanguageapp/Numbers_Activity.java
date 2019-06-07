/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.miwoklanguageapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.ColorRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class Numbers_Activity extends AppCompatActivity {

    //Audio Manager declaration for managing audio of our app.
    public AudioManager mAudioManager;
    //This will hold the media player object for global use.
    private MediaPlayer mMediaPlayer;
    AudioManager.OnAudioFocusChangeListener mChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN)
                mMediaPlayer.start();
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                releaseMediaPlayer();
            }

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
        try{
        getActionBar().setDisplayHomeAsUpEnabled(true);
        }catch (NullPointerException n){
            System.out.println(n);
        }

        mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        //ArrayList of objects of class Word.
        final ArrayList<Word> number = new ArrayList<Word>();

        //Data addition to Array List.
        number.add(new Word("lutti", "one", R.drawable.number_one, R.raw.number_one));
        number.add(new Word("otiiko", "two", R.drawable.number_two, R.raw.number_two));
        number.add(new Word("tolookosu", "three", R.drawable.number_three, R.raw.number_three));
        number.add(new Word("oyyisa", "four", R.drawable.number_four, R.raw.number_four));
        number.add(new Word("massokka", "five", R.drawable.number_five, R.raw.number_five));
        number.add(new Word("temmokka", "six", R.drawable.number_six, R.raw.number_six));
        number.add(new Word("kenekaku", "seven", R.drawable.number_seven, R.raw.number_seven));
        number.add(new Word("kawinta", "eight", R.drawable.number_eight, R.raw.number_eight));
        number.add(new Word("wo’e", "nine", R.drawable.number_nine, R.raw.number_nine));
        number.add(new Word("na’aacha", "ten", R.drawable.number_ten, R.raw.number_ten));

        //Setting the array adapter to display word in Numbers Activity.
        WordAdapter adapter = new WordAdapter(this, number, R.color.category_numbers);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = number.get(position);

                releaseMediaPlayer();

                //This will request for audio focus for our media player instance
                //and if present the audio focus will be allocated to our media player instance
                //and then only the media player will be started to play our audio.
                int focus = mAudioManager.requestAudioFocus(mChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (focus == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mMediaPlayer = MediaPlayer.create(Numbers_Activity.this, word.getmMusic());
                    mMediaPlayer.start();
                }

                //This will set listener for the Completion of the audio playback by passing the global onComplete variable.
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

            //This will release the audio focus from our media player instance.
            mAudioManager.abandonAudioFocus(mChangeListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
