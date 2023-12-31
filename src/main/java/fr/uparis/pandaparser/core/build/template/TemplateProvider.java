package fr.uparis.pandaparser.core.build.template;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.loader.ResourceLocator;
import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.metadata.Metadata;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;

import java.io.IOException;
import java.util.HashMap;

import static fr.uparis.pandaparser.utils.RegExUtils.convertInclude;
import static fr.uparis.pandaparser.utils.RegExUtils.removeMetadataDot;

/**
 * Template Provider read the template stored in the
 * template folder for the parser to use
 *
 * @version 1.0.0
 * @since Mars 2022
 */
@Log
public class TemplateProvider {

    private TemplateProvider() {
    }

    private static final HashMap<String, String> templateList = new HashMap<>();

    /**
     * convert the template to the right format for jinjava
     *
     * @param templatePath template path
     * @return new template with the right format for jinjava
     * @throws IOException if the path doesn't exist.
     */
    private static String convertTemplate(String templatePath) throws IOException {
        String templateContent = FilesUtils.getFileContent(templatePath);
        templateContent = convertInclude(templateContent);
        templateContent = removeMetadataDot(templateContent);
        return templateContent;
    }

    /**
     * get template, and stock it in the hashmap templateList
     *
     * @param templatePath template path
     * @return return the template if exist, otherwise return the default template
     */
    public static String getTemplate(String templatePath) {

        if (templateList.containsKey(templatePath))
            return templateList.get(templatePath);

        String templateContent;
        try {
            templateContent = convertTemplate(templatePath);
        } catch (IOException e) {
            try {
                templateContent = convertTemplate(Config.DEFAULT_TEMPLATE);
            } catch (IOException e1) {
                log.warning("default template not found");
                return DEFAULT_TEMPLATE;
            }
        }
        templateList.put(templatePath, templateContent);
        return templateContent;
    }

    /**
     * use the metadata to complete the template
     *
     * @param meta meta create base on the fileContent
     * @return template completed with the metadata
     */
    public static String applyTemplate(Metadata meta) {
        String template = meta.getMetadataMap().containsKey("template") ? meta.getMetadataMap().get("template").toString() : Config.DEFAULT_TEMPLATE;
        String templateContent = getTemplate(template);
        //set the path for jinjava
        JinjavaConfig config = new JinjavaConfig();
        Jinjava jinjava = new Jinjava(config);
        ResourceLocator resource = (fullName, encoding, interpreter) -> FilesUtils.getFileContent(fullName);
        jinjava.setResourceLocator(resource);
        return jinjava.render(templateContent, meta.getMetadataMap());
    }

    private static final String DEFAULT_TEMPLATE = """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <title>{{ metadata.title }}</title>
                        </head>
                        <body>
                        <div id="content">{{ content }}</div>
                        </body>
                        </html>
                        """;
}
