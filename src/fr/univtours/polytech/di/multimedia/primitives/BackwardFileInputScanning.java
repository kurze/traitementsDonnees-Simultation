package fr.univtours.polytech.di.multimedia.primitives;

import java.io.PrintStream;

// TODO: Auto-generated Javadoc
/**
 * Classe permettant de lire les enregistrements d'un fichier dans l'ordre
 * inverse en utilisant un buffer pour minimiser le nombre d'entrées/sorties.
 * @author Sébastien Aupetit
 */
public class BackwardFileInputScanning extends InputScanning {

  /** La position actuelle dans le fichier. */
  private int fileIndex;

  /** La position de l'enregistrement courant dans le buffer. */
  private int bufferIndex;

  /** Le fichier à partir duquel on lit les données. */
  private final File file;

  /** Le buffer. */
  private final Buffer buffer;

  /**
   * Le constructeur.
   * @param file le fichier à partir duquel les données sont lues.
   * @param bufferSize la taille du buffer de lecture en nombre
   *          d'enregistrements
   */
  public BackwardFileInputScanning(final File file, final int bufferSize) {
    fileIndex = file.getSize() - 1;
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
   * @see fr.univtours.polytech.di.multimedia.primitives.Algorithm#generateGraphRepresentation(java.io.PrintStream)
   */
  @Override
  protected String generateGraphRepresentation(final PrintStream out) {
    final String myNodeId = getNodeId();

    final String fileId = file.generateGraphRepresentation(out);

    super.generateGraphRepresentation(out);

    out.println("\tsubgraph cluster_" + myNodeId + " { fillcolor=coral2 }");
    out.println("\t" + fileId + " -> " + "buffer0_" + myNodeId + "\n");

    return "buffer0_" + myNodeId;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.InputScanning#getFirstAvailableRecord(fr.univtours.polytech.di.multimedia.primitives.Record)
   */
  @Override
  public boolean getFirstAvailableRecord(final Record record) {
    if (buffer.getValidRecordCount() == 0) {
      if (!readBuffer()) {
        return false;
      }
    }
    record.copy(buffer.getRecord(bufferIndex));
    return true;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.InputScanning#markFirstAvailableRecordAsRead()
   */
  @Override
  public void markFirstAvailableRecordAsRead() {
    buffer.invalidateRecord(bufferIndex);
    bufferIndex--;
    getFirstAvailableRecord(new Record());
  }

  /**
   * Effectue la lecture d'un buffer à partir du fichier.
   * @return true si au moins un enregistrement a pu être lu.
   */
  private boolean readBuffer() {
    final int count = file.readRightAlignedBuffer(fileIndex, buffer);
    if (count == 0) {
      return false;
    } else {
      bufferIndex = buffer.getSize() - 1;
      fileIndex -= count;
      return true;
    }
  }
}
