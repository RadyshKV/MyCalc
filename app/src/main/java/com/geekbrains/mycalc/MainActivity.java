package com.geekbrains.mycalc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import static com.geekbrains.mycalc.Constants.*;

public class MainActivity extends AppCompatActivity {
    private TextView resultView;
    private CalculatorState calculatorState = new CalculatorState();
    private final int[] numberButtonIds = new int[]{R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};
    private int currentCodeTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(getAppTheme(skyTheme));
        setContentView(R.layout.activity_main);
        initViews();
        setOnButtonsClickListener();
    }


    private void initViews() {
        resultView = findViewById(R.id.resultView);
    }

    private void setNumberButtonListeners() {
        for (int i = 0; i < numberButtonIds.length; i++) {
            int index = i;
            findViewById(numberButtonIds[i]).setOnClickListener(v -> resultView.setText(calculatorState.setNumber(index)));
        }
    }

    private void setOnButtonsClickListener() {
        setNumberButtonListeners();
        findViewById(R.id.button_clear).setOnClickListener(v -> resultView.setText(calculatorState.clear()));
        findViewById(R.id.button_back).setOnClickListener(v -> resultView.setText(calculatorState.backspace()));
        findViewById(R.id.button_percent).setOnClickListener(v -> resultView.setText(calculatorState.setOperation(PERCENT)));
        findViewById(R.id.button_point).setOnClickListener(v -> resultView.setText(calculatorState.setPoint()));
        findViewById(R.id.button_sum).setOnClickListener(v -> resultView.setText(calculatorState.setOperation(SUMMATION)));
        findViewById(R.id.button_sub).setOnClickListener(v -> resultView.setText(calculatorState.setOperation(SUBTRACTION)));
        findViewById(R.id.button_mult).setOnClickListener(v -> resultView.setText(calculatorState.setOperation(MULTIPLICATION)));
        findViewById(R.id.button_div).setOnClickListener(v -> resultView.setText(calculatorState.setOperation(DIVISION)));
        findViewById(R.id.button_negative).setOnClickListener(v -> resultView.setText(calculatorState.setNegative()));
        findViewById(R.id.button_equals).setOnClickListener(v -> resultView.setText(calculatorState.calculate()));
        findViewById(R.id.button_selectTheme).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChangeThemeActivity.class);
            intent.putExtra(mainActivityDataKey, currentCodeTheme);
            startActivityForResult(intent, changeThemeActivityRequestCode);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(calculatorStateKey, calculatorState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        calculatorState = savedInstanceState.getParcelable(calculatorStateKey);
        resultView.setText(calculatorState.getResult());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == changeThemeActivityRequestCode && resultCode == RESULT_OK && data != null) {
            int codeTheme = data.getIntExtra(changeThemeActivityDataKey, skyTheme);
            setAppTheme(codeTheme);
            recreate();
        }
    }

    private int getAppTheme(int codeTheme) {
        return codeThemeToThemeId(getCodeTheme(codeTheme));
    }

    private int getCodeTheme(int codeTheme) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        currentCodeTheme = sharedPref.getInt(appTheme, codeTheme);
        return currentCodeTheme;
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

    private void setAppTheme(int codeTheme) {
        SharedPreferences sharedPref = getSharedPreferences(NameSharedPreference, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(appTheme, codeTheme);
        editor.apply();
    }
}