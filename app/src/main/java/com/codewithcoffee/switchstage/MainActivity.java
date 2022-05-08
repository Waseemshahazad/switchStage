package com.codewithcoffee.switchstage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {
    LinearLayout layout;
    TextView textView;
    SwitchMaterial switchTheme;
    private UserSetings setings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.linearLayout);
        textView = findViewById(R.id.textView);
        switchTheme = findViewById(R.id.switchTheme);

        setings = (UserSetings) getApplication();

        loadSharedPreferences();
        initSwitchListener();
        updateView();
    }


    private void loadSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(UserSetings.PREFERENCES,MODE_PRIVATE);
        String theme = sharedPreferences.getString(UserSetings.CUSTOM_THEME, UserSetings.LIGHT_THEME);
        setings.setCustomTheme(theme);
    }

    private void initSwitchListener() {
        switchTheme.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b)
                    setings.setCustomTheme(UserSetings.DARK_THEME);
                else
                    setings.setCustomTheme(UserSetings.LIGHT_THEME);
                SharedPreferences.Editor editor = getSharedPreferences(UserSetings.PREFERENCES,MODE_PRIVATE).edit();
                editor.putString(UserSetings.CUSTOM_THEME, setings.getCustomTheme());
                editor.apply();
                updateView();
            }
        });
    }

    private void updateView() {
        final int black = ContextCompat.getColor(this,R.color.black);
        final int white = ContextCompat.getColor(this,R.color.white);

        if (setings.getCustomTheme().equals(UserSetings.DARK_THEME)){
            textView.setText("Dark");
            textView.setTextColor(white);
            layout.setBackgroundColor(black);
            switchTheme.setChecked(true);
        }else
        {
            textView.setText("Light");
            textView.setTextColor(black);
            layout.setBackgroundColor(white);
            switchTheme.setChecked(false);
        }
    }

}