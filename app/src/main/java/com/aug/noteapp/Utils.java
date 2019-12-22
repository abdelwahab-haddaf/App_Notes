package com.aug.noteapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.aug.noteapp.R;
import com.aug.noteapp.ColorsBottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    private static Toast mToast;
    private static ProgressDialog mDialog;

    public static void showToast(Context context, @StringRes int messageId) {
        if (mToast != null)
            mToast.cancel();

        mToast = Toast.makeText(context, messageId, Toast.LENGTH_LONG);
        mToast.show();
    }

    public static void showLoading(Context context) {
        hideLoading();
        mDialog = ProgressDialog.show(context, "", context.getString(R.string.load_pb), true);
        mDialog.show();
    }

    public static void hideLoading() {
        if (mDialog == null) {
            return;
        }
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * Check email validity
     *
     * @param email the email that entered by the user
     * @return is email valid or not
     */
    public static boolean isEmailValid(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static String parseDateToMMddYYYY(long timeinMilliSeccond) {
        return new SimpleDateFormat("MM/dd/yyyy").format(new Date(timeinMilliSeccond));
    }

    public static void setBackgroundCategory(View view, int color) {
        switch (color) {
            case ColorsBottomSheetDialogFragment.COLOR_LILAC:
                view.setBackgroundResource(R.drawable.shape_lilac_background_category);
                break;
            case ColorsBottomSheetDialogFragment.COLOR_GREEN:
                view.setBackgroundResource(R.drawable.shape_green_background_category);
                break;
            case ColorsBottomSheetDialogFragment.COLOR_ORANGE:
                view.setBackgroundResource(R.drawable.shape_orange_background_category);
                break;
            case ColorsBottomSheetDialogFragment.COLOR_RED:
                view.setBackgroundResource(R.drawable.shape_red_background_category);
                break;
            default:
                view.setBackgroundResource(R.drawable.shape_purple_background_category);
        }
    }

    public static void setBackgroundRectangle(View view, int color) {
        switch (color) {
            case ColorsBottomSheetDialogFragment.COLOR_LILAC:
                view.setBackgroundResource(R.drawable.shape_rectangle_color_lilac);
                break;
            case ColorsBottomSheetDialogFragment.COLOR_GREEN:
                view.setBackgroundResource(R.drawable.shape_rectangle_color_green);
                break;
            case ColorsBottomSheetDialogFragment.COLOR_ORANGE:
                view.setBackgroundResource(R.drawable.shape_rectangle_color_orange);
                break;
            case ColorsBottomSheetDialogFragment.COLOR_RED:
                view.setBackgroundResource(R.drawable.shape_rectangle_color_red);
                break;
            default:
                view.setBackgroundResource(R.drawable.shape_rectangle_color_purple);
        }
    }

    public static void setBackgroundOval(View view, int color) {
        switch (color) {
            case ColorsBottomSheetDialogFragment.COLOR_LILAC:
                view.setBackgroundResource(R.drawable.shape_oval_color_lilac);
                break;
            case ColorsBottomSheetDialogFragment.COLOR_GREEN:
                view.setBackgroundResource(R.drawable.shape_oval_color_green);
                break;
            case ColorsBottomSheetDialogFragment.COLOR_ORANGE:
                view.setBackgroundResource(R.drawable.shape_oval_color_orange);
                break;
            case ColorsBottomSheetDialogFragment.COLOR_RED:
                view.setBackgroundResource(R.drawable.shape_oval_color_red);
                break;
            default:
                view.setBackgroundResource(R.drawable.shape_oval_color_purple);
        }
    }
}