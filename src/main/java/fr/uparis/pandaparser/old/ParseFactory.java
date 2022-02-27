package fr.uparis.pandaparser.old;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.old.IParser;
import fr.uparis.pandaparser.old.Md2Html;

public class ParseFactory {

    public IParser getParser (String parserType){

        if(parserType == null)
            return null;

        if (parserType.equals(Config.OPTION.get(0)))
            return new Md2Html();
        return null;
    }
}
