package com.sameer.texttospeechandspeechtotext.engine.TexttoSpeech;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.sameer.texttospeechandspeechtotext.R;

import java.util.Locale;

public class TexttoSpeechFragment extends Fragment {
    private TextToSpeech mTTS;
    private EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_text_to_speech, container, false);

        mButtonSpeak = root.findViewById(R.id.say_it);
        mTTS = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS){
                   int result =  mTTS.setLanguage(Locale.ENGLISH);
                   if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                       Log.e("TTS","Language not Supported.");
                   }
                   else{
                       mButtonSpeak.setEnabled(true);
                   }

                }
                else{
                    Log.e("TTS","Initialization Failed.");
                }
            }
        });

        mEditText = root.findViewById(R.id.enter_text);
        mSeekBarPitch = root.findViewById(R.id.seekbar_pitch);
        mSeekBarSpeed = root.findViewById(R.id.seekbar_speed);

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speak();
                mEditText.setText("");
            }
        });

        return root;
    }
    private void Speak(){
        String text = mEditText.getText().toString();
        float pitch = (float)mSeekBarPitch.getProgress()/50;
        // 50 means 1 100 means 2 and so on
        if(pitch < 0.1){
            pitch = 0.1f;
        }

        float speed = (float)mSeekBarSpeed.getProgress()/50;
        // 50 means 1 100 means 2 and so on
        if(speed < 0.1){
            speed = 0.1f;
        }

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);

    }

    @Override
    public void onDestroyView() {
        if(mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        super.onDestroyView();
    }
}