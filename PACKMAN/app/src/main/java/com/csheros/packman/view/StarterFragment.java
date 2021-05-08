package com.csheros.packman.view;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        View view = inflater.inflate(R.layout.starter_fragement_fragment, container, false);

        Button btn_Start_fragment = (Button)view.findViewById(R.id.start_game);
        Button btn_exit_fragment = (Button)view.findViewById(R.id.end_game);

        btn_Start_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow();
            }
        });

        btn_exit_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider.
                AndroidViewModelFactory(getActivity().getApplication())
                .create(StarterFragmentViewModel.class);
        // TODO: Use the ViewModel
    }

}