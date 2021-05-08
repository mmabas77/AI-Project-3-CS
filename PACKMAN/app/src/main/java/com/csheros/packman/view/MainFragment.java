package com.csheros.packman.view;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.csheros.packman.R;
import com.csheros.packman.engine.Creature;
import com.csheros.packman.engine.Node;
import com.csheros.packman.engine.NodeMap;
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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = new ViewModelProvider.
                AndroidViewModelFactory(getActivity().getApplication())
                .create(MainViewModel.class);

        mViewModel.getGameStateLiveData().observe(getViewLifecycleOwner(),
                gameState -> {
                    nodeMapReceived(gameState.getEngine().getNodeMap());
                });
        mViewModel.getNodeMapLiveData().observe(getViewLifecycleOwner(),
                nodeMap -> mViewModel.startGame(
                        nodeMap,
                        2,
                        10,
                        20,
                        200
                )
        );

        mViewModel.getCurrentLevelLiveData().observe(getViewLifecycleOwner(),
                level -> {
                    mViewModel.createNodeMap(level);
                });

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
}