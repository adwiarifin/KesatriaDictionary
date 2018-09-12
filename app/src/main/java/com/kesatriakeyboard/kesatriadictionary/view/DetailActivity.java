package com.kesatriakeyboard.kesatriadictionary.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.kesatriakeyboard.kesatriadictionary.R;
import com.kesatriakeyboard.kesatriadictionary.model.WordModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.textview_word)
    TextView tvWord;
    @BindView(R.id.textview_info)
    TextView tvInfo;
    @BindView(R.id.textview_translation)
    TextView tvTranslation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        String source = getIntent().getStringExtra("source");
        WordModel model = getIntent().getParcelableExtra("model");

        if (source.equals("english")) {
            tvInfo.setText("Translation");
        } else {
            tvInfo.setText("Terjemahan");
        }

        tvWord.setText(model.getWord());
        tvTranslation.setText(model.getTranslation());
    }
}
