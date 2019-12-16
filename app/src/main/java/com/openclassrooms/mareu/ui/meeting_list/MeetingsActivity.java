package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.ui.meeting_list.filters.FilterListFragment;
import com.openclassrooms.mareu.ui.meeting_list.util.FilterContent;

public class MeetingsActivity extends AppCompatActivity implements FilterListFragment.DialogListener, FilterListFragment.OnListFragmentInteractionListener, FilterListFragment.OnPlaceFragmentInteractionListener {

    private FloatingActionButton fab;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        initMeetingsView();
    }

    private void initMeetingsView() {
        Fragment newFragment = new MeetingsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.add(R.id.content_meeting, newFragment);
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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


    @Override
    public void onFinishEditDialog(String inputText) {
/*
        if (TextUtils.isEmpty(inputText)) {
            textView.setText("Email was not entered");
        } else
            textView.setText("Email entered: " + inputText);*/
    }

    public void onListFragmentInteraction(FilterContent.FiltersItem item) {

        Toast.makeText(getApplicationContext(), "Hello Filter", Toast.LENGTH_SHORT).show();


    }

    public void onPlaceFragmentInteraction(FilterContent.Places places, Boolean isSelected) {
        //TODO
        Toast.makeText(getApplicationContext(), "Hello Places", Toast.LENGTH_SHORT).show();

    }
}
