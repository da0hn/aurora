package aurora.analyzer.lexical.utils.tokens;

import aurora.analyzer.lexical.utils.TokenContainer;

import java.util.ArrayList;
import java.util.List;

/*
 * @project aurora
 * @author Gabriel Honda on 01/03/2020
 */

/* classe utilizada para encapsular a lista de tokens
 * populada na classe Lexical
 * */
public class Tokens {
    private static List<TokenContainer> tokens;

    static {
        tokens = new ArrayList<>();
    }

    public static void add(TokenContainer tk){
        tokens.add(tk);
    }
    public static List<TokenContainer> get(){
        return tokens;
    }
}
