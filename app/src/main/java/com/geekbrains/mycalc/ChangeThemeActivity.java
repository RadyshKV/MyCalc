package com.geekbrains.mycalc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.radiobutton.MaterialRadioButton;

import static com.geekbrains.mycalc.Constants.changeThemeActivityDataKey;
import static com.geekbrains.mycalc.Constants.codeThemeKey;
import static com.geekbrains.mycalc.Constants.darkTheme;
import static com.geekbrains.mycalc.Constants.lightTheme;
import static com.geekbrains.mycalc.Constants.mainActivityDataKey;
import static com.geekbrains.mycalc.Constants.skyTheme;

public class ChangeThemeActivity extends AppCompatActivity {

    private int currentCodeTheme;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCurrentCodeTheme(savedInstanceState);
        setTheme(codeThemeToThemeId(currentCodeTheme));
        setContentView(R.layout.activity_change_theme);
        initThemeChooser();
        setOnBtnListener();
    }

    private void initCurrentCodeTheme(Bundle savedInstanceState) {
        if (savedInstanceState!=null) {
            currentCodeTheme = savedInstanceState.getInt(codeThemeKey);
        } else {
            Intent intent = getIntent();
            currentCodeTheme = intent.getIntExtra(mainActivityDataKey, skyTheme);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(codeThemeKey, currentCodeTheme);
        super.onSaveInstanceState(outState);
    }

    private void setOnBtnListener() {
        findViewById(R.id.applyButton).setOnClickListener(v -> {
            Intent intent = getIntent();
            intent.putExtra(changeThemeActivityDataKey, currentCodeTheme);
            setResult(RESULT_OK, intent);
            finish();
        });
        findViewById(R.id.cancelButton).setOnClickListener(v -> {
            Intent intent = getIntent();
            setResult(RESULT_CANCELED, intent);
            finish();
        });
    }

    private void initThemeChooser() {
        initRadioButton(findViewById(R.id.radioButtonDarkTheme), darkTheme);
        initRadioButton(findViewById(R.id.radioButtonLightTheme), lightTheme);
        initRadioButton(findViewById(R.id.radioButtonSkyTheme), skyTheme);
        RadioGroup rg = findViewById(R.id.radioGroup);
        ((MaterialRadioButton)rg.getChildAt(currentCodeTheme)).setChecked(true);
    }

    private void initRadioButton(View radioButton, int codeTheme) {
        radioButton.setOnClickListener(v -> {
            setCurrentCodeTheme(codeTheme);
            recreate();
        });
    }

    private void setCurrentCodeTheme(int codeTheme) {
        currentCodeTheme = codeTheme;
    }

    private int codeThemeToThemeId(int codeTheme) {
        switch (codeTheme) {
            case darkTheme:
                return R.style.Theme_MyCalc_Dark;
            case lightTheme:
                return R.style.Theme_MyCalc_Light;
            case skyTheme:
            default:
                return R.style.Theme_MyCalc_Sky;
        }
    }
}