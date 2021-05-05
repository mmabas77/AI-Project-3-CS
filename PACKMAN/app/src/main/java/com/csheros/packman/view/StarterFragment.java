package com.csheros.packman.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csheros.packman.R;
import com.csheros.packman.viewmodel.StarterFragmentViewModel;

public class StarterFragment extends Fragment {

    private StarterFragmentViewModel mViewModel;

    public static StarterFragment newInstance() {
        return new StarterFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.starter_fragement_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StarterFragmentViewModel.class);
        // TODO: Use the ViewModel
    }

}