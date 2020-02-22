package aurora.log;

/*
 * @project aurora
 * @author Gabriel Honda on 21/02/2020
 */
public interface Log {

    void message(String msg, int line, int column);
    void error(String err, int line, int column);

}
