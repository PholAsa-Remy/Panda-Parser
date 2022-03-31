package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static fr.uparis.pandaparser.utils.FilesUtils.usePatternToReplace;

/**
 * Template Provider read the template stored in the
 * template folder for the parser to use
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
public class TemplateProvider {
    private static final HashMap <String,String> templateList = new HashMap<>();

    /**
     * convert the template to the right format for jinjava
     *
     * @param templatePath template path
     * @return new template with the right format for jinjava
     * @throws IOException if the path doesn't exist.
     */
    private static String convertTemplate (String templatePath) throws IOException {
        String templateContent = FilesUtils.getFileContent(templatePath);
        //convert {{ include "path" }} to {% include "path" %}
        templateContent = usePatternToReplace(templateContent, "(\\{\\{ +include +\")([^\"]*)(\" +}})", "{% include \"$2\" %}");
        //convert {{ metadata.title }} to {{ title }}
        templateContent = usePatternToReplace(templateContent, "(\\{\\{ +metadata.)([^\"]*)( +}})", "{{ $2 }}");
        return templateContent;
    }

    /**
     * get template, and stock it in the hashmap templateList
     *
     * @param templatePath template path
     * @return return the template if exist, otherwise return the default template
     * @throws IOException if the path doesn't exist.
     */
    public static String getTemplate (String templatePath) {

        if (templateList.containsKey(templatePath))
            return templateList.get(templatePath);

        String template_content;
        try {
            template_content = convertTemplate(templatePath);
        } catch (IOException e) {
            try {
                template_content = convertTemplate(Config.DEFAULT_TEMPLATE);
            }catch (IOException e1){
                log.warning("default template not found");
                return "";
            }
        }
        templateList.put(templatePath, template_content);
        return template_content;
    }
}
