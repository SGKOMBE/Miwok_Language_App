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

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mColorResource;

    /*
     *This is the constructor for this class which initialises this class.
     * @param context : It is used to set the context to the Numbers activity.
     * @param ArrayList<Class> list_name : It receives the object of the class to be used.
     */
    public WordAdapter(Activity context, ArrayList<Word> words, int colorResource) {
        super(context, 0, words);
        mColorResource = colorResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View list = convertView;
        //To check if the View is empty or not?
        if (list == null) {
            list = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //Creating object of the Word class to set the data.
        Word currentWord = getItem(position);

        //Creating the Miwok translaton word textView.
        TextView txt1 = (TextView) list.findViewById(R.id.miwok_numbers);
        txt1.setText(currentWord.getMiwok());

        //Creating the Default language word TextView.
        TextView txt2 = (TextView) list.findViewById(R.id.english_numbers);
        txt2.setText(currentWord.getDefault());

        //Creating the image View.
        ImageView img = (ImageView) list.findViewById(R.id.img_view);

        //It will check if image is present, and if present it will set Image View.
        if (currentWord.hasImage())
            img.setImageResource(currentWord.getImage());

            //Else it will completely hide that Image view.
        else
            img.setVisibility(View.GONE);

        LinearLayout linear = (LinearLayout) list.findViewById(R.id.linear);

        //Converting color resource id to color.
        int color = ContextCompat.getColor(getContext(), mColorResource);

        //Setting background color.
        linear.setBackgroundColor(color);

        //Returning the whole data as an object.
        return list;
    }
}
