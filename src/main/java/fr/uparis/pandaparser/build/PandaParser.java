package fr.uparis.pandaparser.build;

import fr.uparis.pandaparser.build.simple.Simple;
import fr.uparis.pandaparser.build.site.Site;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Classe abstraite PandaParser + classe interne pandaParser. Builder pour créer un Parser.
 *
 * @author panda-parser groupe
 * @version 1.0.0
 * @since Fev 2022
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PandaParser {

    private final String input;
    private final String output;
    private final boolean watch;
    private final int jobs;
    private final ParserType type;

    /**
     * Permet de faire une traduction d'un fichier MDs en HTMLs.
     */
    public abstract void parse();

    /**
     * Création d'un builder.
     *
     * @return le Builder
     */
    public static PandaParser.Builder builder() {
        return new Builder();
    }

    /**
     * Classe interne pour build PandaParser
     */
    @NoArgsConstructor
    public static class Builder implements fr.uparis.pandaparser.build.Builder {

        /* Les valeurs par default */
        private String input = ".";
        private String output = "_output";
        private ParserType type = ParserType.SITE;
        /* Nombre de cœurs de la machine*/
        private int jobs = Runtime.getRuntime().availableProcessors();
        private boolean watch = false;

        @Override
        public Builder setInput(String input) {
            this.input = input;
            return this;
        }

        @Override
        public Builder setOutput(String output) {
            this.output = output;
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
            this.type = ParserType.getType(this.input);
            return (type.equals(ParserType.SIMPLE))
                    ? new Simple(this.input, this.output, this.watch, this.jobs)
                    : new Site(this.input, this.output, this.watch, this.jobs);
        }
    }
}
