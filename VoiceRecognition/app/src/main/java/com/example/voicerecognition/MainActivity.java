package com.example.voicerecognition;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.system.ErrnoException;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static int REQUEST_CODE_STT = 1;
    Button btn_stt;
    TextView inputText, outputText;
    TextToSpeech ttobj;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_stt = findViewById(R.id.btn_stt);
        inputText = findViewById(R.id.et_text_input);
        outputText = findViewById(R.id.et_text_input_robot);
        outputText();

        btn_stt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sttIntent= new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, new Locale("yue", "HK"));
                sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!");
                startActivityForResult(sttIntent, REQUEST_CODE_STT);

            }
        });

    }

    private void outputText() {

        ttobj=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                ttobj.setLanguage(new Locale("zh", "HK"));
                ttobj.speak(outputText.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_STT){

            if (resultCode == Activity.RESULT_OK && data != null){

                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                if (result != null){

                    String txt = result.get(0);
                    inputText.setText(txt);

                    if (inputText.getText().toString().equals("落單") || inputText.getText().toString().equals("1")){

                        outputText.setText("你需要咩服務?\n1.跳開心舞\n2.上我屋企");
                        outputText();
                    }
                }
            }
        }
    }
}