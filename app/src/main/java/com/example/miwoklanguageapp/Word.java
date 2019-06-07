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

/*
   *This class is used to create a word,
   *which will consist of both default and its Miwok translation along with an image in some cases.
 */
public class Word {
    //Stores value of Miwok word.
    private String mMiwok;
    //Store value of word in default language.
    private String mDefault;
    //Constant value to check image is utilised or not.
    private static final int mResource = -1;
    //Stores the drawable resource id of image.
    private int mImageId = mResource;
    //Stores the music file from raw folder.
    private int mMusicId;

    //Constructor to initialise the Words without image.
    public Word(String Miwok, String Default, int musicId){
        mMiwok = Miwok;
        mDefault = Default;
        mMusicId = musicId;
    }

    //Constructor to initialise the words with image.
    public Word(String miwok, String Default, int imageId, int musicId) {
        mMiwok = miwok;
        mDefault = Default;
        mImageId = imageId;
        mMusicId = musicId;
    }

    //Getter methods for all the words and image.
    public String getMiwok(){
        return mMiwok;
    }

    public String getDefault(){
        return mDefault;
    }

    public int getImage(){
        return mImageId;
    }

    public  int getmMusic(){
        return mMusicId;
    }

    //This method will check if image is present or not.
    public boolean hasImage(){
        return (mImageId != mResource);
    }
}
