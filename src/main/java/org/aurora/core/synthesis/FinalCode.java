package org.aurora.core.synthesis;

import org.javatuples.Triplet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;

import static org.aurora.core.analyzer.utils.PredicateService.isDecoratedIdentifier;

/**
 * @author daohn on 04/09/2020
 * @project org.aurora
 */
public class FinalCode {

    private final String       initProgram;
    private final String       endProgram;
    private final String       tab   = " ".repeat(4);
    private       List<String> pseudoAsm;
    private       String       textSection;
    private       String       dataSection;
    private       String       bssSection;
    private       Integer      count = 0;

    public FinalCode() {
        this(null);
    }

    public FinalCode(List<String> pseudoAsm) {
        this.pseudoAsm   = pseudoAsm;
        this.bssSection  = """
                           section .bss
                           """;
        this.dataSection =
                """
                section .data
                    fmtin: db "%d", 0x0
                    fmtout: db "%d", 0xA, 0x0
                          
                """;
        this.textSection =
                """
                section .text
                    global main
                    extern printf
                    extern scanf
                                           
                main:
                """;
        this.initProgram =
                """
                    push ebp
                    mov ebp, esp
                    
                """;
        this.endProgram  =
                """
                    mov esp, ebp
                    pop ebp
                    ret
                """;
    }

    public void setPseudoAsm(List<String> pseudoAsm) {
        this.pseudoAsm = pseudoAsm;
    }

    public String build() throws SynthesisException {

        if(this.pseudoAsm == null) throw new SynthesisException("Pseudo code not inserted");

        var it = pseudoAsm.iterator();

        this.textSection += this.initProgram;

        while(it.hasNext()) {
            var obj = it.next();
            if("INT".equals(obj)) {
                createVar(it);
            }
            else if("WRITE".equals(obj)) {
                createWrite(it);
            }
            else if("READ".equals(obj)) {
                createRead(it);
            }
            else if("IF".equals(obj)) {
                createIF(it);
            }
            else if("GOTO".equals(obj)) {
                createGoto(it);
            }
            else if(obj.contains("_L")) {
                createLabel(obj);
            }
            else {
                createExpression(obj, it);
            }
        }

        this.bssSection += "\n";
        this.dataSection += "\n";
        this.textSection += this.endProgram;

        return dataSection + bssSection + textSection;
    }

    private void createLabel(String label) {
        this.textSection += label + "\n";
    }

    private void createGoto(Iterator<String> it) {
        this.textSection += tab + "jmp " + it.next() + "\n";
    }

    private void createExpression(String variable, Iterator<String> it) throws SynthesisException {

        var expr = variable.split(" ");
        var first = convertToRegister(expr[0]);

        var second = convertToRegister(expr[2]);

        if(expr.length == 3) {
            this.textSection += tab + "mov eax, " + second + "\n";
            this.textSection += tab + "mov " + first + ", eax\n";
        }
        else {
            var third = convertToRegister(expr[3]);
            var opt = Optional.ofNullable(
                    switch(expr[4]) {
                        case "+" -> "add";
                        case "-" -> "sub";
                        case "*" -> "mul";
                        case "/" -> "div";
                        default -> null;
                    }
            );
            var operation =
                    opt.orElseThrow(() -> new SynthesisException(
                            "Ill-formed expression " + Arrays.toString(expr)));
            this.textSection += tab + "mov eax, " + second + "\n";
            this.textSection += tab + "mov ebx, " + third + "\n";

            this.textSection += switch(operation) {
                case "mul", "div" -> tab + operation + " ebx\n";
                default -> tab + operation + " eax, ebx\n";
            };

            this.textSection += tab + "mov " + first + ", eax\n";
        }
    }

    private String convertToRegister(String variable) {
        return switch(variable) {
            case "_t0" -> "ecx";
            case "_t1" -> "edx";
            default -> {
                if(isDecoratedIdentifier(variable)) {
                    yield "[" + variable + "]";
                }
                yield variable;
            }
        };
    }

    private void createVar(Iterator<String> it) {
        var obj = it.next();
        String var =
                """
                    %s: resd 1
                """.formatted(obj);
        this.bssSection += var;
    }

    private void createIF(Iterator<String> it) {
        var args = parseCondition(it.next());

        it.next();
        var obj = it.next();

        BiFunction<String, String, String> toAsm = (str, label) -> {
            if(str.equals(">=")) {
                return "jge " + label;
            }
            else {
                return "jle " + label;
            }
        };

        var condition =
                """
                    mov eax, %s
                    cmp eax, %s
                    %s
                    
                """.formatted(
                        args.getValue0(),
                        args.getValue2(),
                        toAsm.apply(args.getValue1(), obj)
                );

        textSection += condition;
    }

    private Triplet<String, String, String> parseCondition(String condition) {
        String[] values = condition.split(" ");
        UnaryOperator<String> identifier = str -> isDecoratedIdentifier(
                str) ? "[" + str + "]" : str;
        return new Triplet<>(identifier.apply(values[0]), values[1], identifier.apply(values[2]));
    }

    private void createRead(Iterator<String> it) {
        var obj = it.next();
        var read =
                """
                    push %s
                    push dword fmtin
                    call scanf
                    add esp, 8
                 
                """.formatted(obj);
        this.textSection += read;
    }

    private void createWrite(Iterator<String> it) {
        var obj = it.next();
        String write;
        if(isDecoratedIdentifier(obj)) {
            write = """
                        push dword [%s]
                        push dword fmtout
                        call printf
                        add esp, 8
                        
                    """.formatted(obj);
        }
        else {
            var str = "str_" + (count);
            write = """
                        push dword %s
                        call printf
                        add esp, 4
                        
                    """.formatted(str);
            var constDeclaration =
                    """
                        %s: db %s, 0xA, 0x0
                    """.formatted(str, obj);
            this.dataSection += constDeclaration;
            count++;
        }
        this.textSection += write;

    }

}
