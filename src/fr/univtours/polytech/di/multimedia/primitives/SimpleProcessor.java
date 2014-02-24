package fr.univtours.polytech.di.multimedia.primitives;

// TODO: Auto-generated Javadoc
/**
 * Une classe processeur implémentant un processeur simple consistant à lire les
 * enregistrements à parti d'un flux et à écrire ces enregistrements dans un
 * autre flux.
 * @author Sébastien Aupetit
 */
public class SimpleProcessor extends Processor {

  /** The input reader. */
  protected InputScanning inputReader;

  /** The output writer. */
  protected OutputScanning outputWriter;

  /**
   * The Constructor.
   * @param inputReader le flux d'entrée
   * @param outputWriter le flux de sortie
   */
  public SimpleProcessor(final InputScanning inputReader,
      final OutputScanning outputWriter) {
    this.inputReader = inputReader;
    this.outputWriter = outputWriter;
    declareInputDrawable(inputReader);
    declareOutputDrawable(outputWriter);
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.Processor#run()
   */
  @Override
  public void run() {
    final Record record = new Record();

    while (inputReader.getFirstAvailableRecord(record)) {
      outputWriter.writeRecord(record);
      inputReader.markFirstAvailableRecordAsRead();
    }
    outputWriter.flushRecords();
  }

}
