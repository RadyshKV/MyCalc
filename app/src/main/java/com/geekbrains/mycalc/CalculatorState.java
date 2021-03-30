package com.geekbrains.mycalc;

import android.os.Parcel;
import android.os.Parcelable;

import static com.geekbrains.mycalc.MainActivity.*;

public class CalculatorState implements Parcelable {
    private Double result;   // результат вычислений
    private StringBuilder operand;  // операнд
    private char lastOperation; // последняя операция

    public CalculatorState() {
        result = 0.0d;
        operand = new StringBuilder("0");
        lastOperation = noOperation;
    }

    protected CalculatorState(Parcel in) {
        if (in.readByte() == 0) {
            result = null;
        } else {
            result = in.readDouble();
        }
        lastOperation = (char) in.readInt();
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

    public Double getResult() {
        return result;
    }

    public String getOperand() {
        return operand.toString();
    }

    public char getLastOperation() {
        return lastOperation;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public void setOperand(String operand) {
        this.operand.setLength(0);
        this.operand.append(operand);
    }

    public void setLastOperation(char lastOperation) {
        this.lastOperation = lastOperation;
    }

    public void backspace() {
        if (operand.length() > 1) {
            operand.setLength(operand.length() - 1);
        } else {
            operand.setLength(0);
            operand.append(0);
        }
    }

    public void setPoint() {
        if (operand.length() > 0 && operand.lastIndexOf(".") == -1) {
            operand.append(".");
        }
    }

    public void setNumber(int number) {
        operand = operand.append(number);
        if (operand.charAt(0) == '0' && operand.charAt(1) != '.') {
            operand.deleteCharAt(0);
        }
    }

    public void setNegative() {
        if (operand.charAt(0) == '-') {
            operand.deleteCharAt(0);
        } else {
            operand.insert(0, '-');
        }
    }


    public void setEqualsOperation() {

        double curValue = Double.parseDouble(operand.toString());
        switch (lastOperation) {
            case percentOperation:
                result = curValue / 100 * result;
                break;
            case sumOperation:
                result += curValue;
                break;
            case subOperation:
                result -= curValue;
                break;
            case multOperation:
                result *= curValue;
                break;
            case divOperation:
                result /= curValue;
                break;
            case noOperation:
                result = curValue;
                break;
        }
        operand.setLength(0);
        operand.append(result);
        lastOperation = equalsOperation;
    }

    public void setOperation(char operation) {
        lastOperation = operation;
        result = Double.parseDouble(operand.toString());
        operand.setLength(0);
        operand.append(0);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (result == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(result);
        }
        dest.writeInt((int) lastOperation);
    }

    public boolean lastOperationIsEquals() {
        return lastOperation == equalsOperation;
    }
}
