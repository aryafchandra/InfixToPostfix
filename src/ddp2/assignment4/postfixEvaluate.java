package ddp2.assignment4;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.ArrayList;


public class postfixEvaluate {
    private String errorMessage = "[]";
    private String POSTFIX = "";
    private ArrayList listError = new ArrayList();
    private String RESULT ="";
    Long a;
    Long b;


    /**
     * method to return the precedence of a mathematical operator
     * @param text
     * @return int
     */
    static int precedence(String text){
        return switch (text) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            case "^" -> 3;
            default -> -1;
        };
    }

    /**
     * method to determine if a string is numeric or not
     * @param token
     * @return boolean
     */
    public boolean isNumeric(String token){
        boolean go = false;

        try {
            Integer.parseInt(token);
            go = true;
        }catch (NumberFormatException e){

            switch (token){
                case "+":
                    go = false;
                    break;
                case "-":
                    go = false;
                    break;
                case "*":
                    go = false;
                    break;
                case "/":
                    go = false;
                    break;
                case "^":
                    go = false;
                    break;
                case " ":
                    go = false;
                    break;
                case "":
                    go = false;
                    break;
                default:
                    if(!listError.contains("NON INTEGER DETECTED")) {
                        listError.add("NON INTEGER DETECTED");

                        go = false;
                    } else {
                        go = false;
                    }
            }
        }
        return go;
    }

    /**
     * method to convert infix to postfix
     * @param input
     * @return String
     */

    public String infixtoPostfix(String input) {
        Stack<String> INFIX_TO_POSTFIX = new Stack<>();
        String TOKEN;
        POSTFIX = "";
        StringTokenizer token = new StringTokenizer(input, "*/^-+()", true);

        outer:
        while (token.hasMoreTokens()) {

            TOKEN = token.nextToken().strip();

            //push TOKEN when it store (
            if (TOKEN.equals("(")) {
                INFIX_TO_POSTFIX.push(TOKEN);

            } else if (TOKEN.equals(")")) {
                try {
                    //pop stack when its peek is not ( and also not an empty stack
                    while (!INFIX_TO_POSTFIX.peek().equals("(") && !INFIX_TO_POSTFIX.isEmpty())
                        POSTFIX += INFIX_TO_POSTFIX.pop() + " ";

                    //pop when it goes outside the while loop, (when the peek is "(")
                    INFIX_TO_POSTFIX.pop();

                } catch (EmptyStackException e){
                    //catch Exception when the stack is pop until empty, meaning we doesnt find "("

                    if(!listError.contains("UNBALANCED PARANTHESIS")) {

                        listError.add("UNBALANCED PARANTHESIS");

                    }
                }

            } else {
                //check if TOKEN is numeric or not
                if (isNumeric(TOKEN)) {
                    POSTFIX += TOKEN + " ";
                    continue outer;
                }


                //pop for left associavity of operators is less than or equal to its precedence, push for right associativity
                while (!INFIX_TO_POSTFIX.isEmpty() && precedence(TOKEN) <= precedence(INFIX_TO_POSTFIX.peek())) {
                    if(TOKEN.equals("^") && INFIX_TO_POSTFIX.peek().equals("^")){
                        break;
                    }

                    POSTFIX += INFIX_TO_POSTFIX.pop() + " ";

                }
                INFIX_TO_POSTFIX.push(TOKEN);
            }
        }
        //pop all operators
        inner:
        while (!INFIX_TO_POSTFIX.isEmpty()) {
            if (INFIX_TO_POSTFIX.peek().equals("(")) {
                if(!listError.contains("UNBALANCED PARANTHESIS")){
                    INFIX_TO_POSTFIX.pop();
                    listError.add("UNBALANCED PARANTHESIS");
                    continue inner;
                }else {
                    INFIX_TO_POSTFIX.pop();
                    continue inner;
                }

            }
            POSTFIX += INFIX_TO_POSTFIX.pop() + " ";


        }
        errorMessage = listError.toString();
        evalPostfix(POSTFIX);

        return POSTFIX;
    }

    /**
     * method to evaluate/calculate a postfix notation
     * @param postfix
     * @return
     */

    public String evalPostfix(String postfix){
        String TOKEN_POSTFIX;
        Stack<Long> stackInt = new Stack<>();
        String _postFix = postfix.strip();
        StringTokenizer token = new StringTokenizer(_postFix, "*/^-+() ", true);
        boolean ZERO_DIVISION = false;
        boolean GO = true;
        boolean SPACE = false;


        while (token.hasMoreTokens()) {
            TOKEN_POSTFIX = token.nextToken().trim();


            if (TOKEN_POSTFIX.equals(" ") || TOKEN_POSTFIX.equals("")) {
                SPACE = true;
                continue;
            }

            //make result store hypen if the input has non numeric
            if (listError.contains("NON INTEGER DETECTED") && !SPACE) {
                RESULT = "-";
                break;
            }

            //push numbers
            if (isNumeric(TOKEN_POSTFIX)) {
                TOKEN_POSTFIX.replaceAll(" ", "");
                stackInt.push(Long.parseLong(TOKEN_POSTFIX));

            } else {
                //pop when encountered operators
                try {
                    a = stackInt.pop();
                    b = stackInt.pop();

                } catch (EmptyStackException e) {
                    //catch EmptyStackException and add "MISSING OPERAND" to arrayList of errorMessages
                    if (!listError.contains("MISSING OPERAND")) {

                        listError.add("MISSING OPERAND");
                        if (a != null || b != null) {
                            RESULT = a.toString();
                            GO = false;
                            break;
                        } else {
                            RESULT = "-";
                            GO = false;
                        }
                    }
                }

                //push the product of each operation based on the operator encountered
                if (GO) {
                    switch (TOKEN_POSTFIX) {
                        case "+":
                            stackInt.push(a + b);

                            break;
                        case "-":
                            stackInt.push(b - a);
                            break;
                        case "*":
                            stackInt.push(a * b);

                            break;
                        case "/":
                            try {
                                stackInt.push(b / a);
                            } catch (ArithmeticException e) {
                                //catch ArithmeticException and add "ZERO DIVISION" to arrayList of errorMessages
                                if (!listError.contains("ZERO DIVISION")) {
                                    listError.add("ZERO DIVISION");
                                    RESULT = b.toString();
                                    ZERO_DIVISION = true;
                                }
                                RESULT = b.toString();
                                ZERO_DIVISION = true;
                            }
                            break;
                        case "^":
                            stackInt.push((long) Math.pow(b, a));

                            break;
                        case "":
                            continue;
                        case " ":
                            continue;
                    }
                }
            }
        }

        //pop result from stack
        if(!stackInt.isEmpty() && !ZERO_DIVISION){

            RESULT = stackInt.pop().toString();
        }
        errorMessage = listError.toString();

    return RESULT;
    }


    //various getter
    public String getErrorMessage() {
        return errorMessage;
    }

    public String getPostfix(){
        return POSTFIX;
    }

    public String getRESULT(){
        return RESULT;
    }
}





