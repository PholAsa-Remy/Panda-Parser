package fr.uparis.pandaparser.core.build;

import fr.uparis.pandaparser.config.Config;
import fr.uparis.pandaparser.core.build.simple.Simple;
import fr.uparis.pandaparser.core.build.site.Site;
import lombok.*;
import lombok.extern.java.Log;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Classe abstraite PandaParser + classe interne pandaParser. Builder pour créer un Parser.
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@Log
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PandaParser {

    @Getter
    protected final String input;
    @Getter
    protected final String output;
    protected final boolean watch;
    protected final int jobs;
    @Getter
    protected final ParserType type;

    /**
     * Permet de faire une traduction d'un/es fichier(s) MD(s) en HTML(s).
     *
     * @see Site
     * @see Simple
     */
    public abstract void parse();

    /**
     * Création d'un builder.
     *
     * @return le Builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Classe interne pour build PandaParser
     */
    @NoArgsConstructor
    public static class Builder implements fr.uparis.pandaparser.core.build.Builder {

        /* Les valeurs par default */
        private String input = Config.DEFAULT_INPUT;
        private String output = Config.DEFAULT_OUTPUT;
        private ParserType type = Config.DEFAULT_PARSER_TYPE;
        /* Nombre de cœurs de la machine*/
        private int jobs = Config.DEFAULT_MACHINE_JOB;
        private boolean watch = false;

        @Override
        public Builder setInput(String input) {
            this.input = input;
            this.type = ParserType.getType(this.input);
            if (this.type.equals(ParserType.SITE) && !this.input.endsWith(File.separator))
                this.input += File.separator;
            return this;
        }


        @Override
        public Builder setOutput(String output) {
            this.output = output;
            if (!this.output.endsWith(File.separator))
                this.output += File.separator;
            return this;
        }

        @Override
        public Builder setJobs(int jobs) {
            this.jobs = jobs;
            return this;
        }

        @Override
        public Builder isWatched() {
            this.watch = true;
            return this;
        }

        /**
         * Création d'un parser (Simple ou site complet)
         *
         * @return PandaParser
         */
        public PandaParser build() {
            return (type == ParserType.SITE)
                    ? new Site(this.input, this.output, this.watch, this.jobs)
                    : new Simple(this.input, this.output, this.watch, this.jobs);
        }
    }
}
