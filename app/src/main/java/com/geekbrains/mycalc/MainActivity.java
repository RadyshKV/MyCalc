package com.geekbrains.mycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView resultView;
    public static final char percentOperation = '%';
    public static final char sumOperation = '+';
    public static final char subOperation = '-';
    public static final char multOperation = '*';
    public static final char divOperation = '/';
    public static final char equalsOperation = '=';
    public static final char noOperation = 'n';
    private final String calculatorStateKey = "calculatorStateKey";
    private CalculatorState calculatorState = new CalculatorState();
    private final int[] numberButtonIds = new int[]{R.id.button_0, R.id.button_1, R.id.button_2, R.id.button_3, R.id.button_4, R.id.button_5, R.id.button_6, R.id.button_7, R.id.button_8, R.id.button_9};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            findViewById(numberButtonIds[i]).setOnClickListener(v -> onNumberClick(index));
        }
    }

    private void setOnButtonsClickListener() {
        setNumberButtonListeners();
        findViewById(R.id.button_clear).setOnClickListener(v -> onClearClick());
        findViewById(R.id.button_back).setOnClickListener(v -> onBackClick());
        findViewById(R.id.button_percent).setOnClickListener(v -> onOperationClick(percentOperation));
        findViewById(R.id.button_point).setOnClickListener(v -> onPointClick());
        findViewById(R.id.button_sum).setOnClickListener(v -> onOperationClick(sumOperation));
        findViewById(R.id.button_sub).setOnClickListener(v -> onOperationClick(subOperation));
        findViewById(R.id.button_mult).setOnClickListener(v -> onOperationClick(multOperation));
        findViewById(R.id.button_div).setOnClickListener(v -> onOperationClick(divOperation));
        findViewById(R.id.button_negative).setOnClickListener(v -> onNegativeClick());
        findViewById(R.id.button_equals).setOnClickListener(v -> onOperationClick(equalsOperation));
    }


    private void onNumberClick(int number) {
        calculatorState.setNumber(number);
        resultView.setText(calculatorState.getOperand());
    }

    private void onOperationClick(char operation) {
        if (operation == equalsOperation) {
            calculatorState.setEqualsOperation();
            resultView.setText(calculatorState.getOperand());
        } else {
            calculatorState.setOperation(operation);
        }
    }


    private void onPointClick() {
        if (!calculatorState.lastOperationIsEquals()) {
            calculatorState.setPoint();
            resultView.setText(calculatorState.getOperand());
        }
    }

    private void onBackClick() {
        if (!calculatorState.lastOperationIsEquals()) {
            calculatorState.backspace();
            resultView.setText(calculatorState.getOperand());
        }
    }

    private void onClearClick() {
        calculatorState.setLastOperation(noOperation);
        calculatorState.setOperand("0");
        calculatorState.setResult(0.0d);
        resultView.setText(calculatorState.getOperand());
    }

    private void onNegativeClick() {
        calculatorState.setNegative();
        resultView.setText(calculatorState.getOperand());
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
        resultView.setText(calculatorState.getOperand());
    }
}