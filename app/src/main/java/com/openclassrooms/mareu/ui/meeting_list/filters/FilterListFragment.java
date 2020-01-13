package com.openclassrooms.mareu.ui.meeting_list.filters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.meeting_list.adapter.PlaceFilterAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static com.openclassrooms.mareu.ui.meeting_list.filters.Filters.PLACE_ITEMS;
import static com.openclassrooms.mareu.ui.meeting_list.filters.Filters.Places;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.clr;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.day;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.month;
import static com.openclassrooms.mareu.ui.meeting_list.utils.Utils.year;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FilterListFragment extends DialogFragment {

    private OnListFragmentInteractionListener mListener;
    private OnPlaceFragmentInteractionListener mListener2;
    private List<Filters.Places> sortedPlaces;
    private OnFilterButtonClickListener mListener3;

    private static final String TAG = "filter_dialog";
    private Toolbar toolbar;
    private TextView dateText;


    public static void display(FragmentManager fragmentMananger) {
        FilterListFragment filterListFragment = new FilterListFragment();
        filterListFragment.show(fragmentMananger, TAG);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        sortedPlaces = PLACE_ITEMS;
        Collections.sort(sortedPlaces, new Comparator<Places>() {
            @Override
            public int compare(Places o1, Places o2) {
                return o1.getpName().compareToIgnoreCase(o2.getpName());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_content, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        TextView titre = view.findViewById(R.id.filter_text);

        final Context context = view.getContext();

        RecyclerView placeRecyclerView2 = view.findViewById(R.id.recycler_1);
        placeRecyclerView2.setLayoutManager(new LinearLayoutManager(context));
        placeRecyclerView2.setAdapter(new PlaceFilterAdapter(sortedPlaces, mListener2));

        MaterialButton bDateFilter = view.findViewById(R.id.date_btn);
        dateText = view.findViewById(R.id.filter_date_txt);
        bDateFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFilterDate(v);
            }
        });
        MaterialButton bFinnish = view.findViewById(R.id.finnish_btn);
        bFinnish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener3.onFilterButtonClick();
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener3.onFilterButtonClick();
                dismiss();
            }
        });
        toolbar.setTitle("Filtres");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        if (context instanceof OnPlaceFragmentInteractionListener) {
            mListener2 = (FilterListFragment.OnPlaceFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        if (context instanceof OnFilterButtonClickListener) {
            mListener3 = (FilterListFragment.OnFilterButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mListener2 = null;
        mListener3 = null;
    }

    private void selectFilterDate(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(v.getRootView().getContext(), R.style.DatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        DateFormat formatter;
                        Date convertedDate = null;
                        String fDate = String.format("%02d/%02d", day, month + 1) + '/' + year;
                        formatter = new SimpleDateFormat("dd/mm/yyyy", Locale.FRENCH);
                        try {
                            convertedDate = formatter.parse(fDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date mDate = convertedDate;
                        dateText.setText(fDate);
                        assert mDate != null;
                        mListener.onListFragmentInteraction(mDate);

                    }
                }, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(clr.getTimeInMillis());
        datePickerDialog.show();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {

        void onListFragmentInteraction(Date fDate);
    }

    public interface OnPlaceFragmentInteractionListener {

        void onPlaceFragmentInteraction(Places fPlaces, Boolean isSelected);
    }

    public interface OnFilterButtonClickListener {
        void onFilterButtonClick();
    }

}
