package com.csheros.packman.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.csheros.packman.R;

import org.jetbrains.annotations.NotNull;

public class Dialogs {

    public static final String SHARE_TITLE = "Share The Game";
    public static final String SHARE_MSG =
            "A pack-man game with one agent only supplied with AI .." +
                    "\n" +
                    "I challenge you to win level one :-P" +
                    "\n" +
                    "Try it now" +
                    "https://play.google.com/store/apps/details?id=" +
                    Dialogs.class.getPackage().getName();


    public static Dialog getWinnerDialog(int level,
                                         int score,
                                         View.OnClickListener onNextClickListener,
                                         Context context) {
        Dialog dialog = generateDialog(context, R.layout.winner_dialog);
        setDialogStatistics(
                level,
                score,
                dialog,
                R.id.level_winnerDialog,
                R.id.score_winnerDialog
        );

        // Implement nextLevel
        ImageView nextLevel = dialog.findViewById(R.id.nextLevel);
        ImageView share = dialog.findViewById(R.id.winner_share);
        nextLevel.setOnClickListener(v -> {
            onNextClickListener.onClick(v);
            dialog.dismiss();
        });
        share.setOnClickListener(v -> shareApp(context));
        return dialog;
    }

    public static Dialog getLooserDialog(int level,
                                         int score,
                                         View.OnClickListener onReplayClickListener,
                                         Context context) {
        Dialog dialog = generateDialog(context, R.layout.loser_dialog);
        setDialogStatistics(
                level,
                score,
                dialog,
                R.id.level_loserDialog,
                R.id.score_loserDialog
        );

        ImageView replay = dialog.findViewById(R.id.replay);
        ImageView share = dialog.findViewById(R.id.share);

        // Implement replay
        replay.setOnClickListener(v -> {
            onReplayClickListener.onClick(v);
            dialog.dismiss();
        });
        share.setOnClickListener(v -> shareApp(context));

        return dialog;
    }

    private static void setDialogStatistics(int level, int score, Dialog dialog, int levelId, int scoreId) {
        // Set Level
        TextView txtLevel = dialog.findViewById(levelId);
        txtLevel.setText(String.valueOf(level));
        // Set Score
        TextView txtScore = dialog.findViewById(scoreId);
        txtScore.setText(String.valueOf(score));
    }

    @NotNull
    private static Dialog generateDialog(Context context, int dialogContentView) {
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(dialogContentView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }


    private static void shareApp(Context context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, SHARE_MSG);
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, SHARE_TITLE);
        context.startActivity(shareIntent);
    }

}
