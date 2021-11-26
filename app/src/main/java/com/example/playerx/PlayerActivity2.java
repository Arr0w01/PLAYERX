package com.example.playerx;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.palette.graphics.Palette;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.textservice.SpellCheckerInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.playerx.MainActivity.musicFiles;

public class PlayerActivity2 extends AppCompatActivity {
    TextView song_name, artist, current_duration, max_duration;
    ImageView cover_art, next_btn, back_btn, prev_btn, shuffle_btn, repeat_btn;
    SeekBar seekBar;
    FloatingActionButton play;
    int position = -1;
    static ArrayList<MusicFiles> listSongs = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private Thread playThread, prevThread, nextThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player2);
        initViews();
        getIntentMethod();
        song_name.setText(listSongs.get(position).getTitle());
        artist.setText(listSongs.get(position).getArtist());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser ){
                    mediaPlayer.seekTo(progress *1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity2.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null){
                    int current_position = mediaPlayer.getCurrentPosition()/1000;
                    seekBar.setProgress(current_position);
                    current_duration.setText(formattedTime(current_position));

                }
                handler.postDelayed(this::run, 1000);
            }
        });

    }

    @Override
    protected void onResume() {
       playThreadBtn();
       nextThreadBtn();
       prevThreadBtn();
        super.onResume();
    }

    private void prevThreadBtn() {
        prevThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                prev_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prevBtnClicked() {

        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position-1) <0 ? ( listSongs.size()-1):(position -1) );
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist.setText(listSongs.get(position).getArtist());

            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int current_position = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(current_position);

                    }
                    handler.postDelayed(this::run, 1000);
                }
            });
            play.setImageResource(R.drawable.pause);
            mediaPlayer.start();
        }

        else
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position-1) <0 ? (listSongs.size() -1):(position -1) );
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist.setText(listSongs.get(position).getArtist());

            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int current_position = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(current_position);

                    }
                    handler.postDelayed(this::run, 1000);
                }
            });
            play.setImageResource(R.drawable.pause);
            mediaPlayer.start();
        }
        
    }

    private void nextThreadBtn() {

        nextThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                next_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position+1) % listSongs.size() );
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist.setText(listSongs.get(position).getArtist());

            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int current_position = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(current_position);

                    }
                    handler.postDelayed(this::run, 1000);
                }
            });
            play.setImageResource(R.drawable.pause);
            mediaPlayer.start();
        }

        else
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position+1)% listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist.setText(listSongs.get(position).getArtist());

            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int current_position = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(current_position);

                    }
                    handler.postDelayed(this::run, 1000);
                }
            });
            play.setImageResource(R.drawable.pause);
            mediaPlayer.start();
        }
    }

    private void playThreadBtn() {
        playThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        platBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void platBtnClicked() {
        if (mediaPlayer.isPlaying())
        {
            play.setImageResource(R.drawable.play_arrow);
            mediaPlayer.pause();
            seekBar.setMax(mediaPlayer.getDuration()/1000);

            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int current_position = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(current_position);

                    }
                    handler.postDelayed(this::run, 1000);
                }
            });
        }

        else {
            play.setImageResource(R.drawable.pause);
            mediaPlayer.start();
            seekBar.setMax(mediaPlayer.getDuration()/1000);

            PlayerActivity2.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null){
                        int current_position = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(current_position);

                    }
                    handler.postDelayed(this::run, 1000);
                }
            });
        }
    }

    private String formattedTime(int current_position) {
    String totalOut = "";
    String totalNew = "";
    String seconds = String.valueOf(current_position %60);
    String miniutes = String.valueOf(current_position / 60);
    totalOut = miniutes +":" + seconds;
    totalNew = miniutes + ":"+ "0" + seconds;
    if (seconds.length() == 1)
    {
        return  totalNew;
    }
    else {
        return totalOut;
    }
    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position",-1);
        listSongs = musicFiles;

        if (listSongs != null){
            play.setImageResource(R.drawable.pause);
            uri = Uri.parse(listSongs.get(position).getPath());
        }
        if (mediaPlayer !=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
       }
        else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
                mediaPlayer.start();

        }
        seekBar.setMax(mediaPlayer.getDuration() /1000);
        metaData(uri);

    }

    private void initViews() {
        song_name = findViewById(R.id.songname);
        artist = findViewById(R.id.songartist);
        current_duration = findViewById(R.id.current_duration);
        max_duration = findViewById(R.id.max_duration);
        cover_art = findViewById(R.id.coverart);
        next_btn = findViewById(R.id.next);
        play = findViewById(R.id.play);
        prev_btn = findViewById(R.id.previous);
        back_btn = findViewById(R.id.back_btn);
        shuffle_btn = findViewById(R.id.shuffle);
        repeat_btn = findViewById(R.id.repeat);
        seekBar = findViewById(R.id.seekBar);
    }

    private void metaData(Uri uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int maxDuration = Integer.parseInt(listSongs.get(position).getDuration()) / 1000;
        max_duration.setText(formattedTime(maxDuration));
        byte[] art = retriever.getEmbeddedPicture();
        Bitmap bitmap ;

        if (art != null)
        {
            Glide.with(this).asBitmap().load(art).into(cover_art);
            bitmap = BitmapFactory.decodeByteArray(art, 0, art.length);
            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                @Override
                public void onGenerated(@Nullable Palette palette) {
                Palette.Swatch swatch = palette.getDominantSwatch();
                if (swatch != null)
                {
                    ImageView gradient = findViewById(R.id.imageviewGredient);
                    RelativeLayout mContainer = findViewById(R.id.mContainer);
                    gradient.setBackgroundResource(R.drawable.gradient_bg);
                    mContainer.setBackgroundResource(R.drawable.main_bg);
                    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[]{swatch.getRgb(), 0x00000000});
                    gradient.setBackground(gradientDrawable);

                    GradientDrawable gradientDrawableBg = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[]{swatch.getRgb(), swatch.getRgb()});
                    mContainer.setBackground(gradientDrawableBg);
                    song_name.setTextColor(swatch.getTitleTextColor());
                    artist.setTextColor(swatch.getBodyTextColor());
                }
                else
                {
                    ImageView gradient = findViewById(R.id.imageviewGredient);
                    RelativeLayout mContainer = findViewById(R.id.mContainer);
                    gradient.setBackgroundResource(R.drawable.gradient_bg);
                    mContainer.setBackgroundResource(R.drawable.main_bg);
                    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[]{0xff000000, 0x00000000});
                    gradient.setBackground(gradientDrawable);

                    GradientDrawable gradientDrawableBg = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[]{0xff000000, 0xff000000});
                    mContainer.setBackground(gradientDrawableBg);
                    song_name.setTextColor(Color.WHITE);
                    artist.setTextColor(Color.DKGRAY);
                }

                }
            });
        }
        else {
            Glide.with(this).asBitmap().load(R.drawable.cover1).into(cover_art);

         }
        }

    }

