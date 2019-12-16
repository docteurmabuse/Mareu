package com.openclassrooms.mareu.ui.meeting_list.filters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.meeting_list.adapter.PlaceFilterAdapter;
import com.openclassrooms.mareu.ui.meeting_list.util.FilterContent;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FilterPlaceFragment.OnPlaceFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FilterPlaceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterPlaceFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnPlaceFragmentInteractionListener mListener;

    public static FilterPlaceFragment newInstance() {
        return new FilterPlaceFragment();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FilterPlaceFragment.
     */
   /* // TODO: Rename and change types and number of parameters
    public static FilterPlaceFragment newInstance(String param1, String param2) {
        FilterPlaceFragment fragment = new FilterPlaceFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.filter_content, container, false);
        TextView titre = view.findViewById(R.id.filter_text);
        titre.setText("Salle de Réunion : ");
        // Set the adapter
        Context context = view.getContext();

        RecyclerView recyclerView = view.findViewById(R.id.item_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new PlaceFilterAdapter(FilterContent.PLACE_ITEMS, (FilterListFragment.OnPlaceFragmentInteractionListener) mListener));
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(FilterContent.Places places) {
        if (mListener != null) {
            mListener.onPlaceFragmentInteraction(places);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlaceFragmentInteractionListener) {
            mListener = (OnPlaceFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnPlaceFragmentInteractionListener {
        // TODO: Update argument type and name
        void onPlaceFragmentInteraction(FilterContent.Places places);
    }
}
