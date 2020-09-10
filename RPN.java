
package rpn;

public class RPN {

   
    public static void main(String[] arg) {
        String s[] = {"5 + ) * ( 2",
            " 2 + ( - 3 * 5 ) ",
            "(( 2 + 3 ) * 5 ) * 8 ",
            "5 * 10 + ( 15 - 20 ) )  - 25",
            " 5 + ( 5 *  10 + ( 15 - 20 )  - 25 ) * 9"
        };
        for (int i = 0; i < s.length; i++) {

            Arithmetic a = new Arithmetic(s[i]);
            if (a.isBalance()) {
                System.out.println("Expression " + s[i] + " is balanced\n");
                a.postfixExpression();
                System.out.println("The post fixed expression is " + a.getPostfix());
		a.evaluateRPN();
  System.out.println("The answer to the evaluated expression is " + a.getResult());
               
            } else {
                System.out.println("Expression is not balanced\n");
            }
        }
    }
}
