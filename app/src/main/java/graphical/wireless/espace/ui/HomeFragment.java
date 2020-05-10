package graphical.wireless.espace.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import graphical.wireless.espace.R;
import graphical.wireless.espace.ui.data.database.CardData;
import graphical.wireless.espace.ui.data.database.CardListAdapter;
import graphical.wireless.espace.ui.data.database.CardViewModel;

public class HomeFragment extends Fragment {
    private CardListAdapter mAdapter;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View temp = inflater.inflate(R.layout.fragment_home, container, false);

//        Log.i(TAG, "onCreateView: ");
        RecyclerView recyclerView = temp.findViewById(R.id.home_recyclerView);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(temp.getContext()));

        // specify an adapter
        mAdapter = new CardListAdapter(getContext());
        recyclerView.setAdapter(mAdapter);
        final CardViewModel model = new CardViewModel(getActivity().getApplication());
        subscribeToModel(model);

        return temp;
    }

    private void subscribeToModel(final CardViewModel model) {
        model.getPotdData().observe(getViewLifecycleOwner(), new Observer<List<CardData>>() {
            @Override
            public void onChanged(List<CardData> cardData) {
                if(cardData != null) {
                    mAdapter.setCardData(cardData);
                }
            }
        });
    }
}


