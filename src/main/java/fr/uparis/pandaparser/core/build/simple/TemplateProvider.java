package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Template Provider read the template stored in the
 * template folder for the parser to use
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
public class TemplateProvider {
    private static final HashMap <String,String> templateList = new HashMap<>();

    private static String convertTemplate (String templatePath) throws IOException {
        String templateContent = FilesUtils.getFileContent(templatePath);
        Pattern pattern = Pattern.compile("(\\{\\{ +include +\")([^\"]*)(\" +}})");
        Matcher m = pattern.matcher(templateContent);
        templateContent = m.replaceAll("{% include '$2' %}");
        return templateContent;
    }

    public static String getTemplate (String templatePath) throws IOException{
        if (templateList.containsKey(templatePath))
            return templateList.get(templatePath);

        String template_content = convertTemplate(templatePath);
        templateList.put(templatePath,template_content);
        return template_content;
    }
}
