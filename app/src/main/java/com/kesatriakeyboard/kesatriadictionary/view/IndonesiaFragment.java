package com.kesatriakeyboard.kesatriadictionary.view;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.kesatriakeyboard.kesatriadictionary.R;
import com.kesatriakeyboard.kesatriadictionary.adapter.WordAdapter;
import com.kesatriakeyboard.kesatriadictionary.database.IndonesiaHelper;
import com.kesatriakeyboard.kesatriadictionary.model.WordModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class IndonesiaFragment extends Fragment {

    @BindView(R.id.rv_word)
    RecyclerView rvWord;
    @BindView(R.id.text_query)
    EditText textQuery;

    private Context context;
    private boolean loading;

    private WordAdapter adapter;
    private ArrayList<WordModel> wordModels;
    private IndonesiaHelper indonesiaHelper;

    public static IndonesiaFragment newInstance() {
        IndonesiaFragment fragment = new IndonesiaFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indonesia, container, false);
        ButterKnife.bind(this, view);

        context = getActivity();
        adapter = new WordAdapter(context);
        rvWord.setLayoutManager(new LinearLayoutManager(context));
        rvWord.setAdapter(adapter);
        loading = false;

        wordModels = new ArrayList<>();
        indonesiaHelper = new IndonesiaHelper(context);

        return view;
    }

    @OnTextChanged(R.id.text_query)
    public void textChanged() {
        if (!loading) {
            String query = textQuery.getText().toString().trim();

            if (!query.isEmpty()) {
                new LoadWords().execute(query);
            }
        }
    }

    private class LoadWords extends AsyncTask<String, Void, ArrayList<WordModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading = true;
            indonesiaHelper.open();
            if (wordModels.size() > 0){
                wordModels.clear();
            }
        }

        @Override
        protected ArrayList<WordModel> doInBackground(String... strings) {
            return indonesiaHelper.getDataByName(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<WordModel> words) {
            super.onPostExecute(words);

            wordModels.addAll(words);
            adapter.setData(words);
            adapter.notifyDataSetChanged();
            indonesiaHelper.close();
            loading = false;

            if (words.size() == 0){
                showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    private void showSnackbarMessage(String message){
        Snackbar.make(rvWord, message, Snackbar.LENGTH_SHORT).show();
    }
}
