package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.events.DeleteMeetingEvent;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;
import com.openclassrooms.mareu.ui.meeting_list.adapter.MeetingAdapter;
import com.openclassrooms.mareu.ui.meeting_list.filters.FilterListFragment;
import com.openclassrooms.mareu.ui.meeting_list.filters.Filters;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class MeetingsActivity extends AppCompatActivity implements FilterListFragment.OnListFragmentInteractionListener, FilterListFragment.OnPlaceFragmentInteractionListener, FilterListFragment.OnFilterButtonClickListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private MeetingAdapter mAdapter;
    private MeetingApiService mApiService;
    private boolean mTwoPane;
    private List<Meeting> mMeetings;
    private List<Filters.Places> placeSelected;
    private Date fDate;
    private TextView emptyData;
    private ImageView noMeeting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
        placeSelected = new ArrayList<>();
        fDate = null;
        setContentView(R.layout.activity_meetings);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        initMeetingsView();
        initRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.item_recylerview);
        emptyData = findViewById(R.id.empty_data_txt);
        noMeeting = findViewById(R.id.no_meeting_ico);
        if (mApiService.getMeetings().size() < 1) {
            mRecyclerView.setVisibility(View.GONE);
            emptyData.setVisibility(View.VISIBLE);
            noMeeting.setVisibility(View.VISIBLE);

        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyData.setVisibility(View.GONE);
            noMeeting.setVisibility(View.GONE);

        }
        mAdapter = new MeetingAdapter(this, mApiService.getMeetings(), mTwoPane);
        assert mRecyclerView != null;
        mRecyclerView.setItemAnimator(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initMeetingsView() {
        fab = findViewById(R.id.fab_add_meeting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, NewMeetingActivity.class);
                context.startActivity(intent);
            }
        });
        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu_filter, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.icon_filter_menu) {
            initFiltersView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initFiltersView() {
        FilterListFragment.display(getSupportFragmentManager());
    }


    public void onListFragmentInteraction(Date mDate) {
        fDate = mDate;
    }

    public void onPlaceFragmentInteraction(Filters.Places places, Boolean isSelected) {
        RecyclerView mRecyclerView = findViewById(R.id.item_recylerview);
        assert mRecyclerView != null;

        if (isSelected) {
            placeSelected.add(places);
        } else {
            placeSelected.remove(places);
        }
    }

    public void onFilterButtonClick() {
        if (fDate == null && placeSelected.size() < 1) {
            mAdapter.mMeetings = mApiService.getMeetings();
            if (mApiService.getMeetings().size() < 1) {
                mRecyclerView.setVisibility(View.GONE);
                emptyData.setVisibility(View.VISIBLE);
                noMeeting.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                emptyData.setVisibility(View.GONE);
                noMeeting.setVisibility(View.GONE);
            }
        } else {
            mAdapter.mMeetings = mApiService.getFilteredMeetings(fDate, placeSelected);
            if (mApiService.getFilteredMeetings(fDate, placeSelected).size() < 1) {
                mRecyclerView.setVisibility(View.GONE);
                emptyData.setVisibility(View.VISIBLE);
                noMeeting.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.VISIBLE);
                emptyData.setVisibility(View.GONE);
                noMeeting.setVisibility(View.GONE);
            }
        }
        placeSelected.clear();
        fDate = null;
        Objects.requireNonNull(mRecyclerView.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        initRecyclerView();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void DeleteMeetingEvent(DeleteMeetingEvent event) {
        mApiService.deleteMeeting(event.meeting);
        initRecyclerView();
    }
}
