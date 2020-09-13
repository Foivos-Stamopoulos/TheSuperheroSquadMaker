package com.fivos.thesuperherosquadmaker.ui.superHeroes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fivos.thesuperherosquadmaker.data.Character;
import com.fivos.thesuperherosquadmaker.databinding.SuperHeroItemVerticalListBinding;

public class SuperheroPagedAdapter extends PagedListAdapter<Character, SuperheroPagedAdapter.ViewHolder> {

    private OnVerticalListItemClickListener mListener;

    SuperheroPagedAdapter() {
        super(DIFF_CALLBACK);
    }

    // Define the listener interface
    public interface OnVerticalListItemClickListener {
        void onVerticalListItemClick(View itemView, int position);
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnVerticalListItemClickListener(OnVerticalListItemClickListener listener) {
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(SuperHeroItemVerticalListBinding.inflate(LayoutInflater.from(parent.getContext())));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Character superhero = getItem(position);
        if (superhero != null) {
            if (superhero.getThumbnail() != null) {
                Glide.with(holder.avatarIV.getContext()).load(superhero.getThumbnail().getUrl()).circleCrop().into(holder.avatarIV);
            }
            holder.nameTV.setText(superhero.getName());
        }
    }

    private static DiffUtil.ItemCallback<Character> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Character>() {
                @Override
                public boolean areItemsTheSame(Character oldItem, Character newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Character oldItem, Character newItem) {
                    return oldItem.equals(newItem);
                }
            };

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView avatarIV;
        public TextView nameTV;

        public ViewHolder(SuperHeroItemVerticalListBinding itemView) {
            super(itemView.getRoot());
            avatarIV = itemView.avatarIV;
            nameTV = itemView.nameTV;
            // Setup the click listener
            itemView.getRoot().setOnClickListener(v -> {
                // Triggers click upwards to the adapter on click
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onVerticalListItemClick(itemView.getRoot(), position);
                    }
                }
            });
        }
    }

    int getCharacterId(int position) {
        Character superhero = getItem(position);
        if (superhero != null) {
            return superhero.getId();
        }
        return -1;
    }

}
