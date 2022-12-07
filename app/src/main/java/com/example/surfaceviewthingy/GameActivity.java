package com.example.surfaceviewthingy;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity {

    DrawSurfaceView ds;
    Thread thread;
    boolean userAskBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_game_activity);

        ds = new DrawSurfaceView(this);
        thread = new Thread(ds);
        thread.start();

        ViewGroup.LayoutParams layoutParams = new ActionBar.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        addContentView(ds,layoutParams);

    }

    @Override
    public void finish() {
        super.finish();
        userAskBack = true;
        ds.threadRunning = false;
        while(true){
            try {
                thread.join();

            }
            catch (Exception e){
                e.printStackTrace();
            }
            break;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ds !=null){
            ds.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("data", "pause");
        if(userAskBack){
            Log.d("data", "user ask back");
        }
        else if(ds != null){
            ds.pause();
        }
    }



}