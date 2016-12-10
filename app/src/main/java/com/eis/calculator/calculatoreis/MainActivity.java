package com.eis.calculator.calculatoreis;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

public class MainActivity extends Activity {
    private EditText console;
    private String display = "";
    private String currentOperator = "";
    private String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        console = (EditText) findViewById(R.id.EditText);
        console.setText(display);


    }
    //https://tanzil_taqi@bitbucket.org/tanzil_taqi/calculatoreis.git

    public void HideSoftKeyboard(View v) {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(console.getWindowToken(), 0);
    }




    private void updateconsole() {   //Updates your design in the Template Design View.
        console.setText(display);
    }

    public void onClickNumber(View v) { //onclick to press the numbers
        if (result != "") {
            clear();
            updateconsole();
        }
        Button b = (Button) v;
        display += b.getText(); //button will set the text to console
        updateconsole();
    }

    private boolean isOperator(char op) {
        switch (op) {
            case '+':                               //operator fucntion switch case
            case '-':
            case 'x':
            case '%':
            case '÷':
                return true;
            default:
                return false;
        }
    }

    public void onClickOperator(View v) {           //function for clicking the operators
        if (display == "") return;

        Button b = (Button) v;

        if (result != "") {
            String _display = result;
            clear();
            display = _display;
        }

        if (currentOperator != "") {
            Log.d("CalcX", "" + display.charAt(display.length() - 1));
            if (isOperator(display.charAt(display.length() - 1))) {
                display = display.replace(display.charAt(display.length() - 1),
                        b.getText().charAt(0));
                updateconsole();
                return;
            } else {

                System.out.println((getResult()));
                display = result;
                result = "";
            }
            currentOperator = b.getText().toString();
        }
        display += b.getText();
        currentOperator = b.getText().toString();
        updateconsole();
    }

    private void clear() {          //clear the whole console
        display = "";
        currentOperator = "";
        result = "";
    }

//    public void onClickErase(View v) {        //Erase one last digit from cursor
////        String s = console.getText().toString();
////        s = s.substring(0, s.length() - 1);
////        display = s;
////        console.setText(s);
//
//
//        if (console.getText().toString().length() > 1) {
//            int offset=console.getSelectionStart();
//            StringBuffer buffer=new StringBuffer(console.getText().toString());
//            try {
//                buffer=buffer.deleteCharAt(offset-1);
//                console.setText(buffer.toString());
//                display = buffer.toString();
//            } catch (Exception e) {
//                //e.printStackTrace();
//            }
//            /*String console_new = console.getText().toString().substring(0, console.getText().toString().length() - 1);
//            //console_new=console_new.re
//            console.setText(console_new);
//            display = console_new;*/
//        } else {
//            console.setText("");
//            display = "";
//            Toast.makeText(getApplicationContext(),
//                    "Invalid!!", Toast.LENGTH_LONG).show();
//
//        }
//
//
//    }


    public void onClickErase(View v) {
        //Erase one last digit from cursor
//        String s = console.getText().toString();
//        s = s.substring(0, s.length() - 1);
//        display = s;
//        console.setText(s);


        if (console.getText().toString().length() > 1) {
            /*String console_new = console.getText().toString().substring(0, console.getText().toString().length() - 1);
            console.setText(console_new);
            display = console_new;*/
            try {
                StringBuffer stringBuffer = new StringBuffer(console.getText().toString());
                int position = console.getSelectionStart();
                stringBuffer = stringBuffer.deleteCharAt(position - 1);
                console.setText(stringBuffer.toString());
                display = stringBuffer.toString();
            } catch (Exception e) {

            }

        } else {

            console.setText("");
            display = "";
            Toast.makeText(getApplicationContext(),
                    "Invalid!!", Toast.LENGTH_LONG).show();
            Vibrator mvib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            mvib.vibrate(300);


        }
    }


    public void onClickClear(View v) {      //onclick button function for clear console
        clear();
        updateconsole();
    }


    private BigDecimal operate(String a, String b, String op) {     //operator case condition
        switch (op) {
            case "+":
                return new BigDecimal(a).add(new BigDecimal(b)).stripTrailingZeros();
            case "-":
                return new BigDecimal(a).subtract(new BigDecimal(b)).stripTrailingZeros();
            case "*":
                return new BigDecimal(a).multiply(new BigDecimal(b)).stripTrailingZeros();

            case "%":
                return new BigDecimal(a).multiply(new BigDecimal(b).divide(new BigDecimal("100"), 8, RoundingMode.HALF_UP)).stripTrailingZeros();

            case "/":
                try {
                    return new BigDecimal(a).divide(new BigDecimal(b), 8, RoundingMode.HALF_UP).stripTrailingZeros();
                } catch (Exception e) {
                    Log.d("Calc", e.getMessage());
                    Toast.makeText(getApplicationContext(),
                            "Invalid!!", Toast.LENGTH_LONG).show();
                }
            default:
                return BigDecimal.valueOf(-1);


        }
    }




/*
    private double operate(String a, String b, String op) {     //operator case condition
        System.out.println(a + "  " +b+ "  "+op);
        switch (op) {
            case "+":return Double.valueOf(a) + Double.valueOf(b);
            case "-":
                return Double.valueOf(a) - Double.valueOf(b);
            case "*":
                return Double.valueOf(a) * Double.valueOf(b);
            case "%":
                return Double.valueOf(a) % Double.valueOf(b);
            case "/":
                try {
                    return Double.valueOf(a) / Double.valueOf(b);
                } catch (Exception e) {
                    Log.d("Calc", e.getMessage());
                }
            default:
                return -1.0d;


        }
    }

*/

 /*   private boolean getResult() {       //getting output
        if (currentOperator == "") return false;
        String[] operation = display.split(Pattern.quote(currentOperator));
        if (operation.length < 2) return false;
        result = operate(operation[0], operation[1], currentOperator).stripTrailingZeros().toPlainString();
        try {

        }catch (Exception e){

        }

        return true;

    } */

    private boolean getResult() {       //getting output
        String[] operation = display.split(Pattern.quote(currentOperator));
        try {
            BigDecimal bigDecimal1 = new BigDecimal(operation[0]);
            BigDecimal bigDecimal2 = new BigDecimal(operation[1]);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "Invalid Input", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (currentOperator == "") return false;

        if (operation.length < 2) return false;


        BigDecimal ans = operate(operation[0], operation[1], currentOperator);


        /*System.out.println(ans);
        if(isInt(ans)){
            result=((int)ans)+"";
        }else{
            result=String.format("%.4f",ans)+"";
            System.out.println(result);
        }*/
        result = ans.toPlainString();

        return true;

    }

    public boolean isInt(double double1) {

        double c = Math.abs(double1 - (int) double1);
        if (c > 0) {
            return false;
        } else
            return true;
    }

    public void onClickEqual(View v) {     //equal button function
        if (display == "") return;
        if (!getResult()) return;


        console.setText(display + "\n" + "=" + "\t" + String.valueOf(result));

    }


}
