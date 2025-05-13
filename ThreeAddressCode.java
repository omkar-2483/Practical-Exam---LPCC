import java.util.*;

public class ThreeAddressCode {
    public static void main(String[] args) {
        String expr = "y = x * x + w - v / r + r";
        System.out.println("Input: " + expr);
        
        // Remove spaces for easier parsing
        expr = expr.replaceAll("\\s+", "");
        String[] sides = expr.split("=");

        String lhs = sides[0];
        String rhs = sides[1];

        Stack<String> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        List<String> tac = new ArrayList<>();
        int tempCount = 1;

        // Operator precedence
        Map<Character, Integer> precedence = Map.of(
            '+', 1, '-', 1,
            '*', 2, '/', 2
        );

        for (int i = 0; i < rhs.length(); i++) {
            char c = rhs.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                operands.push(Character.toString(c));
            } else if (precedence.containsKey(c)) {
                while (!operators.isEmpty() && precedence.get(c) <= precedence.get(operators.peek())) {
                    char op = operators.pop();
                    String b = operands.pop();
                    String a = operands.pop();
                    String temp = "t" + tempCount++;
                    tac.add(temp + " = " + a + " " + op + " " + b);
                    operands.push(temp);
                }
                operators.push(c);
            }
        }

        // Remaining operations
        while (!operators.isEmpty()) {
            char op = operators.pop();
            String b = operands.pop();
            String a = operands.pop();
            String temp = "t" + tempCount++;
            tac.add(temp + " = " + a + " " + op + " " + b);
            operands.push(temp);
        }

        // Final assignment
        tac.add(lhs + " = " + operands.pop());

        // Output TAC
        System.out.println("\nThree Address Code:");
        for (String line : tac) {
            System.out.println(line);
        }
    }
}

