package fr.univtours.polytech.di.multimedia.primitives;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Une classe permettant d'enchainer des traitements du type Processor.
 * @author S�bastien Aupetit
 */
public class SequentialProcessors extends Processor {

  /** Les processeurs. */
  List < Processor > processors = new ArrayList < Processor >();

  /**
   * Ajoute un processeur.
   * @param processor le processeur
   */
  public void addProcessor(final Processor processor) {
    processors.add(processor);
  }

  /**
   * Demande l'affichage des statistiques de l'algorithme et de l'ensemble des
   * algorithmes en entr�es et en sorties qui sont utilis�s.
   */
  @Override
  public void displayStatistics() {
    for (int i = 0; i < processors.size(); ++i) {
      processors.get(i).displayStatistics();
    }
  }

  /**
   * G�n�re les instructions DOT/Graphviz associ�es � l'algorithme afin de cr�er
   * une repr�sentation graphique de l'algorithme.
   * @param out le flux dans lequel sont �crits les instructions
   * @return l'identifiant unique de l'objet vers lequel doivent se faire les
   *         liaisons (fl�ches)
   */
  @Override
  protected String generateGraphRepresentation(final PrintStream out) {
    final String processorId = getNodeId();

    for (int i = 0; i < processors.size(); ++i) {
      final Processor processor = processors.get(i);
      processor.generateGraphRepresentation(out);

      if (processor.isSubgraph()) {
        out.println("\n\tsubgraph cluster_" + processor.getNodeId()
            + " {label=<" + processor.getMyName() + "<br/>Step " + (i + 1)
            + "> }\n");
      } else {
        out.println("\n\t " + processor.getNodeId() + " [label=<"
            + processor.getMyName() + "<br/>Step " + (i + 1) + "> ]\n");
      }
    }
    return processorId;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.Processor#run()
   */
  @Override
  public void run() {
    for (int i = 0; i < processors.size(); ++i) {
      processors.get(i).run();
      System.out.println(processors.get(i).getNodeId() + " done");
    }
  }
}
