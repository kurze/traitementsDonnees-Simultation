package fr.univtours.polytech.di.multimedia.exercices;

import java.util.Random;

import fr.univtours.polytech.di.multimedia.primitives.File;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileInputScanning;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileOutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;

/**
 * Exercice 3.1
 * @author Sébastien Aupetit
 */
public class Exercice3_1 implements Exercice {
  /** Le fichier d'entrée. */
  private File inputFile;
  /** Le fichier de sortie des enregistrements pairs. */
  private File outputpairFile;
  /** Le fichier de sortie des enregistrements impairs. */
  private File outputimpairFile;

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#checkOuputFiles()
   */
  @Override
  public boolean checkOuputFiles() {
    final Record record = new Record();
    for (int i = 0; i < outputpairFile.getSize(); ++i) {
      outputpairFile.readRecord(i, record);
      if (Integer.parseInt(record.getField("key")) % 2 != 0) {
        return false;
      }
    }
    for (int i = 0; i < outputimpairFile.getSize(); ++i) {
      outputimpairFile.readRecord(i, record);
      if (Integer.parseInt(record.getField("key")) % 2 == 0) {
        return false;
      }
    }
    return (inputFile.getSize() == outputimpairFile.getSize()
        + outputpairFile.getSize());
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#configureInputFiles()
   */
  @Override
  public void configureInputFiles() {
    final Random random = new Random(1);
    final Record record = new Record();

    inputFile = new File("input");

    for (int i = 0; i < 100000; ++i) {
      record.setField("id", String.valueOf(i));
      record.setField("key", String.valueOf(random.nextInt(100)));
      inputFile.writeRecord(i, record);
    }

    outputpairFile = new File("pair");
    outputimpairFile = new File("impair");
    inputFile.resetStatistics();
    outputpairFile.resetStatistics();
    outputimpairFile.resetStatistics();
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#getProcessor()
   */
  @Override
  public Processor getProcessor() {
    return new KeySeparatorProcessor(new ForwardFileInputScanning(inputFile, fileBufferSize), new ForwardFileOutputScanning(outputpairFile, fileBufferSize),  new ForwardFileOutputScanning(outputimpairFile, fileBufferSize));
  }

}
