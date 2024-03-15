package com.projectapps.calculatorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;




import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button zero
            ,one,two,three
            ,four,five,six
            ,seven,eight,nine
            ,point,equal,plus
            ,minus,multi,dive
            ,percent,plusminus,c;

    TextView resultNumber;
    String toCalculate ="";
    boolean equalFlag = false;
    boolean operatorOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultNumber = findViewById(R.id.resultNumber);

        bindButtonId(zero,R.id.buttonZero);
        bindButtonId(one,R.id.buttonOne);
        bindButtonId(two,R.id.buttonTwo);
        bindButtonId(three,R.id.buttonThree);
        bindButtonId(four,R.id.buttonFour);
        bindButtonId(five,R.id.buttonFive);
        bindButtonId(six,R.id.buttonSix);
        bindButtonId(seven,R.id.buttonSeven);
        bindButtonId(eight,R.id.buttonEight);
        bindButtonId(nine,R.id.buttonNine);
        bindButtonId(point,R.id.buttonPoint);
        bindButtonId(equal,R.id.buttonEqual);
        bindButtonId(plus,R.id.buttonPlus);
        bindButtonId(minus,R.id.buttonMinus);
        bindButtonId(multi,R.id.buttonMulti);
        bindButtonId(dive,R.id.buttonDiv);
        bindButtonId(percent,R.id.buttonPercent);
        bindButtonId(plusminus,R.id.buttonPlusMinus);
        bindButtonId(c,R.id.buttonC);
    }

    @Override
    public void onClick(View v) {
        Button btn = findViewById(v.getId());
        String buttonTxt = btn.getText().toString();
        String numberToDisplay = resultNumber.getText().toString();
        double tempNum = 0;
        boolean operatorButtonClicked = (buttonTxt.equals("+") || buttonTxt.equals("-") || buttonTxt.equals("*") || buttonTxt.equals("/"));


        //CANCEL BUTTON CLICKED
        if(buttonTxt.equals("C")){
            resetAll();
        }
        else if (buttonTxt.equals("+/-")) {
            if(!numberToDisplay.isEmpty()) {
                if (numberToDisplay.charAt(0) == '-') {
                    numberToDisplay = numberToDisplay.substring(1);
                } else {
                    numberToDisplay = "-" + numberToDisplay;
                }
                resultNumber.setText(numberToDisplay);
                toCalculate = numberToDisplay;
            }
        }
        //EQUAL BUTTON CLICKED
        else if(buttonTxt.equals("=")){
            toCalculate = toCalculate + numberToDisplay;
            numberToDisplay = getResult(toCalculate);
            if(numberToDisplay.endsWith(".0")){
                numberToDisplay = numberToDisplay.replace(".0","");
            }
            resultNumber.setText(numberToDisplay);
            equalFlag = true;
        }
        //PERCENT BUTTON CLICKED
        else if (buttonTxt.equals("%")) {
                if(!numberToDisplay.isEmpty()) {
                    tempNum = Double.parseDouble(numberToDisplay);
                    numberToDisplay = "" + tempNum / 100;
                    resultNumber.setText(numberToDisplay);
                }

        }
        //OPERATOR IS CLICKED (+,-,/,*)
        else if(operatorButtonClicked){
            if(!operatorOn) {
                operatorOn = true;
                if(equalFlag){
                    equalFlag = false;
                    toCalculate = "("+toCalculate+ ")" +buttonTxt;
                }else {
                    toCalculate = toCalculate + numberToDisplay + buttonTxt;
                }
            }

        }
        else {
            if (!operatorOn && !equalFlag) {
                numberToDisplay = numberToDisplay + buttonTxt;
                resultNumber.setText(numberToDisplay);
            } else {
                if(equalFlag){
                    resetAll();
                }
                operatorOn = false;
                resultNumber.setText(buttonTxt);
            }
        }
    }


    private void bindButtonId(Button btn,int id){
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    private String getResult(String string){
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, string, "Javascript", 1, null).toString();
            return finalResult;
        }catch (Exception e){
            return "Error";
        }
    }

    private void resetAll(){
        toCalculate ="";
        resultNumber.setText("");
        operatorOn = false;
        equalFlag = false;
    }

    private void equalBtnClicked(){

    }


}







