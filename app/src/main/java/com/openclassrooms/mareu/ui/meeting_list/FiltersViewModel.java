package com.openclassrooms.mareu.ui.meeting_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class FiltersViewModel extends ViewModel {

    /**
     * Live Data Instance
     */
    private MutableLiveData<String> mName = new MutableLiveData<>();

    public LiveData<String> getName() {
        return mName;
    }

    public void setName(String name) {
        mName.setValue(name);
    }

}
