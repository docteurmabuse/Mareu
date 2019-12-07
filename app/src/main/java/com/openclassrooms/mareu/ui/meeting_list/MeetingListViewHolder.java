package com.openclassrooms.mareu.ui.meeting_list;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.mareu.R;

class MeetingListViewHolder extends RecyclerView.ViewHolder {
    final CardView mAvatarView;
    final TextView mSubtitleView;
    final TextView mTitleView;
    final ImageView mDeleteButton;

    MeetingListViewHolder(@NonNull View view) {
        super(view);
        mAvatarView = view.findViewById(R.id.item_list_meeting_avatar);
        mSubtitleView = view.findViewById(R.id.item_list_meeting_subtitle);
        mTitleView = view.findViewById(R.id.item_list_meeting_title);
        mDeleteButton = view.findViewById(R.id.item_list_meeting_delete_button);
    }
}


