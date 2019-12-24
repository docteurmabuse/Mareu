package com.openclassrooms.mareu.ui.meeting_list.adapter;

import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.meeting_list.filters.FilterListFragment.OnListFragmentInteractionListener;
import com.openclassrooms.mareu.ui.meeting_list.filters.FiltersContent.FiltersItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
                switch (holder.mItem.id) {
                    case 1:
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            selectFilterDate(v);
                        }
                        break;
                }

            }
        });
    }

    private void selectFilterDate(View v) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(v.getRootView().getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        DateFormat formatter = null;
                        Date convertedDate = null;
                        Date filterDate = null;

                        String fDate = (day + "/" + (month + 1) + '/' + year);
                        formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.FRENCH);
                        try {
                            convertedDate = formatter.parse(fDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date mDate = convertedDate;
                        assert mDate != null;
                        mListener.onListFragmentInteraction(mDate);

                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        datePickerDialog.show();


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
