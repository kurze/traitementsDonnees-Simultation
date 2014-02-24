package fr.univtours.polytech.di.multimedia.exercices;

import fr.univtours.polytech.di.multimedia.primitives.File;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;

/**
 * Exercice 4
 * @author Sébastien Aupetit
 */
public class Exercice4 implements Exercice {

  /** Le fichier d'entrée. */
  private File inputFile;
  /** Le fichier de sortie. */
  private File outputFile;

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#checkOuputFiles()
   */
  @Override
  public boolean checkOuputFiles() {
    final Record record1 = new Record();
    final Record record2 = new Record();
    for (int i = 0; i < 100000; ++i) {
      inputFile.readRecord(i, record1);
      outputFile.readRecord(outputFile.getSize() - 1 - i, record2);
      if (!record1.getField("id").equals(record2.getField("id"))) {
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
    return null;
  }

}
