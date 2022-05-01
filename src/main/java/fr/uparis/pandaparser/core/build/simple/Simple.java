package fr.uparis.pandaparser.core.build.simple;

import fr.uparis.pandaparser.core.build.PandaParser;
import fr.uparis.pandaparser.core.build.ParserType;
import fr.uparis.pandaparser.core.build.incremental.HistoryManager;
import fr.uparis.pandaparser.core.build.metadata.Metadata;
import fr.uparis.pandaparser.core.build.template.TemplateProvider;
import fr.uparis.pandaparser.utils.FilesUtils;
import lombok.extern.java.Log;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static fr.uparis.pandaparser.utils.FilesUtils.createFileFromContent;
import static fr.uparis.pandaparser.utils.FilesUtils.getFileContent;
import static fr.uparis.pandaparser.utils.RegExUtils.removeHeaderFromContent;

/**
 * Translation from markdown to HTML
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
public class Simple extends PandaParser {

    /* An instance of the parser of common-markdown library */
    List<Extension> extensions = Arrays.asList(TablesExtension.create(), StrikethroughExtension.create());
    private final org.commonmark.parser.Parser parser = Parser.builder().extensions(extensions).build();

    private String inputFileName;
    private String fileContent;

    public Simple(String input, String output, String template, boolean watch, int jobs) {
        super(input, output, template, watch, jobs, ParserType.SIMPLE);
        init();
    }

    private void init() {
        try {
            this.inputFileName = FilesUtils.getHtmlFilenameFromMdFile(FilesUtils.getFileName(input));
            fileContent = getFileContent(input);
        } catch (IOException e) {
            log.warning(e.getMessage());
        }
    }

    @Override
    public void parse() {
        if (!checkToRebuild()) return;
        String bodyContentHTML = getBodyContentWithoutHeaderHTML(fileContent);
        applyTemplate(bodyContentHTML);
        save();
    }

    /**
     * apply the template to the body content
     *
     * @return the body content with the template applied
     */
    private boolean checkToRebuild() {
        boolean shouldBeRebuild = HistoryManager.getInstance().shouldBeRebuild(input);
        log.info(FilesUtils.getFileName(input) + " : " + (shouldBeRebuild
                ? "Rebuild, changes detected: file " + input +" has modified"
                : "no changes detected"));
        return shouldBeRebuild;
    }

    /**
     * get the body content without the header
     *
     * @param fileContent the content of the file
     * @return the body content without the header
     */
    private String getBodyContentWithoutHeaderHTML(String fileContent) {
        // remove the header from the fileContent
        String fileContentWithoutHeader = removeHeaderFromContent(fileContent);
        //parse the fileContentWithoutHeader in html for the key "content"
        Node document = parser.parse(fileContentWithoutHeader);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        return renderer.render(document);
    }


    /**
     * Apply the template to the content
     *
     * @param bodyContentHTML the content of the body
     */
    private void applyTemplate(String bodyContentHTML) {
        try {
            Metadata meta = new Metadata(fileContent, bodyContentHTML);
            String fileContentAfterTemplate = TemplateProvider.applyTemplate(meta);
            createFileFromContent(this.output + inputFileName, fileContentAfterTemplate);
        } catch (IOException e) {
            log.warning(e.getMessage());
        }
    }

    /**
     * save file changements in the history
     */
    private void save() {
        HistoryManager.getInstance().update(input);
        log.info("MD 2 HTML parser : input" + this.input + " -> out: " + this.output + inputFileName);
    }
}
