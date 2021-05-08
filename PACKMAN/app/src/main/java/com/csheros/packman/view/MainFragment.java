package com.csheros.packman.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.csheros.packman.R;
import com.csheros.packman.viewmodel.MainViewModel;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        View view = inflater.inflate(R.layout.main_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
//        mViewModel.getNodeMapLiveData().observe(getViewLifecycleOwner(),
//                nodeMap -> {
//
//                });
//
//        mViewModel.createNodeMap(0);
    }

}