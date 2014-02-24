package fr.univtours.polytech.di.multimedia.primitives;

import java.io.PrintStream;

/**
 * Une classe repr�sentant la r�ception d'un buffer d'enregistrements sur un
 * r�seau. Une instance de {@link NetworkBufferSender} repr�sente l'h�te
 * distant.
 * @author S�bastien Aupetit
 */
public class NetworkBufferReceiver extends InputScanning {

  /** Le nom de la machine. */
  private final String hostname;

  /** Le buffer. */
  private final Buffer buffer;

  /** Le num�ro de l'enregistrement courant dans le buffer. */
  private int bufferIndex;

  /** Le {@link NetworkBufferSender} repr�sentant le processus distant. */
  private final NetworkBufferSender remoteHost;

  /** Compteur du nombre de buffers lu via le r�seau */
  private int buffersRead = 0;

  /**
   * Le constructeur.
   * @param hostname le nom de la machine
   * @param remoteHost Le {@link NetworkBufferSender} repr�sentant le processus
   *          distant
   */
  public NetworkBufferReceiver(final String hostname,
      final NetworkBufferSender remoteHost) {
    this.hostname = hostname;
    this.remoteHost = remoteHost;
    buffer = new Buffer(remoteHost.getBufferSize());
    bufferIndex = 0;
    declareBuffer(buffer);
    buffersRead = 0;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.Drawable#displayStatistics()
   */
  @Override
  protected void displayStatistics() {
    super.displayStatistics();
    remoteHost.displayStatistics();
    System.out.println("Network receiver " + hostname + " # buffers lus : "
        + buffersRead);
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.Algorithm#generateGraphRepresentation(java.io.PrintStream)
   */
  @Override
  protected String generateGraphRepresentation(final PrintStream out) {
    final String myNodeId = getNodeId();

    final String bufferPackerId = remoteHost.generateGraphRepresentation(out);

    super.generateGraphRepresentation(out);

    out
        .println("\tnetwork_"
            + myNodeId
            + " [shape=diamond, style=\"filled,rounded\", label=<Network>,fillcolor=darkolivegreen1,width=3,height=1];\n");

    out.println("\tsubgraph cluster_" + myNodeId
        + " { fillcolor=salmon; label=<" + getMyName() + " on " + hostname
        + ">;}");
    out.println("\t" + bufferPackerId + " -> " + "network_" + myNodeId + "\n");
    out
        .println("\tnetwork_" + myNodeId + " -> " + "buffer0_" + myNodeId
            + "\n");

    return "buffer0_" + myNodeId;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.InputScanning#getFirstAvailableRecord(fr.univtours.polytech.di.multimedia.primitives.Record)
   */
  @Override
  public boolean getFirstAvailableRecord(final Record record) {
    if (buffer.getValidRecordCount() == 0) {
      // on demande � obtenir un nouveau buffer au serveur distant.
      final Buffer fromBuffer = remoteHost.getBuffer();

      // Le buffer est transf�r� sur le r�seau

      // on receptionne le buffer
      if (fromBuffer != null) {
        buffer.invalidateAllRecords();
        int i = 0;
        while ((i < buffer.getSize()) && fromBuffer.isValid(i)) {
          buffer.setRecord(i, fromBuffer.getRecord(i));
          i++;
        }

        bufferIndex = 0;
        buffersRead++;
      } else {
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
    bufferIndex++;

    // on provoque le chargement du buffer suivant si n�cessaire
    getFirstAvailableRecord(new Record());
  }
}
