package com.aug.noteapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ColorsBottomSheetDialogFragment extends BottomSheetDialogFragment
        implements View.OnClickListener {
    public static final String TAG = "ColorsBottomSheetDialogFragment";
    public static final int COLOR_LILAC = 0;
    public static final int COLOR_GREEN = 1;
    public static final int COLOR_ORANGE = 2;
    public static final int COLOR_RED = 3;
    public static final int COLOR_PURPLE = 4;
    private ItemClickListener mListener;

    public static ColorsBottomSheetDialogFragment newInstance() {
        return new ColorsBottomSheetDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.colors_bottom_sheet_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.view_color_lilac).setOnClickListener(this);
        view.findViewById(R.id.view_color_green).setOnClickListener(this);
        view.findViewById(R.id.view_color_orange).setOnClickListener(this);
        view.findViewById(R.id.view_color_red).setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ItemClickListener) {
            mListener = (ItemClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ItemClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view_color_lilac:
                mListener.onItemClick(COLOR_LILAC);
                break;
            case R.id.view_color_green:
                mListener.onItemClick(COLOR_GREEN);
                break;
            case R.id.view_color_orange:
                mListener.onItemClick(COLOR_ORANGE);
                break;
            case R.id.view_color_red:
                mListener.onItemClick(COLOR_RED);
                break;
        }
        dismiss();
    }

    public interface ItemClickListener {
        void onItemClick(int color);
    }
}
