package com.csheros.packman.view;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.csheros.packman.Ads;
import com.csheros.packman.R;
import com.csheros.packman.engine.Creature;
import com.csheros.packman.engine.Node;
import com.csheros.packman.engine.NodeMap;
import com.csheros.packman.pojo.GameState;
import com.csheros.packman.utils.Dialogs;
import com.csheros.packman.utils.Direction;
import com.csheros.packman.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private LinearLayout gameWorld;
    private Button btnUp, btnDown, btnRight, btnLeft;
    private TextView txtLevel, txtScore;
    Dialog dialog;

    // Sounds
    private boolean evilMoveSoundsOn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        View view = inflater.inflate(R.layout.main_fragment, container, false);

        // Get views
        gameWorld = view.findViewById(R.id.gameWorld);

        btnUp = view.findViewById(R.id.btnUp);
        btnDown = view.findViewById(R.id.btnDown);
        btnLeft = view.findViewById(R.id.btnLeft);
        btnRight = view.findViewById(R.id.btnRight);
        for (Button button : new Button[]{btnUp, btnRight, btnLeft, btnDown}) {
            button.setOnClickListener(this::setPackManDirection);
        }

        // Statistics
        txtLevel = view.findViewById(R.id.txtLevel);
        txtScore = view.findViewById(R.id.txtScore);

        // init dialog
        dialog = new Dialog(getContext());
        dialog.setCancelable(false);

        // Sounds
        evilMoveSoundsOn = true;

        //disable dark mood
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider.
                AndroidViewModelFactory(getActivity().getApplication())
                .create(MainViewModel.class);

        mViewModel.getGameStateLiveData().observe(getViewLifecycleOwner(),
                this::gameStateReceived);
        mViewModel.getNodeMapLiveData().observe(getViewLifecycleOwner(),
                nodeMap -> mViewModel.startGame(
                        nodeMap,
                        3,
                        10,
                        20,
                        200
                )
        );

        mViewModel.getCurrentLevelLiveData().observe(getViewLifecycleOwner(),
                level -> {
                    txtLevel.setText(String.valueOf(level));
                    evilMoveSoundsOn = true;
                });

        mViewModel.createNodeMap(1);

    }

    private void gameStateReceived(GameState gameState) {
        nodeMapReceived(gameState.getEngine().getNodeMap());
        updateStatistics(gameState);
        showWinnerOrLoserDialog(gameState);
        stopSoundsIfFinished(gameState);
        playSounds(gameState);
    }

    private void playSounds(GameState gameState) {

        if (gameState.isAteMasterPoint())
            playSound(R.raw.hala2_alieh, VolumeType.HIGH);

        if (gameState.isAtePoint())
            playSound(R.raw.eat, VolumeType.LOW);

        if (gameState.isPackManDied())
            playSound(R.raw.death, VolumeType.HIGH);

        if (evilMoveSoundsOn)
            playSound(R.raw.ghost, VolumeType.LOWER);
    }

    enum VolumeType {
        HIGH, LOW, LOWER
    }

    private void playSound(int id, VolumeType volumeType) {
        MediaPlayer player = MediaPlayer.create(getContext(), id);
        float volume = 0.7f;
        if (volumeType == VolumeType.LOW)
            volume = 0.2f;
        else if (volumeType == VolumeType.LOWER)
            volume = 0.05f;
        player.setVolume(volume, volume);
        player.setOnCompletionListener(MediaPlayer::release);
        player.start();
    }

    private void stopSoundsIfFinished(GameState gameState) {
        if (gameState.isGameFinished() || gameState.isPackManDied())
            evilMoveSoundsOn = false;
    }

    private void updateStatistics(GameState gameState) {
        int score = gameState.getEngine().getScore();
        txtScore.setText(String.valueOf(score));
    }

    private NodeMap currentMap;

    private void nodeMapReceived(NodeMap nodeMap) {
        gameWorld.removeAllViews();
        currentMap = nodeMap;
        List<Node[]> rows = nodeMap.getNodesMap();
        for (Node[] row : rows) {
            // Linear for row
            LinearLayout layoutRow = createRowLinear();
            // add to row linear
            for (Node node : row) {
                // One square
                LinearLayout nodeLayout = createNodeLayout(node);
                layoutRow.addView(nodeLayout);
            }
            gameWorld.addView(layoutRow);
        }
    }

    private LinearLayout createNodeLayout(Node node) {
        // Create Node Layout
        LinearLayout nodeView = createRowLinear();
        nodeView.setGravity(Gravity.CENTER);

        // Add Images
        List<Creature> creatures = new ArrayList<>(node.getCreatures());
        Collections.reverse(creatures);
        for (Creature creature : creatures) {
            ImageView img = new ImageView(getContext());

            LayoutParams params = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            img.setLayoutParams(params);
            img.setImageResource(creature.getImgAsset());

            nodeView.addView(img);
        }

        return nodeView;
    }

    private LinearLayout createRowLinear() {
        LinearLayout layoutRow = new LinearLayout(getContext());
        layoutRow.setOrientation(LinearLayout.HORIZONTAL);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
        );
        params.weight = 1;
        layoutRow.setLayoutParams(
                params
        );
        return layoutRow;
    }

    public void setPackManDirection(View view) {
        switch (view.getId()) {
            case R.id.btnUp:
                currentMap.setPackManDirection(Direction.UP);
                break;
            case R.id.btnDown:
                currentMap.setPackManDirection(Direction.DOWN);
                break;
            case R.id.btnLeft:
                currentMap.setPackManDirection(Direction.LEFT);
                break;
            case R.id.btnRight:
                currentMap.setPackManDirection(Direction.RIGHT);
                break;
        }
    }

    public void showWinnerOrLoserDialog(GameState gameState) {
        int level = mViewModel.getCurrentLevelLiveData().getValue();
        int score = mViewModel.getGameStateLiveData().getValue().getEngine().getScore();
        if (gameState.isPackManDied()) {
            Dialogs.getWinnerOrLooserDialog(
                    level,
                    score,
                    false,
                    v -> mViewModel.createNodeMap(level),
                    getContext()
            ).show();
            Ads.showAd(getActivity());
        } else if (gameState.isGameFinished()) {
            Dialogs.getWinnerOrLooserDialog(
                    level,
                    score,
                    true,
                    v -> mViewModel.createNodeMap(level + 1),
                    getContext()
            ).show();
            Ads.showAd(getActivity());
        }
    }

}