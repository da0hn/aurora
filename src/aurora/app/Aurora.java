package aurora.app;

import aurora.parser.argument.Argument;

/*
 * @project aurora
 * @author Gabriel Honda on 20/02/2020
 */
public class Aurora {

    public static void main(String[] args) {
        var data = Argument.parseArgs(args);

        System.out.println(data.getAssembly());
        System.out.println(data.getAurora());

    }

}
