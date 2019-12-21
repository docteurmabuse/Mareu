package com.openclassrooms.mareu.ui.meeting_list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.meeting_list.filters.FilterListFragment;
import com.openclassrooms.mareu.ui.meeting_list.util.FiltersContent;

import java.util.List;

public class PlaceFilterAdapter extends RecyclerView.Adapter<PlaceFilterAdapter.ViewHolder> {

    private final List<FiltersContent.Places> mValues;
    // public static  List<FiltersContent.Places> placeSelected ;
    private final FilterListFragment.OnPlaceFragmentInteractionListener mListener;
    public FiltersContent.Places places;
    private boolean isSelected;


    public PlaceFilterAdapter(List<FiltersContent.Places> mValues, FilterListFragment.OnPlaceFragmentInteractionListener mListener) {
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_filter_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final FiltersContent.Places places = mValues.get(position);

        holder.fPlaces = mValues.get(position);
        holder.fPlaceText.setText(mValues.get(position).getpName());
        holder.fCheckView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    if (holder.fCheckView.isChecked()) {
                        addPlace(mValues.get(position));
                        isSelected=true;
                    } else {
                        removePlace(mValues.get(position));
                        isSelected=false;
                    }
                }

            }

        });
        holder.fView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                //holder.fCheckView.setChecked(true);

                if (isSelected) {
                    holder.fCheckView.setChecked(false);
                    removePlace(mValues.get(position));
                    isSelected=false;
                } else {
                    holder.fCheckView.setChecked(true);
                    addPlace(mValues.get(position));
                    isSelected=true;
                }
                //   mListener.onPlaceFragmentInteraction(mValues.get(position), true);

            }
        });
    }

    private void removePlace(FiltersContent.Places places) {
        mListener.onPlaceFragmentInteraction(places, false);
    }

    private void addPlace(FiltersContent.Places pPlaces) {
        mListener.onPlaceFragmentInteraction(pPlaces, true);
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View fView;
        TextView fPlaceText;
        CheckBox fCheckView;
        FiltersContent.Places fPlaces;

        ViewHolder(View view) {
            super(view);
            fView = view;
            fPlaceText = view.findViewById(R.id.place_name);
            fCheckView = view.findViewById(R.id.chbx_place);
        }


    }
}
