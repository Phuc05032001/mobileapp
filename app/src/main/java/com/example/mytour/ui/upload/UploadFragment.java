package com.example.mytour.ui.upload;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mytour.databinding.FragmentUploadBinding;

public class UploadFragment extends Fragment {

    private FragmentUploadBinding binding;

    Button searchBrowser, upload_btn;

    EditText inputBrowser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUploadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        inputBrowser = binding.inputBrowser;
        searchBrowser = binding.searchBrowser;

        searchBrowser.setOnClickListener(v -> {
            String Search = inputBrowser.getText().toString().trim();
            searchInternet(Search);
        });

        return root;
    }
//search internet with the default search top

    private void searchInternet(String string){

        try{
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, string);
            startActivity(intent);
        }
        catch (ActivityNotFoundException e){
            e.printStackTrace();
            searchInternetCompat(string);
        }
    }


//search internet with the browser if there's no default search app

    private void searchInternetCompat(String string){
        try{
            Uri uri = Uri.parse("https://www.google.com/#q=" + string);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        catch (ActivityNotFoundException e){
            e.printStackTrace();
            Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}