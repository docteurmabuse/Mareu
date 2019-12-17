package com.openclassrooms.mareu.ui.meeting_list;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;
import com.openclassrooms.mareu.ui.meeting_list.adapter.MeetingAdapter;
import com.openclassrooms.mareu.ui.meeting_list.filters.FilterListFragment;
import com.openclassrooms.mareu.ui.meeting_list.util.Filters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class MeetingsActivity extends AppCompatActivity implements FilterListFragment.OnListFragmentInteractionListener, FilterListFragment.OnPlaceFragmentInteractionListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private MeetingAdapter mAdapter;
    private MeetingApiService mApiService;
    private boolean mTwoPane;
    private List<Meeting> fMeetings;

    public MeetingsActivity() {
        mApiService = DI.getMeetingApiService();
        fMeetings = Objects.requireNonNull(mApiService).getMeetings();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mApiService = DI.getMeetingApiService();
        setContentView(R.layout.activity_meetings);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        initMeetingsView();
        initRecyclerView();
    }


    private void initRecyclerView() {
        mRecyclerView = findViewById(R.id.item_recylerview);
        assert mRecyclerView != null;
        setupRecyclerView(mRecyclerView);
    }

    private void initMeetingsView() {
        Fragment newFragment = new MeetingsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.add(R.id.frameLayout, newFragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        fab = findViewById(R.id.fab_add_meeting);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, NewMeetingActivity.class);
                context.startActivity(intent);
            }
        });
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

    public void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        mAdapter = new MeetingAdapter(this, fMeetings, mTwoPane);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    private void initFiltersView() {
        FilterListFragment.display(getSupportFragmentManager());

//        FilterListFragment filterListFragment = new FilterListFragment();
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
//        if (prev != null) {
//            transaction.remove(prev);
//        }
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.content_meeting, filterListFragment);
//        transaction.commit();
    }


    public void onListFragmentInteraction(Filters.FiltersItem item) {

        Toast.makeText(getApplicationContext(), "Hello Filter", Toast.LENGTH_SHORT).show();
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                    }
                }, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    public void onPlaceFragmentInteraction(Filters.Places places, Boolean isSelected) {

        Toast.makeText(getApplicationContext(), "Hello Places2", Toast.LENGTH_SHORT).show();
        mRecyclerView = findViewById(R.id.item_recylerview);
        assert mRecyclerView != null;
        fMeetings = new ArrayList<>();
        for (Meeting meeting : mApiService.getMeetings()) {
            if (meeting.getmPlace().contains("Mario")) {
                fMeetings.add(meeting);
            }
        }
        mRecyclerView.setAdapter(new MeetingAdapter(this, fMeetings, mTwoPane));
    }
}
