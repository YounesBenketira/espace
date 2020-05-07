package graphical.wireless.espace.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import graphical.wireless.espace.R;

public class WelcomeFragment extends Fragment {

    public WelcomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View temp = inflater.inflate(R.layout.fragment_welcome, container, false);
        return temp;
    }
}
