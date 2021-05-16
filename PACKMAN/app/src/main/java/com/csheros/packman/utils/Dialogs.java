package com.csheros.packman.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.csheros.packman.R;

import org.jetbrains.annotations.NotNull;

public class Dialogs {

    private static final String SHARE_TITLE = "Share The Game";
    private static final String SHARE_MSG =
            "A pack-man game with one agent only supplied with AI .." +
                    "\n" +
                    "I challenge you to win level one :-P" +
                    "\n" +
                    "Try it now : " +
                    "https://play.google.com/store/apps/details?id=";

    public static String getShareMsg(Context context) {
        return SHARE_MSG + context.getPackageName();
    }

    public static Dialog getWinnerOrLooserDialog(
            int level,
            int score,
            boolean winner,
            View.OnClickListener onNextOrReplayClickListener,
            Context context
    ) {

        Dialog dialog = generateDialog(context);
        setDialogStatistics(
                level,
                score,
                dialog
        );
        // Implement nextLevel
        ImageView nextOrReplay = dialog.findViewById(R.id.nextOrReplay);
        ImageView share = dialog.findViewById(R.id.share);
        nextOrReplay.setOnClickListener(v -> {
            onNextOrReplayClickListener.onClick(v);
            dialog.dismiss();
        });
        share.setOnClickListener(v -> shareApp(context));

        if (winner) {
            TextView headingTxt = dialog.findViewById(R.id.headingTxt);
            ImageView gameStateImg = dialog.findViewById(R.id.gameStateImg);

            headingTxt.setText(context.getString(R.string.win_game));
            gameStateImg.setImageResource(R.drawable.cup_3);
            nextOrReplay.setImageResource(R.drawable.next_icon);
            nextOrReplay.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorPrimary)
            );
        }
        return dialog;
    }


    private static void setDialogStatistics(int level,
                                            int score,
                                            Dialog dialog
    ) {
        // Set Level
        TextView txtLevel = dialog.findViewById(R.id.level);
        txtLevel.setText(String.valueOf(level));
        // Set Score
        TextView txtScore = dialog.findViewById(R.id.score);
        txtScore.setText(String.valueOf(score));
    }

    @NotNull
    private static Dialog generateDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.winner_loser_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }


    private static void shareApp(Context context) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getShareMsg(context));
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, SHARE_TITLE);
        context.startActivity(shareIntent);
    }

}
