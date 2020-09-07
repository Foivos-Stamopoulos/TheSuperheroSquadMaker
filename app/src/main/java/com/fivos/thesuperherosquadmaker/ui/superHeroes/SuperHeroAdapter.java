package com.fivos.thesuperherosquadmaker.ui.superHeroes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.databinding.SuperHeroItemVerticalListBinding;
import com.fivos.thesuperherosquadmaker.util.MyStringUtils;

import java.util.List;

public class SuperHeroAdapter extends RecyclerView.Adapter<SuperHeroAdapter.ViewHolder>{

    // Store a member variable for the Super Heroes
    private List<Character> mCharacters;
    private OnItemClickListener mListener;

    // Pass in the Super Heroes list into the constructor
    public SuperHeroAdapter(List<Character> characters) {
        this.mCharacters = characters;
    }

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(SuperHeroItemVerticalListBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mCharacters != null) {
            Character superHero = mCharacters.get(position);
            if (superHero != null) {
                String url = getThumbnailUrl(superHero);
                if (!MyStringUtils.isNoE(url)) {
                    Glide.with(holder.avatarIV.getContext()).load(url).circleCrop().into(holder.avatarIV);
                }
                holder.nameTV.setText(superHero.getName());
            }
        }
    }

    private String getThumbnailUrl(Character superHero) {
        if (superHero.getThumbnail() != null) {
            return superHero.getThumbnail().getPath() + "." + superHero.getThumbnail().getExtension();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mCharacters != null ? mCharacters.size() : 0;
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView avatarIV;
        public TextView nameTV;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(SuperHeroItemVerticalListBinding itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView.getRoot());
            avatarIV = itemView.avatarIV;
            nameTV = itemView.nameTV;
            // Setup the click listener
            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(itemView.getRoot(), position);
                        }
                    }
                }
            });
        }
    }

    int getCharacterId(int position) {
        if (mCharacters != null) {
            return mCharacters.get(position).getId();
        }
        return -1;
    }

}
