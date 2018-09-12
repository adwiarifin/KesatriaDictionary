package com.kesatriakeyboard.kesatriadictionary.view;

import android.content.Context;
import android.content.Intent;
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
import com.kesatriakeyboard.kesatriadictionary.database.EnglishHelper;
import com.kesatriakeyboard.kesatriadictionary.model.WordModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class EnglishFragment extends Fragment implements WordAdapter.OnItemClickListener {

    @BindView(R.id.rv_word)
    RecyclerView rvWord;
    @BindView(R.id.text_query)
    EditText textQuery;

    private Context context;
    private boolean loading;

    private WordAdapter adapter;
    private ArrayList<WordModel> wordModels;
    private EnglishHelper englishHelper;

    public static EnglishFragment newInstance() {
        EnglishFragment fragment = new EnglishFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_english, container, false);
        ButterKnife.bind(this, view);

        context = getActivity();
        adapter = new WordAdapter(context, this);
        rvWord.setLayoutManager(new LinearLayoutManager(context));
        rvWord.setAdapter(adapter);
        loading = false;

        wordModels = new ArrayList<>();
        englishHelper = new EnglishHelper(context);

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

    @Override
    public void onItemClick(WordModel model) {
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra("source", "english");
        i.putExtra("model", model);
        startActivity(i);
    }

    private class LoadWords extends AsyncTask<String, Void, ArrayList<WordModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            loading = true;
            englishHelper.open();
            if (wordModels.size() > 0){
                wordModels.clear();
            }
        }

        @Override
        protected ArrayList<WordModel> doInBackground(String... strings) {
            return englishHelper.getDataByName(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<WordModel> words) {
            super.onPostExecute(words);

            wordModels.addAll(words);
            adapter.setData(words);
            adapter.notifyDataSetChanged();
            englishHelper.close();
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
