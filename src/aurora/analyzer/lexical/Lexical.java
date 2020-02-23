package aurora.analyzer.lexical;

import java.util.ArrayList;
import java.util.List;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public class Lexical {

    private Integer line = 0;
    private Integer column = 0;
    private List<TokenContainer> tokens = new ArrayList<>();

    public void analyze(List<String> code) {
        for(String line : code) {
            this.line++;
            analyzeLine(line);
            this.column = 1;
        }
    }

    private void analyzeLine(String line) {
        if(line.isEmpty() && line.isBlank()) return;
        StringBuilder buffer = new StringBuilder();

        Character currentChar = line.charAt(this.column);
        Character previousChar = line.charAt(this.column);
        Character nextChar = line.charAt(this.column + 1);

        for(Character ch : line.toCharArray()) {
            if(currentChar.equals('/') && nextChar.equals('/')) {
                return;
            } else if(currentChar.equals(' ')) {
                previousChar = currentChar;
                currentChar = nextChar;
                nextChar = line.charAt(++this.column + 1);
            } else if(Symbol.getValues().contains(ch.toString())) {
                tokens.add(new TokenContainer(Symbol.valueOf(ch.toString()),
                                              ch.toString(), this.line, this.column));
                buffer = new StringBuilder();
                this.column++;
            } else if(!Character.isLetterOrDigit(nextChar)) {
                buffer.append(currentChar);
                analyzeBuffer(buffer.toString());
                column += buffer.length();
                buffer = new StringBuilder();
            }
            buffer.append(ch);
        }
        this.tokens.add(new TokenContainer(Token.FINAL, "$",
                                           this.line, this.column));
    }

    private void analyzeBuffer(String buffer) {
        // TODO: LexicalLog
        if(Keyword.getValues().contains(buffer)) {
            this.tokens.add(new TokenContainer(Keyword.valueOf(buffer), buffer,
                                               this.line, this.column));
        } else if(isIdentifier(buffer)) {
            this.tokens.add(new TokenContainer(Token.ID, buffer,
                                               this.line, this.column));
        } else if(isNumber(buffer)) {
            this.tokens.add(new TokenContainer(Token.NUMBER, buffer,
                                               this.line, this.column));
        } else {
            // TODO: Log error
            throw new IllegalStateException("sdlkalfka");
        }
    }

    private boolean isIdentifier(String str) {
        if(Character.isDigit(str.charAt(0))) {
            return false;
        }
        return str.chars().allMatch(Character::isLetterOrDigit);
    }

    private boolean isNumber(String str) {
        return str.chars().allMatch(Character::isDigit);
    }

    public List<TokenContainer> getTokens() {
        return tokens;
    }
}
