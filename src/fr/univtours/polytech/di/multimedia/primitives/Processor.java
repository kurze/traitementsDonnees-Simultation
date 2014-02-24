package fr.univtours.polytech.di.multimedia.primitives;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Une classe de base représantant le processus controlant l'ensemble du
 * traitement effectué.
 *
 * @author Sébastien Aupetit
 */
public abstract class Processor extends Drawable {

    /**
     * Demande l'affichage des statistiques.
     */
    @Override
    public void displayStatistics() {
        super.displayStatistics();
    }

    /**
     * Génère une représentation graphique du processeur et de ses flux dans un
     * fichier image.
     * <p/>
     * Le logiciel externe dot de graphviz est utilisé pour construire la
     * représentation.
     *
     * @return le nom du fichier image contenant la représentation.
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public String generateGraph() throws IOException {
        final String fileName = "graph-" + this;
        final PrintStream out = new PrintStream(fileName);

        out.println("digraph {");
        out.println("\trankdir=LR;");
        out.println("\tpenwidth=1;");
        out.println("\tpencolor=black;");
        out.println("\tbgcolor=white;");
        out.println("\tmargin=0;");
        out.println("\tcompound=true;pack=true;");
        generateGraphRepresentation(out);
        out.println("}");

        String windowsHack;
        if (System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
            windowsHack = "..\\graphviz-bin\\release\\bin\\";
        } else
            windowsHack = "";

        final ProcessBuilder pb = new ProcessBuilder(windowsHack + "dot",
                "-Tpng", "-o" + fileName + ".png", fileName);
        final Process process = pb.start();
        try {
            process.waitFor();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        }

        final java.io.File f = new java.io.File(fileName);
        f.delete();
        return fileName + ".png";
    }

    /**
     * Génère les instructions DOT/Graphviz associées au processeur afin de
     * créer une représentation graphique de celui-ci.
     *
     * @param out
     *            le flux dans lequel sont écrits les instructions
     * @return l'identifiant unique de l'objet vers lequel doivent se faire les
     *         liaisons (flèches)
     */
    @Override
    protected String generateGraphRepresentation(final PrintStream out) {

        final String processorId = super.generateGraphRepresentation(out);

        if (isSubgraph()) {
            out
                    .println("\n\tsubgraph cluster_"
                            + getNodeId()
                            + " {fillcolor=yellow; style=\"rounded,filled\"; shape=box;  height=2;}");
        } else {
            out
                    .println("\n\t"
                            + getNodeId()
                            + " [fillcolor=yellow, style=\"rounded,filled\", shape=box,  height=2]");
        }

        return processorId;
    }

    /**
     * Exécute l'ensemble des traitements associés à ce processeur. Cette
     * méthode doit être redéfinie par les classes dérivées.
     */
    public abstract void run();
}
