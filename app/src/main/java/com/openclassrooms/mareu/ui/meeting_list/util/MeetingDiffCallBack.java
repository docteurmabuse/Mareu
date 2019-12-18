package com.openclassrooms.mareu.ui.meeting_list.util;

import androidx.recyclerview.widget.DiffUtil;

import com.openclassrooms.mareu.model.Meeting;

import java.util.List;

public class MeetingDiffCallBack extends DiffUtil.Callback {
    private final List<Meeting> oldMeetings;
    private final List<Meeting> newMeetings;

    public MeetingDiffCallBack(List<Meeting> oldMeetings, List<Meeting> newMeetings) {
        this.oldMeetings = oldMeetings;
        this.newMeetings = newMeetings;
    }

    @Override
    public int getOldListSize() {
        return oldMeetings.size();
    }

    @Override
    public int getNewListSize() {
        return newMeetings.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMeetings.get(oldItemPosition).getmId() == newMeetings.get(newItemPosition).getmId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldMeetings.get(oldItemPosition).equals(newMeetings.get(newItemPosition));
    }
}
