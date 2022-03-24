package fr.uparis.pandaparser.core.build.simple;

import java.util.HashMap;

/**
 * Template Provider read the template stored in the
 * template folder for the parser to use
 * @version 1.0.0
 * @since Mars 2022
 */
public class TemplateProvider {
    private static HashMap <String,String> templateList;

    public static String getTemplateContent (String template_path){
        if (templateList == null){
            templateList = new HashMap<>();
        }

        if (templateList.containsKey(template_path))
            return templateList.get(template_path);

        return "";
    }
}
