package fr.uparis.pandaparser.core.build.simple;

import com.hubspot.jinjava.Jinjava;
import com.hubspot.jinjava.JinjavaConfig;
import com.hubspot.jinjava.interpret.JinjavaInterpreter;
import com.hubspot.jinjava.loader.ResourceLocator;
import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;
import java.io.IOException;
import java.nio.charset.Charset;

import static fr.uparis.pandaparser.utils.FilesUtils.createFileFromContent;
import static fr.uparis.pandaparser.utils.FilesUtils.getFileContent;

/**
 * Translation from markdown to HTML
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Simple extends PandaParser {

    public Simple(String input, String output, boolean watch, int jobs) {
        super(input, output, watch, jobs, ParserType.SIMPLE);
    }

    /**
     * use the metadata to complete the template
     * @param meta meta create base on the fileContent
     * @param template template content
     * @return template completed with the metadata
     */
    private String applyTemplate (Metadata meta, String template){
        //set the path for jinjava
        JinjavaConfig config = new JinjavaConfig();
        Jinjava jinjava = new Jinjava(config);
        ResourceLocator resource = new ResourceLocator() {
            @Override
            public String getString(String fullName, Charset encoding, JinjavaInterpreter interpreter) throws IOException {

                return FilesUtils.getFileContent(fullName);
            }
        };
        jinjava.setResourceLocator(resource);

        return jinjava.render(template, meta.getMetadata());
    }

    @Override
    public void parse() {
        try {
            String inputFileName = FilesUtils.getHtmlFilenameFromMdFile(FilesUtils.getFileName(input));
            String fileContent = getFileContent(input);
            Metadata meta = new Metadata(fileContent);

            //if there is a path for the template in the metadata, take the path otherwise take the default template
            String FileContentAfterTemplate;
            if (meta.getMetadata().containsKey("template")){
                FileContentAfterTemplate = applyTemplate(meta, TemplateProvider.getTemplate(meta.getMetadata().get("template").toString()));
            }else {
                FileContentAfterTemplate = applyTemplate(meta, TemplateProvider.getTemplate(Config.DEFAULT_TEMPLATE));
            }

            createFileFromContent(this.output + inputFileName, FileContentAfterTemplate);
            log.info("MD 2 HTML parser : input" + this.input + " -> out: " + this.output + inputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
