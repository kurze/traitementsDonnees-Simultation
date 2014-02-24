package fr.univtours.polytech.di.multimedia.exercices;

import java.util.Random;

import fr.univtours.polytech.di.multimedia.primitives.File;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;

/**
 * Exercice 5
 * @author S�bastien Aupetit
 */
public class Exercice5 implements Exercice {
  /** Le fichier d'entr�e. */
  private File inputFile;
  /** Le fichier de sortie. */
  private File outputFile;

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#checkOuputFiles()
   */
  @Override
  public boolean checkOuputFiles() {
    // test non impl�ment� pour ne pas donner la solution !!!!
    return true;
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
      record.setField("x", String.valueOf(random.nextInt(200000)));
      record.setField("y", String.valueOf(random.nextInt(200000)));
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
