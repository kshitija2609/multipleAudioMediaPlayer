package com.example.admin.multipleaudiomediaplayer;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    //Add ListView class

    ListView mainList;

//Add MediaPlayer class

    MediaPlayer mp;

//Add a list items in String

   List<String> listContent ;

//for fast forword and rewind
    int length;
    private int seekForwardTime = 10000; // 5000 milliseconds
    private int seekBackwardTime = 10000; // 5000 milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainList= (ListView)findViewById(R.id.listView);

        listContent=new ArrayList<>();
        Field[] fi=R.raw.class.getFields();
        for(int i= 0;i< fi.length;i++){
            listContent.add(fi[i].getName());
        }

        ArrayAdapter <String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,listContent);
        mainList.setAdapter(adapter);

        //clicklistner
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                //start

              try{
                if (mp != null) {
                    mp.release();    //This method releases any resource attached with MediaPlayer object
                }

                int resID = getResources().getIdentifier(listContent.get(position), "raw", getPackageName());
                mp = MediaPlayer.create(getApplicationContext(), resID);
                mp.start();

                }catch (Exception e){
                  Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();}
            }

        });



// rewind
        // seekTo(position = This method takes an integer, and move song to that particular position millisecond
        // current position -This method returns the current position of song in milliseconds
        ImageView rewind= (ImageView)findViewById(R.id.rewind);
        rewind.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int currentPosition = mp.getCurrentPosition();
                // check if seekBackward time is greater than 0 sec
                if (currentPosition - seekBackwardTime >= 0) {
                    // forward song
                    mp.seekTo(currentPosition - seekBackwardTime);
                } else {
                    // backward to starting position
                    mp.seekTo(0);
                }
                return false;
            }
        });

        //stop
        ImageView stop= (ImageView)findViewById(R.id.stop);
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop();
            }
        });

        //pause
        ImageView pause=(ImageView)findViewById(R.id.pause);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.pause();
                length = mp.getCurrentPosition();
            }
        });

        //Resume
        ImageView play=(ImageView)findViewById(R.id.play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.seekTo(length);
                mp.start();

            }
        });

        //forward
      //  getDuration()= This method returns the total time duration of song in milliseconds
        ImageView forward= (ImageView)findViewById(R.id.forward);
        forward.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // get current song position
                int currentPosition = mp.getCurrentPosition();
                // check if seekForward time is lesser than song duration

                if (currentPosition + seekForwardTime <= mp.getDuration()) {
                    // forward song
                    mp.seekTo(currentPosition + seekForwardTime);
                } else {
                    // forward to end position
                    mp.seekTo(mp.getDuration());
                }
                return false;
            }
        });

    }

    }


