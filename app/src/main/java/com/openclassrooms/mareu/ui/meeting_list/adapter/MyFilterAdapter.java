package com.openclassrooms.mareu.ui.meeting_list.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.meeting_list.FilterListFragment.OnListFragmentInteractionListener;
import com.openclassrooms.mareu.ui.meeting_list.util.FilterContent.FiltersItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link FiltersItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyFilterAdapter extends RecyclerView.Adapter<MyFilterAdapter.ViewHolder> {

    private final List<FiltersItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyFilterAdapter(List<FiltersItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_filter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FiltersItem filters = mValues.get(position);
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).toString());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public FiltersItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.filter_name);
        }

    }
}
