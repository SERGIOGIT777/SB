package com.example.javasb.FragmentTest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.javasb.R;
import com.example.javasb.databinding.FragmentTestIndexBinding;

public class TestIndexFragment extends Fragment {

    private FragmentTestIndexBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTestIndexBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}