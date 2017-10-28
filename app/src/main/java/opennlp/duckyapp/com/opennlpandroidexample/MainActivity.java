package opennlp.duckyapp.com.opennlpandroidexample;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.Span;

public class MainActivity extends AppCompatActivity {

    EditText inputEt;
    SentencesDialogFragment sentencesDialogFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEt = (EditText) findViewById(R.id.inputText);

    }


    public void onClickButton(View view) {

        long startTime = System.currentTimeMillis();
        long consumeTime = 0;
        //Loading sentence detector model
        InputStream inputStream = null;
        SentenceModel model = null;
        String sentences[] = null;
        Span sentencesIndex[] = null;
        try {
            inputStream = getAssets().open("en-sent.bin");
            model = new SentenceModel(inputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //Instantiating the SentenceDetectorME class
        if (model != null) {
            SentenceDetectorME detector = new SentenceDetectorME(model);

            //Detecting the sentence
             sentences = detector.sentDetect(inputEt.getText().toString());
            sentencesIndex = detector.sentPosDetect(inputEt.getText().toString());

        }


        consumeTime = System.currentTimeMillis() - startTime;
        ArrayList<String> listSentences = new ArrayList<>();
        ArrayList<Span> spanArrayList = new ArrayList<>();
        if (sentences != null) {
            spanArrayList = new ArrayList<>(Arrays.asList(sentencesIndex));

        }



        FragmentManager fm = getSupportFragmentManager();
        SentencesDialogFragment sentencesDialogFragment = new SentencesDialogFragment(spanArrayList, inputEt.getText().toString(), consumeTime);

        if (fm.findFragmentByTag("fragment_answer_dialog") == null) {
            sentencesDialogFragment.show(fm, "fragment_answer_dialog");
        }

    }
}
