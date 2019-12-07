package com.openclassrooms.mareu.ui.meeting_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;
import com.openclassrooms.mareu.events.DeleteMeetingEvent;
import com.openclassrooms.mareu.model.Meeting;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MeetingRecyclerViewAdapter
        extends RecyclerView.Adapter<MeetingListViewHolder> {

    private final ItemListActivity mParentActivity;
    private final List<Meeting> mMeetings;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Meeting meeting = (Meeting) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putSerializable(ItemDetailFragment.ARG_ITEM_ID, meeting);
                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Bundle arguments = new Bundle();
                arguments.putSerializable(ItemDetailFragment.ARG_ITEM_ID, meeting);
                Intent intent = new Intent(context, ItemDetailActivity.class);
                intent.putExtras(arguments);
                context.startActivity(intent);
            }
        }
    };

    MeetingRecyclerViewAdapter(ItemListActivity parent,
                               List<Meeting> meetings,
                               boolean twoPane) {
        mMeetings = meetings;
        mParentActivity = parent;
        mTwoPane = twoPane;
    }

    @NonNull
    @Override
    public MeetingListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_content, parent, false);
        return new MeetingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MeetingListViewHolder holder, int position) {
        final Meeting meeting = mMeetings.get(position);
        String Title = mMeetings.get(position).getmSubject() + " - " + mMeetings.get(position).getmTime() + " - " + mMeetings.get(position).getmPlace();
        String Subtitle = meeting.getmParticipants();
        holder.mAvatarView.setCardBackgroundColor(meeting.getmAvatar());
        holder.mSubtitleView.setText(Subtitle);
        holder.mTitleView.setText(Title);
        holder.itemView.setTag(mMeetings.get(position));
        holder.itemView.setOnClickListener(mOnClickListener);
        holder.mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetings.size();
    }
}
