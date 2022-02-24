package fr.uparis.pandaparser;

public class ParseFactory {

    public IParser getParser (String parserType){

        if(parserType == null)
            return null;

        if (parserType.equals(Config.OPTION.get(0)))
            return new Md2Html ();
        return null;
    }
}
