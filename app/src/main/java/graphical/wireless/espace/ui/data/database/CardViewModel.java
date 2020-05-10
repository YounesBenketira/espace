package graphical.wireless.espace.ui.data.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class CardViewModel extends AndroidViewModel {
    private CardDataRepository mRepository;

    private LiveData<List<CardData>> mAllCardData;
    private LiveData<List<CardData>> mPotdData;

    public CardViewModel (Application application) {
        super(application);
        mRepository = new CardDataRepository(application);
        mAllCardData = mRepository.getAllCardData();
        mPotdData = mRepository.getPotdData();
    }

    public LiveData<List<CardData>> getAllCardData() { return mAllCardData; }
    public LiveData<List<CardData>> getPotdData() { return mPotdData; }

    public boolean containsFavourite(CardData card) {
        return mAllCardData.getValue().contains(card);
    }

    public void insert(CardData card) { mRepository.insert(card); }
}
