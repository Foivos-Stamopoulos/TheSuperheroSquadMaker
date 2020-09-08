package com.fivos.thesuperherosquadmaker.ui.superHeroDetails;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.fivos.thesuperherosquadmaker.R;
import com.fivos.thesuperherosquadmaker.util.UnitConverter;

public class ProgressDialog extends DialogFragment {

    public ProgressDialog() {
        // Empty Constructor
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.progress_dialog, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getDialog() != null && getDialog().getWindow() != null){
            //getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int size = (int) UnitConverter.convertDpToPixel(200, getActivity());
            getDialog().getWindow().setLayout(size, size);
            View v = getDialog().getWindow().getDecorView();
            v.setBackgroundResource(android.R.color.transparent);
        }
    }

}
