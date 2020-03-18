package aurora.analyzer.lexical.utils;

import aurora.analyzer.lexical.interfaces.LexicalObject;
import aurora.lang.Terminal;

/*
 * @project aurora
 * @author Gabriel Honda on 18/03/2020
 */
public record TokenContainer(
        Terminal token,
        String lexeme,
        Integer line,
        Integer column
) implements LexicalObject {
    @Override
    public String print() {
        return this.token() + " at [" + this.line()
                + ", " + this.column() + "]" +
                ": '" + this.lexeme() + '\'';
    }
}
