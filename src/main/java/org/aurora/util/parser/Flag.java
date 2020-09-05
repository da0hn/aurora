package org.aurora.util.parser;

/*
 * @project org.aurora
 * @author Gabriel Honda on 19/02/2020
 */
public enum Flag {
    /*
     * Enumerados sao Singletons por natureza, os valores das flags sao mantidos
     * em toda a execucao da aplicacao, assim se o booleano for alterado
     * essa alteracao sera permanente ate o final da execucao do programa
     * */
    TOKENS(false, "--tokens"),
    SYNTACTIC(false, "--syntactic"),
    SEMANTIC(false, "--semantic"),
    SYMBOLS(false, "--symbols"),
    READABLE(false, "--readable"),
    FINAL_CODE(false, "--final-code");

    private String name;
    private boolean value;

    Flag(boolean value, String name) {
        this.value = value;
        this.name = name;
    }

    private void setValue(boolean value) {
        this.value = value;
    }

    public void activate() {
        setValue(true);
    }

    public String getName() {
        return this.name;
    }

    public boolean isActive() {
        return this.value;
    }

    public static Flag getFlag(String arg) {
        for(Flag flag : Flag.values()) {
            if(flag.name.equals(arg)) return flag;
        }
        throw new IllegalStateException("A flag " + arg + " não foi reconhecida.");
    }

    @Override
    public String toString() {
        return "Flag{" +
                "arg='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
