package aurora.app;

import aurora.parser.argument.Argument;
import aurora.parser.argument.Flag;

import java.util.Arrays;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class Aurora {

    public static void main(String[] args) {
        var data = Argument.parseArgs(args);

       for(Flag f : Flag.values()) {
           System.out.println(f);
       }

        System.out.println(data.getAssembly());
        System.out.println(data.getAurora());

    }

}
