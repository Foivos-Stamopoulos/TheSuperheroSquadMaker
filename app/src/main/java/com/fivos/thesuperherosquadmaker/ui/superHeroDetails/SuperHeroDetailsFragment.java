package com.fivos.thesuperherosquadmaker.ui.superHeroDetails;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.fivos.thesuperherosquadmaker.Injection;
import com.fivos.thesuperherosquadmaker.R;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.data.Comic;
import com.fivos.thesuperherosquadmaker.databinding.SuperHeroDetailsFragmentBinding;
import com.fivos.thesuperherosquadmaker.util.UnitConverter;
import com.google.android.material.snackbar.Snackbar;

public class SuperHeroDetailsFragment extends Fragment implements ConfirmationDialog.OnFireConfirmationDialogListener{

    private SuperHeroDetailsViewModel mViewModel;
    private int mId;
    private SuperHeroDetailsFragmentBinding mBinding;
    private int mRedColor;
    private int mPrimaryColor;

    public SuperHeroDetailsFragment() {
        // Empty Constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey("hero_id")) {
            mId = getArguments().getInt("hero_id");
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = SuperHeroDetailsFragmentBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this,
                new SuperHeroDetailsViewModelFactory(Injection.provideDataSource(getActivity()), mId))
                .get(SuperHeroDetailsViewModel.class);
        mRedColor = ContextCompat.getColor(getActivity(), R.color.colorAccent);
        mPrimaryColor = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        mBinding.button.setOnClickListener(v -> mViewModel.onButtonPressed());
        setupToolbar();
        subscribeToViewModel();
        mViewModel.start();
    }

    private void subscribeToViewModel() {
        mViewModel.getSuperHero().observe(getViewLifecycleOwner(), this::setupSuperHeroDetails);

        mViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                if (isLoading) {
                    showProgressDialog();
                } else {
                    hideProgressDialog();
                }
            }
        });

        mViewModel.getComics().observe(getViewLifecycleOwner(), comicsList -> {
            if (comicsList != null) {
                if (comicsList.size() > 0) {
                    Comic comic1 = comicsList.get(0);
                    String url = comic1.getThumbnail() != null ? comic1.getThumbnail().getUrl() : comic1.getThumbnailUrl();
                    Glide.with(getActivity()).load(url).into(mBinding.comic1IV);
                    mBinding.comic1TV.setText(comic1.getTitle());
                }
                if (comicsList.size() > 1) {
                    Comic comic2 = comicsList.get(1);
                    String url = comic2.getThumbnail() != null ? comic2.getThumbnail().getUrl() : comic2.getThumbnailUrl();
                    Glide.with(getActivity()).load(url).into(mBinding.comic2IV);
                    mBinding.comic2TV.setText(comic2.getTitle());
                }
            }

        });

        mViewModel.getTotalComicsAppeared().observe(getViewLifecycleOwner(), totalComicsAppeared -> {
            if (totalComicsAppeared != null && totalComicsAppeared > 2) {
                mBinding.otherComicsTV.setVisibility(View.VISIBLE);
                String text = getResources().getQuantityString(R.plurals.numberOfComicsAppeared,
                        totalComicsAppeared - 2, totalComicsAppeared - 2);
                mBinding.otherComicsTV.setText(text);
            } else {
                mBinding.otherComicsTV.setVisibility(View.INVISIBLE);
            }
        });

        mViewModel.getShouldRecruit().observe(getViewLifecycleOwner(), shouldRecruit -> {
            if (shouldRecruit != null) {
                styleButton(shouldRecruit);
            }
        });

        mViewModel.getShouldShowConfirmationDialog().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldShowConfirmationDialog) {
                if (shouldShowConfirmationDialog != null) {
                    if (shouldShowConfirmationDialog) {
                        showConfirmationDialog();
                    } else {
                        hideConfirmationDialog();
                    }
                }
            }
        });

        mViewModel.getSnackbarText().observe(getViewLifecycleOwner(), event -> {
            Integer msg = event.getContentIfNotHandled();
            if (msg != null) {
                Snackbar.make(mBinding.getRoot(), getString(msg), Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    private void setupSuperHeroDetails(Character superHero) {
        if (superHero != null) {
            String url = superHero.getThumbnail() != null ? superHero.getThumbnail().getUrl() : superHero.getThumbnailUrl();
            Glide.with(getActivity()).load(url).into(mBinding.avatarIV);
            mBinding.nameTV.setText(superHero.getName());
            mBinding.biographyTV.setText(superHero.getDescription());
        }
    }

    private void styleButton(boolean shouldRecruit) {
        if (shouldRecruit) {
            mBinding.button.setBackgroundTintList(ColorStateList.valueOf(mRedColor));
            mBinding.button.setText(R.string.button_recruit);
            mBinding.button.invalidate();
        } else {
            mBinding.button.setStrokeWidth((int) UnitConverter.convertDpToPixel(1, getActivity()));
            mBinding.button.setBackgroundTintList(ColorStateList.valueOf(mPrimaryColor));
            mBinding.button.setStrokeColor(ColorStateList.valueOf(mRedColor));
            mBinding.button.setText(R.string.button_fire);
            mBinding.button.invalidate();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = mBinding.toolbar;
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    private void showProgressDialog() {
        ProgressDialog dialog = new ProgressDialog();
        dialog.show(getActivity().getSupportFragmentManager(), "progress_dialog");
    }

    private void hideProgressDialog() {
        DialogFragment dialog = (DialogFragment) getActivity().getSupportFragmentManager().findFragmentByTag("progress_dialog");
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void showConfirmationDialog() {
        ConfirmationDialog dialog = new ConfirmationDialog();
        dialog.setTargetFragment(this, 300);
        dialog.show(getParentFragmentManager(), "confirmation_dialog");
    }

    private void hideConfirmationDialog() {
        DialogFragment dialog = (DialogFragment) getParentFragmentManager().findFragmentByTag("confirmation_dialog");
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void onFirePressed() {
        mViewModel.onFirePressed();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}