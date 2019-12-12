package com.openclassrooms.mareu.ui.meeting_list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.meeting_list.FilterListFragment;
import com.openclassrooms.mareu.ui.meeting_list.util.FilterContent;

import java.util.List;

public class PlaceFilterAdapter extends RecyclerView.Adapter<PlaceFilterAdapter.ViewHolder> {

    private final List<FilterContent.Places> mValues;
    private final FilterListFragment.OnListFragmentInteractionListener mListener;

    public PlaceFilterAdapter(List<FilterContent.Places> mValues, FilterListFragment.OnListFragmentInteractionListener mListener) {
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_filter_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final FilterContent.Places places = mValues.get(position);
        holder.fPlaces = mValues.get(position);
        holder.fIdView.setText(mValues.get(position).toString());
        holder.fView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //TODO   mListener.onListFragmentInteraction(holder.fPlaces);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View fView;
        public final TextView fIdView;
        public final CheckBox fCheckView;
        public FilterContent.Places fPlaces;

        public ViewHolder(View view) {
            super(view);
            fView = view;
            fIdView = view.findViewById(R.id.filter_name);
            fCheckView = view.findViewById(R.id.chbx_place);
        }

    }
}
