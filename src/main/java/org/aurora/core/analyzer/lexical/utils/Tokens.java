package org.aurora.core.analyzer.lexical.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @project org.aurora
 * @author Gabriel Honda on 01/03/2020
 */

/* Classe utilizada para encapsular a lista de tokens
 * populada na classe Lexical
 * */
public class Tokens {
    private static List<TokenContainer> tokens;

    static {
        tokens = new ArrayList<>();
    }

    public static void reset() {
        tokens = new ArrayList<>();
    }

    public static void add(TokenContainer tk) {
        tokens.add(tk);
    }

    /**
     *
     * @return List<TokenContainer> lista de container de token
     */
    public static List<TokenContainer> get() {
        return tokens;
    }
}
