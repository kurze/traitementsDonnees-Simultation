package fr.univtours.polytech.di.multimedia.exercices;

import java.awt.BufferCapabilities;

import fr.univtours.polytech.di.multimedia.primitives.BackwardFileInputScanning;
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
 * Exercice 1.2
 * @author Sébastien Aupetit
 */
public class Exercice1_2 implements Exercice {
  /** Le fichier d'entrée. */
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
		NetworkBufferSender nbs = new NetworkBufferSender("localhost",
				networkBufferSize, inputFile);
		InputScanning inputReader = new NetworkBufferReceiver("localhost", nbs);
		OutputScanning outputWriter = new ForwardFileOutputScanning(outputFile,
				fileBufferSize);

		return new SimpleProcessor(inputReader, outputWriter);
	}
}
