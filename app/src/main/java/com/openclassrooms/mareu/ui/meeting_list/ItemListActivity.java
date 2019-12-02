package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.mareu.di.DI;
import com.openclassrooms.mareu.dummy.DummyContent;
import com.openclassrooms.mareu.model.Meeting;
import com.openclassrooms.mareu.service.MeetingApiService;

import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private List<Meeting> meetings;
    private MeetingApiService mApiService;
    private RecyclerView mRecyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = findViewById(R.id.fab);
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

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        initList();
    }

    private void initList() {
        meetings = mApiService.getMeetings();
        mRecyclerView.setAdapter(new MeetingRecyclerViewAdapter(this, meetings, mTwoPane));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MeetingRecyclerViewAdapter(this, meetings, mTwoPane));
    }

    public static class MeetingRecyclerViewAdapter
            extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<Meeting> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);

                    context.startActivity(intent);
                }
            }
        };

        MeetingRecyclerViewAdapter(ItemListActivity parent,
                                   List<Meeting> items,
                                   boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mIdView.setText(mValues.get(position).getmId());
            holder.mContentView.setText(mValues.get(position).getmSubject());

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final TextView mContentView;
            final ImageView mDeleteButton;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.item_list_meeting_subtitle);
                mContentView = view.findViewById(R.id.item_list_meeting_title);
                mDeleteButton = view.findViewById(R.id.item_list_meeting_delete_button);
            }
        }
    }
}
