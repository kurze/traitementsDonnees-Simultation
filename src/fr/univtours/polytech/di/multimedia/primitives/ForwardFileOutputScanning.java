package fr.univtours.polytech.di.multimedia.primitives;

import java.io.PrintStream;

// TODO: Auto-generated Javadoc
/**
 * Classe permettant d'écrire les enregistrements dans un fichier dans l'ordre
 * en utilisant un buffer pour minimiser le nombre d'entrées/sorties.
 * @author Sébastien Aupetit
 */
public class ForwardFileOutputScanning extends OutputScanning {

  /** La position actuelle dans le fichier. */
  private int fileIndex;

  /** La position de l'enregistrement courant dans le buffer. */
  private int bufferIndex;

  /** Le fichier à partir du quel on lit les données. */
  private final File file;

  /** Le buffer. */
  private final Buffer buffer;

  /**
   * Le constructeur
   * @param file le fichier à partir duquel les données sont lues
   * @param bufferSize la taille du buffer d'écrire en nombre d'enregistrements
   */
  public ForwardFileOutputScanning(final File file, final int bufferSize) {
    fileIndex = 0;
    bufferIndex = 0;
    this.file = file;
    buffer = new Buffer(bufferSize);
    declareBuffer(buffer);
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.Drawable#displayStatistics()
   */
  @Override
  protected void displayStatistics() {
    super.displayStatistics();
    file.displayStatistics();
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.OutputScanning#flushRecords()
   */
  @Override
  public void flushRecords() {
    file.writeLeftAlignedBuffer(fileIndex, buffer);
    fileIndex += buffer.getValidRecordCount();
    buffer.invalidateAllRecords();
    bufferIndex = 0;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.Algorithm#generateGraphRepresentation(java.io.PrintStream)
   */
  @Override
  protected String generateGraphRepresentation(final PrintStream out) {
    final String myNodeId = getNodeId();

    final String fileId = file.generateGraphRepresentation(out);

    super.generateGraphRepresentation(out);

    out.println("\tsubgraph cluster_" + myNodeId + " { fillcolor=chocolate2 }");
    out.println("\tbuffer0_" + myNodeId + " -> " + fileId + "\n");

    return "buffer0_" + myNodeId;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.OutputScanning#writeRecord(fr.univtours.polytech.di.multimedia.primitives.Record)
   */
  @Override
  public void writeRecord(final Record record) {
    buffer.setRecord(bufferIndex, record);
    bufferIndex++;
    if (bufferIndex == buffer.getSize()) {
      flushRecords();
    }
  }
}
