package com.fivos.thesuperherosquadmaker.ui.superHeroDetails;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.fivos.thesuperherosquadmaker.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ConfirmationDialog extends DialogFragment {

    public ConfirmationDialog() {
        // Empty Constructor
    }

    public interface OnFireConfirmationDialogListener {
        void onFirePressed();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getString(R.string.fire_confirmation_dialog_title))
                .setMessage(getString(R.string.fire_confirmation_dialog_description))
                .setPositiveButton(getString(R.string.fire_confirmation_dialog_ok), (dialogInterface, i) -> {
                    OnFireConfirmationDialogListener listener = ((OnFireConfirmationDialogListener) getTargetFragment());
                    if (listener != null) {
                        listener.onFirePressed();
                    }
                })
                .setNegativeButton(getString(R.string.fire_confirmation_dialog_cancel), (dialogInterface, i) -> {
                    // do nothing
                })
                .show();
    }
}
