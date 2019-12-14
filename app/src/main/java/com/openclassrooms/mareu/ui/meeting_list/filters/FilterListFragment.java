package com.openclassrooms.mareu.ui.meeting_list.filters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.meeting_list.adapter.MyFilterAdapter;
import com.openclassrooms.mareu.ui.meeting_list.adapter.PlaceFilterAdapter;
import com.openclassrooms.mareu.ui.meeting_list.util.FilterContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class FilterListFragment extends DialogFragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private FilterListFragment.OnPlaceFragmentInteractionListener mListener2;

    public static final String TAG = "filter_dialog";
    private Toolbar toolbar;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     *//*
    public FilterListFragment() {
    }*/
    public static FilterListFragment display(FragmentManager fragmentMananger) {
        FilterListFragment filterListFragment = new FilterListFragment();
        filterListFragment.show(fragmentMananger, TAG);
        return filterListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_content, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        TextView titre = view.findViewById(R.id.filter_text);
        MaterialButton bFinnish = view.findViewById(R.id.finnish_btn);

        //titre.setText("Filtres : ");
        // Set the adapter
        final Context context = view.getContext();
        bFinnish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "Button3 Clicked!", Toast.LENGTH_SHORT).show();
            }
        });
        RecyclerView recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyFilterAdapter(FilterContent.ITEMS, mListener));


        RecyclerView recyclerView2 = view.findViewById(R.id.recycler_1);
        recyclerView2.setLayoutManager(new LinearLayoutManager(context));
        recyclerView2.setAdapter(new PlaceFilterAdapter(FilterContent.PLACE_ITEMS, mListener2));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        toolbar.setTitle("Filtres");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

        void onListFragmentInteraction(FilterContent.FiltersItem item);
    }

    public interface OnPlaceFragmentInteractionListener {

        void onPlaceFragmentInteraction(List<FilterContent.Places> placeSelected);
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }


}
