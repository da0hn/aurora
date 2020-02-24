package aurora.analyzer.lexical.utils;

/*
 * @project aurora
 * @author Gabriel Honda on 23/02/2020
 */
public class Pointer {

    private Character previous;
    private Character current;
    private Character next;

    public Pointer(Character current, Character next) {
        this.current = current;
        this.previous = current;
        this.next = next;
    }

    public Pointer(Character previous, Character current, Character next) {
        this.previous = previous;
        this.current = current;
        this.next = next;
    }

    public Character getPrevious() {
        return previous;
    }

    public void setPrevious(Character previous) {
        this.previous = previous;
    }

    public Character getCurrent() {
        return current;
    }

    public void setCurrent(Character current) {
        this.current = current;
    }

    public Character getNext() {
        return next;
    }

    public void setNext(Character next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return "Pointer{" +
                "previous=" + previous +
                ", current=" + current +
                ", next=" + next +
                '}';
    }
}
