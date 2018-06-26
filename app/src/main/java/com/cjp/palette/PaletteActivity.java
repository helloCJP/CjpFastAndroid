package com.cjp.palette;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.cjp.R;

/**
 * Created by 蔡金品 on 2018/3/23.
 * email : caijinpin@zhexinit.com
 */

public class PaletteActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(new PaletteView(this),new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(R.layout.palette_layout);
    }
}
