package fr.univtours.polytech.di.multimedia.exercices;

import java.util.Random;

import fr.univtours.polytech.di.multimedia.primitives.File;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;

/**
 * Exercice 8
 * @author Sébastien Aupetit
 */
public class Exercice8 implements Exercice {

  /** Le fichier d'entrée. */
  private File input;
  /** Le fichier de sortie. */
  private File output;

  /**
   * Nombre maximal de buffers qui peuvent être utilisés simultanément sur une
   * machine.
   */
  static final int maxBufferInMemory = 4;

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#checkOuputFiles()
   */
  @Override
  public boolean checkOuputFiles() {
    final Record record = new Record();
    final int last = -1;
    for (int i = 0; i < output.getSize(); ++i) {
      output.readRecord(i, record);
      final int k = Integer.valueOf(record.getField("key"));
      if (last > k) {
        return false;
      }
    }

    return input.getSize() == output.getSize();
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#configureInputFiles()
   */
  @Override
  public void configureInputFiles() {
    final Random random = new Random(1);
    final Record record = new Record();
    input = new File("Input");

    for (int i = 0; i < 10000; ++i) {
      record.setField("id", String.valueOf(i));
      record.setField("key", String.valueOf(random.nextInt(200000)));
      input.writeRecord(i, record);
    }

    output = new File("Output");
    input.resetStatistics();
    output.resetStatistics();
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#getProcessor()
   */
  @Override
  public Processor getProcessor() {
    return null;
  }
}
