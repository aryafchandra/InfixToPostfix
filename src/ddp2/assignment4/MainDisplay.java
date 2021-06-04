package ddp2.assignment4;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import javax.swing.*;

public class MainDisplay extends JPanel {
    /*
    Creating label for the gui, the top half is for the left hand side,
    the bottom half is the output for the respective top half
    */
    private JLabel Infix;
    private JLabel postExpression;
    private JLabel evaluateResult;
    private JLabel errorMessage;
    private JLabel outputPostfix;
    private JLabel outputResult;
    private JLabel outputError;

    // Textfield for the input
    private JTextField inputInfix;

    private JPanel panel;
    private String plainInfix;

    private String text;

    /**
     * Method to create label and textfield if needed
     */
    public MainDisplay(){
        /*
        Create both label and textfield for the input
         */
        Infix = new JLabel("Enter Infix: ");
        inputInfix = new JTextField(10);
        inputInfix.setBackground(Color.YELLOW);
        inputInfix.addActionListener(new textListener());
        add(Infix);
        add(inputInfix);

        /*
        Create label for the postfix expression
         */
        postExpression = new JLabel("Postfix expression: ");
        outputPostfix = new JLabel();
        add(postExpression);
        add(outputPostfix);

        /*
        Create label for the result
         */
        evaluateResult = new JLabel("Result: ");
        outputResult = new JLabel();
        add(evaluateResult);
        add(outputResult);

        /*
        Create label for the error message
         */
        errorMessage = new JLabel("Error Messages: ");
        outputError = new JLabel("[]");
        add(errorMessage);
        add(outputError);
        outputError.setForeground(Color.decode("#651200"));

        setLayout(new GridLayout(4,2));

        /*
        Set the size and background color
         */
        setPreferredSize(new Dimension(700, 150));

        setBackground(Color.GRAY);
    }

    /**
     * class textListener, what it does is get input text from the inputInfix textfield
     */
    private class textListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            text = inputInfix.getText();
            System.out.println(text);

            postfixEvaluate postfixClass = new postfixEvaluate();

            postfixClass.infixtoPostfix(text);
            outputPostfix.setText(postfixClass.getPostfix());
            outputError.setText(postfixClass.getErrorMessage());
            //change the foreground color of error messages
            outputError.setForeground(Color.decode("#651200"));
            outputResult.setText(postfixClass.getRESULT());

        }
    }
}
