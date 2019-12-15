package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.events.DeleteMeetingEvent;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;
import com.openclassrooms.mareu.ui.meeting_list.adapter.MeetingAdapter;
import com.openclassrooms.mareu.ui.meeting_list.filters.FilterListFragment;
import com.openclassrooms.mareu.ui.meeting_list.util.FilterContent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class MeetingsFragment extends Fragment implements FilterListFragment.OnPlaceFragmentInteractionListener {

    private RecyclerView mRecyclerView;

    private MeetingApiService mApiService;
    private boolean mTwoPane;


    protected MeetingsFragment newInstance() {
        return new MeetingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));
        if (view.findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        initList();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void DeleteMeetingEvent(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        initList();
    }

    private void initList() {
        mApiService = DI.getMeetingApiService();
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);
    }

    public void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MeetingAdapter((MeetingsActivity) getActivity(), mApiService.getMeetings(), mTwoPane));
    }

    public void onPlaceFragmentInteraction(FilterContent.Places places, Boolean isSelected) {
        //TODO
        Snackbar.make(this.getView(), "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        List<Meeting> fMeetings = new ArrayList<>();
        for (Meeting meeting : mApiService.getMeetings()) {
            if (meeting.getmPlace().contains("Mario")) {
                fMeetings.add(meeting);
            }
        }
        mRecyclerView.setAdapter(new MeetingAdapter((MeetingsActivity) getActivity(), fMeetings, mTwoPane));
    }

}
