package fr.univtours.polytech.di.multimedia.primitives;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Une classe m�re abstraite pout tous les objets dessinables.
 * @author S�bastien Aupetit
 */
public abstract class Drawable {

  /** La liste des buffers utilis�s par cet algorithme. */
  private final List < Buffer > myBuffers = new ArrayList < Buffer >();

  /** La derni�re partie du nom de la classe qualifi�e. */
  private final String myName;

  /** La liste des drawables d'entr�es utilis�s par cet algorithme. */
  private final List < Drawable > myInputDrawables =
      new ArrayList < Drawable >();

  /** La liste des drawables de sorties utilis�s par cet algorithme. */
  private final List < Drawable > myOutputDrawables =
      new ArrayList < Drawable >();

  /**
   * Le constructeur
   */
  public Drawable() {
    final String className = getClass().getName().replace('$', '.');
    myName = className.substring(className.lastIndexOf('.') + 1);
  }

  /**
   * D�clare un buffer comme �tant utilis�.
   * @param buffer le buffer
   */
  protected void declareBuffer(final Buffer buffer) {
    myBuffers.add(buffer);
  }

  /**
   * Declare un algorithme comme �tant utilis� en entr�e.
   * @param drawable l'algorithme
   */
  protected void declareInputDrawable(final Drawable drawable) {
    myInputDrawables.add(drawable);
  }

  /**
   * Declare un algorithme comme �tant utilis� en sortie.
   * @param algorithm l'algorithme
   */
  protected void declareOutputDrawable(final Drawable drawable) {
    myOutputDrawables.add(drawable);
  }

  /**
   * Demande l'affichage des statistiques de l'algorithme et de l'ensemble des
   * algorithmes en entr�es et en sorties qui sont utilis�s.
   */
  protected void displayStatistics() {
    for (final Drawable in : myInputDrawables) {
      in.displayStatistics();
    }
    for (final Drawable out : myOutputDrawables) {
      out.displayStatistics();
    }
  }

  /**
   * G�n�re les instructions DOT/Graphviz associ�es au drawable afin de cr�er
   * une repr�sentation graphique de l'algorithme.
   * @param out le flux dans lequel sont �crits les instructions
   * @return l'identifiant unique de l'objet vers lequel doivent se faire les
   *         liaisons (fl�ches)
   */
  protected String generateGraphRepresentation(final PrintStream out) {
    final String myNodeId = getNodeId();

    if (!isSubgraph()) {
      out.println("\t" + myNodeId + " [label=<" + getMyName()
          + ">,shape=box,fillcolor=purple2,style=filled];");
      generateGraphRepresentationForInputDrawable(out, myNodeId);
      generateGraphRepresentationForOuputDrawable(out, myNodeId);
      return myNodeId;
    } else {
      out.println("\tsubgraph cluster_" + myNodeId + " {");
      out.println("\t\tlabel=<" + getMyName() + ">;");
      out.println("\t\tstyle=filled;");

      for (int i = 0; i < myBuffers.size(); ++i) {
        out.println("\t\tbuffer" + i + "_" + myNodeId
            + " [shape=record, label=<{|||}|" + myBuffers.get(i).getSize()
            + " records>, style=filled, fillcolor=khaki1, rankdir=LR];");
      }

      out.println("\t}");

      generateGraphRepresentationForInputDrawable(out, "buffer0_" + myNodeId);
      generateGraphRepresentationForOuputDrawable(out, "buffer0_" + myNodeId);
      return "buffer0_" + myNodeId;
    }
  }

  /**
   * Generate graph representation for input drawable.
   * @param out the print stream
   * @param nodeId the node id to link with
   */
  protected void generateGraphRepresentationForInputDrawable(
      final PrintStream out, final String nodeId) {
    if (myInputDrawables.size() > 0) {
      for (final Drawable drawable : myInputDrawables) {
        final String id = drawable.generateGraphRepresentation(out);
        out.println("\t" + id + " -> " + nodeId + ";");
      }
    }
  }

  /**
   * Generate graph representation for output drawable.
   * @param out the print stream
   * @param nodeId the node id to link with
   */
  protected void generateGraphRepresentationForOuputDrawable(
      final PrintStream out, final String nodeId) {
    if (myOutputDrawables.size() > 0) {
      for (final Drawable drawable : myOutputDrawables) {
        final String id = drawable.generateGraphRepresentation(out);
        out.println("\t" + nodeId + " -> " + id + ";");
      }
    }
  }

  /**
   * Obtenir le nom court de l'objet.
   * <p/>
   * Les diff�rentes instances ne sont pas distingu�es.
   * @return le nom
   */
  public final String getMyName() {
    return myName;
  }

  /**
   * Obtenir un identifiant unique pour l'instance de la classe.
   * @return l'identifiant
   */
  public final String getNodeId() {
    return getMyName() + Integer.toHexString(hashCode());
  }

  protected boolean isSubgraph() {
    return (myBuffers.size() > 0);
  }
}
