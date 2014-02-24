package fr.univtours.polytech.di.multimedia.primitives;

import java.io.IOException;
import java.io.PrintStream;

/**
 * Une classe de base repr�santant le processus controlant l'ensemble du
 * traitement effectu�.
 *
 * @author S�bastien Aupetit
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
     * G�n�re une repr�sentation graphique du processeur et de ses flux dans un
     * fichier image.
     * <p/>
     * Le logiciel externe dot de graphviz est utilis� pour construire la
     * repr�sentation.
     *
     * @return le nom du fichier image contenant la repr�sentation.
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
     * G�n�re les instructions DOT/Graphviz associ�es au processeur afin de
     * cr�er une repr�sentation graphique de celui-ci.
     *
     * @param out
     *            le flux dans lequel sont �crits les instructions
     * @return l'identifiant unique de l'objet vers lequel doivent se faire les
     *         liaisons (fl�ches)
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
     * Ex�cute l'ensemble des traitements associ�s � ce processeur. Cette
     * m�thode doit �tre red�finie par les classes d�riv�es.
     */
    public abstract void run();
}
