/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpn;

/**
 *
 * @author Joey
 */
import java.util.Stack;
import java.util.EmptyStackException;
import java.util.Scanner;

class Arithmetic {

    Stack stk;
    String expression, postfix;
    int length;

    Arithmetic(String s) {
        expression = s;
        postfix = "";
        length = expression.length();
        stk = new Stack();
    }

    // Validate the expression - make sure parentheses are balanced

    boolean isBalance() {
        int index = 0;
        boolean fail = false;

        try {
            while (index < length && !fail) {
                char ch = expression.charAt(index);

                switch (ch) {
                    case Constants.LEFT_NORMAL:
                        stk.push(new Character(ch));
                        break;
                    case Constants.RIGHT_NORMAL:
                        stk.pop();
                        break;
                    default:

                        break;
                }//end of swtich
                index++;
            }//end of while
        }//end of try
        
        catch (EmptyStackException e) {
            System.out.println(e.toString());
            fail = true;
        }
        if (stk.empty() && !fail) {
            return true;
        } else {
            return false;
        }
    } 	// Already defined

    // Convert expression to postfix notation
    void postfixExpression() {
        stk.clear(); // Re-using the stack object
        Scanner scan = new Scanner(expression);
        char current;
        // The algorithm for doing the conversion.... Follow the bullets
        while (scan.hasNext()) {
            String token = scan.next();

            if (isNumber(token)) // Bullet # 1
            {
                postfix = postfix + token + " ";
            } else {
                current = token.charAt(0);

                if (isParentheses(current)) // Bullet # 2 begins
                {
                    if (stk.empty() || current == Constants.LEFT_NORMAL) {

                        stk.push(current);
                    } else if (current == Constants.RIGHT_NORMAL) {
                        try {

                            Character ch = (Character) stk.pop();
                            char top = ch.charValue();

                            while (top != Constants.LEFT_NORMAL) {
                                postfix = postfix + top + " ";

                                ch = (Character) stk.pop();
                                top = top = ch;

                            }
                        } catch (EmptyStackException e) {

                        }
                    }
                }// Bullet # 2 ends
                else if (isOperator(current))// Bullet # 3 begins
                {
                    if (stk.empty()) {
                        stk.push(current);
                    } else {
                        try {
							 // Remember the method peek simply looks at the top
                            // element on the stack, but does not remove it.

                            char top = (Character) stk.peek();
                            boolean higher = hasHigherPrecedence(top, current);

                            while (top != Constants.LEFT_NORMAL && higher) {
                                postfix = postfix + stk.pop() + " ";
                                top = (Character) stk.peek();
                            }
                            stk.push(new Character(current));
                        } catch (EmptyStackException e) {
                            stk.push(new Character(current));
                        }
                    }
                }// Bullet # 3 ends
            }
        } // Outer loop ends

        try {
            while (!stk.empty()) // Bullet # 4
			postfix = postfix + stk.pop() + " ";
        } catch (EmptyStackException e) {

        }
    }

    boolean isNumber(String str) {
        try {

            Double.parseDouble(str);

            return true;

        } catch (NumberFormatException e) {

            return false;

        }
    }

		//define this method
    boolean isParentheses(char current) {
        boolean parenthesis;

        switch (current) {

            case Constants.LEFT_NORMAL:

            case Constants.RIGHT_NORMAL:

                parenthesis = true;

                break;

            default:

                parenthesis = false;

                break;

        }

        return parenthesis;

    }

    boolean isOperator(char ch) {
        boolean operator = false;

        switch (ch) {

            case Constants.PLUS:

            case Constants.MINUS:

            case Constants.MULTIPLY:

            case Constants.DIVIDE:

                operator = true;

                break;

            default:

                break;

        }

        return operator;

    }

    boolean hasHigherPrecedence(char top, char current) {
        int topmost = -1;

        int currentValue = -1;

        switch (top) {

            case Constants.PLUS:

            case Constants.MINUS:

                topmost = 0;

                break;

            case Constants.MULTIPLY:

            case Constants.DIVIDE:

                topmost = 1;

                break;

            default:

                break;

        }

        switch (current) {

            case Constants.PLUS:

            case Constants.MINUS:

                currentValue = 0;

                break;

            case Constants.MULTIPLY:

            case Constants.DIVIDE:

                currentValue = 1;

                break;

            default:

                break;

        }

        return topmost >= currentValue;
    }

    String getPostfix() {
        return postfix;
    }

    void evaluateRPN() {

        Scanner scan = new Scanner(getPostfix());

        stk.clear(); // Empty the stack and re-use it,

                     // this time to house the operands use stack to
                     // house the operands - numbers
        while (scan.hasNext()) {

            String s = scan.next();

            int x3;

            if (isNumber(s)) {
                stk.push(Integer.parseInt(s));
            } else {

                try {

                    Integer i = (Integer) stk.pop();

                    int x1 = i;

                    int x2 = (Integer) stk.pop();

                    char x = s.charAt(0);

                    Integer value = null;

                    switch (x) {

                        case '+':

                            x3 = x2 + x1;

                            value = x3;

                            stk.push(value);

                            break;

                        case '*':

                            x3 = x2 * x1;

                            value = x3;

                            stk.push(value);

                            break;

                        case '/':

                            x3 = x2 / x1;

                            value = x3;

                            stk.push(value);

                            break;

                        case '-':

                            x3 = x2 - x1;

                            value = x3;

                            stk.push(value);

                            break;

                        default:

                            break;

                    }

                } catch (EmptyStackException e) {

                }

            }

        }//while

    }

    int getSizeOfStack() {

        return stk.size();

    }

    int getResult() {

        try {

            return (Integer) stk.pop();

        } catch (EmptyStackException e) {

            System.out.println(e.toString() + "\nResult might be unreliable");

        }

        return -1;

    }
}
