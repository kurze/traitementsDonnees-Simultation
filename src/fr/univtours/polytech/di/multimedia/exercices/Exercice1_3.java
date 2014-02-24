package fr.univtours.polytech.di.multimedia.exercices;

import fr.univtours.polytech.di.multimedia.primitives.File;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileInputScanning;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileOutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.InputScanning;
import fr.univtours.polytech.di.multimedia.primitives.NetworkBufferReceiver;
import fr.univtours.polytech.di.multimedia.primitives.NetworkBufferSender;
import fr.univtours.polytech.di.multimedia.primitives.OutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;
import fr.univtours.polytech.di.multimedia.primitives.SimpleProcessor;

/**
 * Exercice 1.3
 * @author S�bastien Aupetit
 */
public class Exercice1_3 implements Exercice {
  /** Le fichier d'entr�e. */
  private File inputFile;
  /** Le fichier de sortie. */
  private File outputFile;

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#checkOuputFiles()
   */
  @Override
  public boolean checkOuputFiles() {
    final Record record = new Record();
    for (int i = 0; i < 100000; ++i) {
      outputFile.readRecord(i, record);
      if (Integer.parseInt(record.getField("id")) != i) {
        return false;
      }
    }
    return true;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#configureInputFiles()
   */
  @Override
  public void configureInputFiles() {
    final Record record = new Record();

    inputFile = new File("input");

    for (int i = 0; i < 100000; ++i) {
      record.setField("id", String.valueOf(i));
      inputFile.writeRecord(i, record);
    }

    outputFile = new File("output");
    inputFile.resetStatistics();
    outputFile.resetStatistics();
  }

	/**
	 * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#getProcessor()
	 */
	@Override
	public Processor getProcessor() {
		ForwardFileInputScanning ffis = new ForwardFileInputScanning(inputFile,
				fileBufferSize);
		NetworkBufferSender nbs = new NetworkBufferSender("localhost",
				networkBufferSize, ffis);
		InputScanning inputReader = new NetworkBufferReceiver("localhost", nbs);
		OutputScanning outputWriter = new ForwardFileOutputScanning(outputFile,
				fileBufferSize);

		return new SimpleProcessor(inputReader, outputWriter);
	}

}
