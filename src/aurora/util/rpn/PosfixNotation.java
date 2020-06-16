package aurora.util.rpn;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static java.util.Arrays.asList;

/*
 * @project linguagemDeProgramacaoDois
 * @author Gabriel Honda on 17/04/2020
 */
public class PosfixNotation {

    private static class Counter {
        private int count;

        public Counter() {
            this.count = 0;
        }

        public int incrementAndGet() {
            return ++count;
        }

        public int getAndIncrement() {
            return count++;
        }

        public int get() {
            return count;
        }
    }

    private Stack<String> operatorStack;

    public PosfixNotation() {
        this.operatorStack = new Stack<>();
    }

    private int priorityOperator(String element) {
        switch(element) {
            case ")":
                return 1;
            case "+":
            case "-":
                return 2;
            case "*":
            case "/":
                return 3;
            default:
                return 0;
        }
    }

    private boolean isOperator(String element) {
        return asList("+", "-", "*", "=", "/").contains(element);
    }

    private boolean isVariable(String element) {
        return element.chars().allMatch(Character::isLetterOrDigit);
    }

    private List<String> process(String expression) {
        var separateAll = "";
        return asList(expression.split(separateAll));
    }

    public String toPosfix(String expression) {
        LinkedList<String> posfix = new LinkedList<>();
        var listExpression = process(expression);
        var index = new Counter();
        while(index.get() < listExpression.size()) {
            String ch = listExpression.get(index.getAndIncrement());

            if(ch.isBlank()) continue;

            if(isVariable(ch)) {
                posfix.add(ch);
            }
            else if(ch.equals("(")) {
                this.operatorStack.push(ch);
            }
            else if(ch.equals(")")) {
                while(!this.operatorStack.isEmpty()) {
                    var op = this.operatorStack.pop();
                    if( op.equals("(")) break;
                    posfix.add(op);
                }
            }
            else if(isOperator(ch)) {
                while( !operatorStack.isEmpty() && priorityOperator(operatorStack.peek()) >= priorityOperator(ch)) {
                    if(operatorStack.isEmpty()) break;
                    posfix.add(operatorStack.pop());
                }
                operatorStack.push(ch);
            }
        }

        var builder = new StringBuilder();
        posfix.addAll(operatorStack);
        posfix.forEach(builder::append);
        return builder.toString();
    }
}
