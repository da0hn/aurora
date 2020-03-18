package aurora.lang;

/*
 * @project aurora
 * @author Gabriel Honda on 08/03/2020
 */
public enum NonTerminal implements Language {

    AURORA(0, "aurora"),
    RECURSIVE_STATEMENT(1, "recursive_statement"),
    STATEMENT(2, "statement"),
    WRITE(3, "write"),
    READ(4, "read"),
    CONDITIONAL(5, "conditional"),
    IF(6, "if"),
    ELSE(7, "else"),
    LOOP(8, "loop"),
    WHILE(9, "while"),
    DECLARATION(10, "declaration"),
    ASSIGNMENT(11, "assignment"),
    BASIC_EXPRESSION(12, "basic_expression"),
    BASIC_OPERATOR(13, "basic_operator"),
    STRING_EXPRESSION(14, "string_expression"),
    ANY_EXPRESSION(15, "any_expression");

    private String name;
    private int index;

    NonTerminal(int index, String name) {
        this.name = name;
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getName() {
        return name;
    }
}
