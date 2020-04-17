package graphical.wireless.espace.ui.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.core.content.ContextCompat;

import graphical.wireless.espace.R;

public class FavouriteButton extends ToggleButton {

    public FavouriteButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupFavouriteButton();
    }

    public FavouriteButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupFavouriteButton();
    }

    public FavouriteButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupFavouriteButton();
    }

    public FavouriteButton(Context context) {
        super(context);
        setupFavouriteButton();
    }

    private final void setupFavouriteButton() {
        setTextOn("");
        setTextOff("");
        setText("");

        // The favourite star icon and toggling it
        setChecked(false);
        setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favourite_empty));
        setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    setBackgroundDrawable(ContextCompat.getDrawable(getContext(),R.drawable.ic_favourite_filled));
                else
                    setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_favourite_empty));
            }
        });
    }
}
