package com.kesatriakeyboard.kesatriadictionary.view;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import com.kesatriakeyboard.kesatriadictionary.R;
import com.kesatriakeyboard.kesatriadictionary.database.EnglishHelper;
import com.kesatriakeyboard.kesatriadictionary.database.IndonesiaHelper;
import com.kesatriakeyboard.kesatriadictionary.model.WordModel;
import com.kesatriakeyboard.kesatriadictionary.prefs.AppPreference;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        new LoadData().execute();
    }

    private class LoadData extends AsyncTask<Void, Integer, Void>{

        final String TAG = LoadData.class.getSimpleName();
        AppPreference appPreference;
        EnglishHelper englishHelper;
        IndonesiaHelper indonesiaHelper;
        double progress;
        double maxprogress = 100;

        @Override
        protected void onPreExecute() {
            appPreference = new AppPreference(SplashActivity.this);
            englishHelper = new EnglishHelper(SplashActivity.this);
            indonesiaHelper = new IndonesiaHelper(SplashActivity.this);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Boolean firstRun = appPreference.getFirstRun();

            if (firstRun) {
                ArrayList<WordModel> englishModels = preloadEnglish();
                ArrayList<WordModel> indonesiaModels = preloadIndonesia();

                progress = 30;
                publishProgress((int) progress);
                Double progressMaxInsert = 80.0;
                Double progressDiff = (progressMaxInsert - progress) / (englishModels.size() + indonesiaModels.size());

                englishHelper.open();
                englishHelper.beginTransaction();
                try {
                    for (WordModel model : englishModels) {
                        englishHelper.insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                englishHelper.setTransactionSuccess();
                englishHelper.endTransaction();
                englishHelper.close();

                indonesiaHelper.open();
                indonesiaHelper.beginTransaction();
                try {
                    for (WordModel model : indonesiaModels) {
                        indonesiaHelper.insertTransaction(model);
                        progress += progressDiff;
                        publishProgress((int) progress);
                    }
                } catch (Exception e) {
                    Log.e(TAG, "doInBackground: Exception");
                }
                indonesiaHelper.setTransactionSuccess();
                indonesiaHelper.endTransaction();
                indonesiaHelper.close();

                appPreference.setFirstRun(false);
                publishProgress((int) maxprogress);
            } else {
                publishProgress((int) maxprogress);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private ArrayList<WordModel> preloadEnglish() {
        ArrayList<WordModel> englishModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.english_indonesia);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] splitstr = line.split("\t");

                WordModel model;

                model = new WordModel(splitstr[0], splitstr[1]);
                englishModels.add(model);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return englishModels;
    }

    private ArrayList<WordModel> preloadIndonesia() {
        ArrayList<WordModel> indonesiaModels = new ArrayList<>();
        String line;
        BufferedReader reader;
        try {
            Resources res = getResources();
            InputStream raw_dict = res.openRawResource(R.raw.indonesia_english);

            reader = new BufferedReader(new InputStreamReader(raw_dict));
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] splitstr = line.split("\t");

                WordModel model;

                model = new WordModel(splitstr[0], splitstr[1]);
                indonesiaModels.add(model);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return indonesiaModels;
    }

}
