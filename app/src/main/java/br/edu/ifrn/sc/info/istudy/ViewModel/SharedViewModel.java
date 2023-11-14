package br.edu.ifrn.sc.info.istudy.ViewModel;

import android.os.Bundle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Bundle> sharedBundle = new MutableLiveData<>();

    public void setSharedBundle(Bundle bundle) {
        sharedBundle.setValue(bundle);
    }

    public LiveData<Bundle> getSharedBundle() {
        return sharedBundle;
    }
}