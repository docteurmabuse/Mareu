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
import com.openclassrooms.mareu.ui.meeting_list.util.FilterContent;

import java.util.List;

import static com.openclassrooms.mareu.ui.meeting_list.filters.FilterListFragment.placeSelected;

public class PlaceFilterAdapter extends RecyclerView.Adapter<PlaceFilterAdapter.ViewHolder> {

    private final List<FilterContent.Places> mValues;
    // public static  List<FilterContent.Places> placeSelected ;
    private final FilterListFragment.OnPlaceFragmentInteractionListener mListener;

    public PlaceFilterAdapter(List<FilterContent.Places> mValues, FilterListFragment.OnPlaceFragmentInteractionListener mListener) {
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
        final FilterContent.Places places = mValues.get(position);
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
                    } else {
                        removePlace(mValues.get(position));
                    }
                }

            }

            private void removePlace(FilterContent.Places places) {
                placeSelected.add(mValues.get(position));

            }

            private void addPlace(FilterContent.Places places) {
                placeSelected.remove(mValues.get(position));
            }
        });
        holder.fView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  mListener.onPlaceFragmentInteraction(placeSelected);

            }
        });
    }


    @Override
    public int getItemCount() {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View fView;
        TextView fPlaceText;
        CheckBox fCheckView;
        FilterContent.Places fPlaces;

        ViewHolder(View view) {
            super(view);
            fView = view;
            fPlaceText = view.findViewById(R.id.place_name);
            fCheckView = view.findViewById(R.id.chbx_place);
        }


    }
}
