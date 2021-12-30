package com.example.javasb.FragmentText;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.javasb.R;
import com.example.javasb.databinding.FragmentJava8Binding;

import java.io.IOException;
import java.io.InputStream;

public class Java8Fragment extends Fragment {

    private FragmentJava8Binding binding;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentJava8Binding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.simpleWebViewJ8);
        try {
            InputStream is = getActivity().getAssets().open("j8.html");
            int size = is.available();
            byte[] arry = new byte[size];
            is.read(arry);
            is.close();
            String str_data = new String(arry);
            webView.loadDataWithBaseURL(null, new String(str_data),
                    "text/html", "utf-8", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = getArguments().getString("j8");
        webView.findAllAsync(str);
    }
}