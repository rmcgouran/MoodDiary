package com.rmcgouran.mooddiary;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.rmcgouran.mooddiary.adapters.voiceListAdapter;

import java.io.File;
import java.io.IOException;


public class voiceFragment extends Fragment implements voiceListAdapter.onItemListClick{

    private ConstraintLayout playerSheet;
    private BottomSheetBehavior bottomSheetBehavior;
    private RecyclerView voiceList;
    private File[] allFiles;

    private voiceListAdapter voiceListAdapter;


    private MediaPlayer mediaPlayer = null;
    private boolean isPlaying = false;

    private File fileToPlay;

    //UI elements from player_sheet
    private ImageButton playBtn;
    private TextView playerHeader;
    private TextView playerFilename;
    private SeekBar playerSeekbar;
    private Handler seekbarHandler;
    private Runnable updateSeekbar;



    private static final String MoodParam0 = "MoodParam0";
    private static final String MoodMoodParam0 = "MoodParam1";

    // TODO: Rename and change types of parameters
    private String moodParam0;
    private String moodParam1;
    private ImageButton deletevoice;
    private ImageButton sharevoice;

    public voiceFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static voiceFragment getInstance() {
        voiceFragment fragment = new voiceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            moodParam0 = getArguments().getString(MoodParam0);
            moodParam1 = getArguments().getString(MoodMoodParam0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_voice_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        playerSheet = view.findViewById(R.id.player_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(playerSheet);
        voiceList = view.findViewById(R.id.voice_list_view);


        //Initializing UI elements
        playBtn = view.findViewById(R.id.player_play_btn);

        playerHeader = view.findViewById(R.id.player_header_title);
        playerFilename = view.findViewById(R.id.player_filename);
        playerSeekbar = view.findViewById(R.id.player_seekbar);
        deletevoice = view.findViewById(R.id.delete_voice_entry);
        sharevoice = view.findViewById(R.id.share_voice_entry);

        //Reversing voiceList
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        voiceList.setLayoutManager(linearLayoutManager);


        String path = getActivity().getExternalFilesDir("/").getAbsolutePath();
        File directory = new File(path);
        allFiles = directory.listFiles();


        voiceListAdapter = new voiceListAdapter(allFiles, this);


        voiceList.setHasFixedSize(true);
        voiceList.setAdapter(voiceListAdapter);



        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //Leave this EMPTY
            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    pausevoice();
                } else {
                    if (fileToPlay != null){
                        resumevoice();
                    }
                }
            }//END OF onClick
        });

//-----CHANGING STATE OF SeekBar---------------------
        playerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if (fileToPlay != null) {
                    pausevoice();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (fileToPlay != null) {
                    int progress = seekBar.getProgress();
                    mediaPlayer.seekTo(progress);
                    resumevoice();
                }
            }
        });

    }
//END OF onViewCreated() METHOD---------------------------





    @Override
    public void onClickListener(File file, int position) {
        fileToPlay = file;
        if (isPlaying){
            stopvoice();
            isPlaying = false;
            playvoice(fileToPlay);

        } else {
            playvoice(fileToPlay);

        }
    }

//Pause and Resume from playBtn

    private void pausevoice(){
        mediaPlayer.pause();
        playBtn.setImageResource(R.drawable.player_play_btn);
        isPlaying = false;
        seekbarHandler.removeCallbacks(updateSeekbar);
    }


    private void resumevoice(){
        mediaPlayer.start();
        playBtn.setImageResource(R.drawable.player_pause_btn);
        isPlaying = true;
        createUpdateRunnable();
        seekbarHandler.postDelayed(updateSeekbar, 0);
    }



//STOP voice
    private void stopvoice() {
        //Stop voice
        playerHeader.setText("Paused");
        isPlaying = false;
        playBtn.setImageResource(R.drawable.player_play_btn);
        mediaPlayer.stop();
        seekbarHandler.removeCallbacks(updateSeekbar);
    }

//PLAY voice
    private void playvoice(final File fileToPlay) {

        mediaPlayer = new MediaPlayer();

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        deletevoice.setVisibility(View.VISIBLE);
        sharevoice.setVisibility(View.VISIBLE);

        try {
            mediaPlayer.setDataSource(fileToPlay.getAbsolutePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }


        playBtn.setImageResource(R.drawable.player_pause_btn);
        playerFilename.setText(fileToPlay.getName());
        playerHeader.setText("Playing");

        //Play voice
        isPlaying = true;

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopvoice();
                playerHeader.setText("Finished");
            }
        });

        playerSeekbar.setMax(mediaPlayer.getDuration());
        seekbarHandler = new Handler();
        createUpdateRunnable();
        seekbarHandler.postDelayed(updateSeekbar, 0);


        deletevoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(voiceFragment.this.getContext(), "This voice note has been deleted", Toast.LENGTH_LONG).show();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                stopvoice();
                fileToPlay.delete();
            }
        });

        sharevoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("voice/*");
                    String shareMessage= String.valueOf(fileToPlay);
                    shareMessage = shareMessage + "Download Here: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                    shareIntent.putExtra(Intent.EXTRA_STREAM, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    e.toString();
                }


            }
        });


    }

    private void stopPlaying() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        isPlaying = false;
        playerHeader.setText("Paused");
        playBtn.setImageResource(R.drawable.player_play_btn);
        seekbarHandler.removeCallbacks(updateSeekbar);
    }

    private void createUpdateRunnable() {
        updateSeekbar = new Runnable() {
            @Override
            public void run() {
                playerSeekbar.setProgress(mediaPlayer.getCurrentPosition());
                seekbarHandler.postDelayed(this, 500);
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        if(isPlaying) {
            stopPlaying();
        }
    }



}