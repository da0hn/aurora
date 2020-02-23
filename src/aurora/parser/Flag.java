package aurora.parser;

/*
 * @project aurora
 * @author Gabriel Honda on 19/02/2020
 */
public enum Flag {
    /*
    * Enumerados sao Singletons por natureza, os valores das flags sao mantidos em toda
    * a execucao da aplicacao, assim se o booleano for alterado
    * essa alteracao sera permanente ate o final da execucao do programa
    * */
    TOKENS(false, "--tokens"),
    SYNTACTIC(false,"--syntactic"),
    SEMANTIC(false, "--semantic"),
    SYMBOLS(false,"--symbols"),
    FINAL_CODE(false,"--final-code");

    private String arg;
    private boolean value;

    Flag(boolean value, String arg){
        this.value = value;
        this.arg = arg;
    }

    public void setValue(boolean value){
        this.value = value;
    }

    public String getValue(){
        return this.arg;
    }

    public static Flag getFlag(String arg){
        switch( arg ){
            case "--tokens":
                return Flag.TOKENS;
            case "--syntactic":
                return Flag.SYNTACTIC;
            case "--semantic":
                return Flag.SEMANTIC;
            case "--symbols":
                return Flag.SYMBOLS;
            case "--final-code":
                return Flag.FINAL_CODE;
        }
        throw new IllegalStateException("A flag "+ arg +" não foi reconhecida.");
    }

    @Override
    public String toString() {
        return "Flag{" +
                "arg='" + arg + '\'' +
                ", value=" + value +
                '}';
    }
}
