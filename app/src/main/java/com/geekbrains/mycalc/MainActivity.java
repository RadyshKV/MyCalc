package com.geekbrains.mycalc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView resultView;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button button_5;
    private Button button_6;
    private Button button_7;
    private Button button_8;
    private Button button_9;
    private Button button_0;
    private Button button_clear;
    private Button button_back;
    private Button button_percent;
    private Button button_point;
    private Button button_sum;
    private Button button_sub;
    private Button button_mult;
    private Button button_div;
    private Button button_negative;
    private Button button_equals;
    public static final char percentOperation = '%';
    public static final char sumOperation = '+';
    public static final char subOperation = '-';
    public static final char multOperation = '*';
    public static final char divOperation = '/';
    public static final char equalsOperation = '=';
    public static final char noOperation = 'n';
    private final String calculatorStateKey = "calculatorStateKey";
    private CalculatorState calculatorState = new CalculatorState();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setOnButtonsClickListener();

    }

    private void initViews() {
        resultView = findViewById(R.id.resultView);
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_4 = findViewById(R.id.button_4);
        button_5 = findViewById(R.id.button_5);
        button_6 = findViewById(R.id.button_6);
        button_7 = findViewById(R.id.button_7);
        button_8 = findViewById(R.id.button_8);
        button_9 = findViewById(R.id.button_9);
        button_0 = findViewById(R.id.button_0);
        button_clear = findViewById(R.id.button_clear);
        button_back = findViewById(R.id.button_back);
        button_percent = findViewById(R.id.button_percent);
        button_point = findViewById(R.id.button_point);
        button_sum = findViewById(R.id.button_sum);
        button_sub = findViewById(R.id.button_sub);
        button_mult = findViewById(R.id.button_mult);
        button_div = findViewById(R.id.button_div);
        button_negative = findViewById(R.id.button_negative);
        button_equals = findViewById(R.id.button_equals);
    }

    private void setOnButtonsClickListener() {
        button_1.setOnClickListener(v -> onNumberClick(1));
        button_2.setOnClickListener(v -> onNumberClick(2));
        button_3.setOnClickListener(v -> onNumberClick(3));
        button_4.setOnClickListener(v -> onNumberClick(4));
        button_5.setOnClickListener(v -> onNumberClick(5));
        button_6.setOnClickListener(v -> onNumberClick(6));
        button_7.setOnClickListener(v -> onNumberClick(7));
        button_8.setOnClickListener(v -> onNumberClick(8));
        button_9.setOnClickListener(v -> onNumberClick(9));
        button_0.setOnClickListener(v -> onNumberClick(0));
        button_clear.setOnClickListener(v -> onClearClick());
        button_back.setOnClickListener(v -> onBackClick());
        button_percent.setOnClickListener(v -> onOperationClick(percentOperation));
        button_point.setOnClickListener(v -> onPointClick());
        button_sum.setOnClickListener(v -> onOperationClick(sumOperation));
        button_sub.setOnClickListener(v -> onOperationClick(subOperation));
        button_mult.setOnClickListener(v -> onOperationClick(multOperation));
        button_div.setOnClickListener(v -> onOperationClick(divOperation));
        button_negative.setOnClickListener(v -> onNegativeClick());
        button_equals.setOnClickListener(v -> onOperationClick(equalsOperation));
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
        if (calculatorState.getLastOperation() != equalsOperation) {
            calculatorState.setPoint();
            resultView.setText(calculatorState.getOperand());
        }
    }

    private void onBackClick() {
        if (calculatorState.getLastOperation() != equalsOperation) {
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