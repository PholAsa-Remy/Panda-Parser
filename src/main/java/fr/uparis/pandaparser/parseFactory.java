package fr.uparis.pandaparser;

public class parseFactory {

    public IParser getParser (String parserType){

        if(parserType == null)
            return null;

        /* wait for the class Md2Html
        if (parserType.equals(Config.OPTION.get(0)))
            return new Md2Html ();
        */
        return null;
    }
}
