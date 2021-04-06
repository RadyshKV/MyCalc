package com.geekbrains.mycalc;

import android.os.Parcel;
import android.os.Parcelable;

import static com.geekbrains.mycalc.Constants.*;

public class CalculatorState implements Parcelable {
    private String firstOperand;   // первый операнд
    private String secondOperand;  // второй операнд
    private char lastOperation; // последняя операция

    public CalculatorState() {

        firstOperand = "";
        secondOperand = "";
        lastOperation = EMPTY;
    }


    protected CalculatorState(Parcel in) {
        firstOperand = in.readString();
        secondOperand = in.readString();
        lastOperation = (char) in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstOperand);
        dest.writeString(secondOperand);
        dest.writeInt((int) lastOperation);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CalculatorState> CREATOR = new Creator<CalculatorState>() {
        @Override
        public CalculatorState createFromParcel(Parcel in) {
            return new CalculatorState(in);
        }

        @Override
        public CalculatorState[] newArray(int size) {
            return new CalculatorState[size];
        }
    };

    public String clear() {
        lastOperation = EMPTY;
        secondOperand = "";
        firstOperand = "";
        return getResult();
    }

    public String backspace() {
        if (!secondOperand.isEmpty()) {
            secondOperand = secondOperand.substring(0, secondOperand.length() - 1);
        } else {
            lastOperation = EMPTY;
        }
        return getResult();
    }

    public String setPoint() {
        if (lastOperation == EMPTY) {
            firstOperand = "";
        }
        if (secondOperand.lastIndexOf(".") == -1) {
            if (secondOperand.isEmpty()) {
                secondOperand += "0";
            }
            secondOperand += ".";
        }
        return getResult();
    }

    public String setNumber(int number) {
        if (lastOperation == EMPTY) {
            firstOperand = "";
        }
        if (secondOperand.equals("0")) {
            secondOperand = String.format("%s", number);
        } else {
            secondOperand += number;
        }
        return getResult();
    }

    public String setNegative() {
        if (!secondOperand.isEmpty() && !secondOperand.equals("0")) {
            if (secondOperand.startsWith("-")) {
                secondOperand = secondOperand.replace("-", "");
            } else {
                secondOperand = "-" + secondOperand;
            }
        }
        return getResult();
    }


    public String calculate() {
        double firstNumber;
        double secondNumber;

        if (firstOperand.isEmpty()) {
            firstNumber = 0.0d;
        } else {
            firstNumber = Double.parseDouble(firstOperand);
        }

        if (secondOperand.isEmpty()) {
            secondNumber = firstNumber;
        } else {
            secondNumber = Double.parseDouble(secondOperand);
        }

        switch (lastOperation) {
            case PERCENT:
                firstNumber = secondNumber / 100 * firstNumber;
                break;
            case SUMMATION:
                firstNumber += secondNumber;
                break;
            case SUBTRACTION:
                firstNumber -= secondNumber;
                break;
            case MULTIPLICATION:
                firstNumber *= secondNumber;
                break;
            case DIVISION:
                firstNumber /= secondNumber;
                break;
            case EMPTY:
                firstNumber = secondNumber;
                break;
        }
        firstOperand = "";
        firstOperand += firstNumber;
        secondOperand = "";
        lastOperation = EMPTY;

        return getResult();
    }

    public String setOperation(char operation) {
        if (firstOperand.isEmpty()) {
            firstOperand = secondOperand;
            secondOperand = "";
        } else {
            calculate();
        }
        lastOperation = operation;
        return getResult();
    }


    public String getResult() {
        return firstOperand + lastOperation + secondOperand;
    }


}
