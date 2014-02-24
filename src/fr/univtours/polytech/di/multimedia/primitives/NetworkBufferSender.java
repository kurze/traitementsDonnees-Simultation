/*
 *
 */
package fr.univtours.polytech.di.multimedia.primitives;

import java.io.PrintStream;

// TODO: Auto-generated Javadoc
/**
 * Une classe repr�sentant le processus d'envoi d'enregistrements � travers un
 * r�seau. Les enregistrements sont rassembl�s au sein d'un buffer qui est
 * ensuite envoy� int�gralement � travers le r�seau.
 * @author S�bastien Aupetit
 */
public class NetworkBufferSender extends Drawable {

  /** Le nom de la machine h�te. */
  private final String hostname;

  /** Le flux � partir du quel on obtient les enregistrements. */
  private InputScanning inputReader;

  /** Le buffer. */
  private final Buffer buffer;

  /** Le fichier � partir du quel on obtient les enregistrements. */
  private File inputFile;

  /** Le num�ro de l'enregistrement courant dans le fichier. */
  private int fileIndex = 0;

  /** Compteur de buffers �crits sur le r�seau. */
  private int buffersWritten = 0;

  /**
   * Le constructeur utilisant un fichier comme source d'enregistrements.
   * @param hostname Le nom de la machine h�te.
   * @param bufferSize La taille du buffer de lecture des enregistrements et
   *          d'envoi sur le r�seau (en nombre d'enregistrements)
   * @param inputFile le fichier d'entr�e
   */
  public NetworkBufferSender(final String hostname, final int bufferSize,
      final File inputFile) {
    this.hostname = hostname;
    this.inputFile = inputFile;
    this.fileIndex = 0;
    buffer = new Buffer(bufferSize);
    declareBuffer(buffer);
    declareInputDrawable(inputFile);
    buffersWritten = 0;
  }

  /**
   * Le constructeur utilisant un flux comme source d'enregistrements.
   * @param identifier Le nom de la machine h�te
   * @param bufferSize La taille du buffer d'envoi sur le r�seau (en nombre
   *          d'enregistrements)
   * @param inputReader le flux d'entr�e
   */
  public NetworkBufferSender(final String identifier, final int bufferSize,
      final InputScanning inputReader) {
    this.hostname = identifier;
    this.inputReader = inputReader;
    buffer = new Buffer(bufferSize);
    declareBuffer(buffer);
    declareInputDrawable(inputReader);
    buffersWritten = 0;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.Drawable#displayStatistics()
   */
  @Override
  protected void displayStatistics() {
    super.displayStatistics();
    System.out.println("Network sender " + hostname + " # buffers �crits : "
        + buffersWritten);
  }

  /**
   * Remplit le buffer d'enregistrements
   * @return true si au moins un enregistrement a pu �tre mis dans le buffer
   */
  private boolean fillBuffer() {
    if (inputFile == null) {

      final Record record = new Record();
      int i;

      buffer.invalidateAllRecords();
      i = 0;
      while ((i < buffer.getSize())
          && inputReader.getFirstAvailableRecord(record)) {
        buffer.setRecord(i, record);
        inputReader.markFirstAvailableRecordAsRead();
        i++;
      }
      if (i > 0) {
        buffersWritten++;
      }
      return i > 0;
    } else {
      final int count = inputFile.readLeftAlignedBuffer(fileIndex, buffer);
      fileIndex += count;
      return count > 0;
    }
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.Algorithm#generateGraphRepresentation(java.io.PrintStream)
   */
  @Override
  protected String generateGraphRepresentation(final PrintStream out) {
    final String myNodeId = super.generateGraphRepresentation(out);

    out.println("\tsubgraph cluster_" + getNodeId()
        + "{fillcolor=salmon; label=<" + getMyName() + " on " + hostname
        + ">;}");

    return myNodeId;
  }

  /**
   * Obtient un buffer rempli.
   * @return the buffer
   * @internal
   */
  Buffer getBuffer() {
    if (fillBuffer()) {
      return buffer;
    } else {
      return null;
    }
  }

  /**
   * Obtient la taille du buffer.
   * @return la taille du buffer
   * @internal
   */
  int getBufferSize() {
    return buffer.getSize();
  }
}
