package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;
import java.io.IOException;
import java.util.HashMap;

/**
 * Template Provider read the template stored in the
 * template folder for the parser to use
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
public class TemplateProvider {
    private static HashMap <String,String> templateList;

    private static String readTemplate (String templatePath){
        try {
            return FilesUtils.getFileContent(Config.TEMPLATE_DIR + templatePath);
        }catch (IOException e){
            log.warning("Template " + templatePath + " not found !");
            return "";
        }
    }

    public static String getTemplateContent (String templatePath){
        if (templateList == null){
            templateList = new HashMap<>();
        }

        if (templateList.containsKey(templatePath))
            return templateList.get(templatePath);

        String template_content = readTemplate (templatePath);
        templateList.put(templatePath,template_content);
        return template_content;
    }
}
