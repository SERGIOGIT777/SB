package com.example.javasb.FragmentText;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.javasb.R;
import com.example.javasb.databinding.FragmentJavacfBinding;

import java.io.IOException;
import java.io.InputStream;

public class JavacfFragment extends Fragment {

    private FragmentJavacfBinding binding;
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentJavacfBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.simpleWebViewJCF);
        try {
            InputStream is = getActivity().getAssets().open("jcf.html");
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
        String str = getArguments().getString("jcf");
        webView.findAllAsync(str);
    }

}