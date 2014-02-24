package fr.univtours.polytech.di.multimedia.exercices;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.univtours.polytech.di.multimedia.primitives.File;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;

/**
 * Exercice 6
 * @author Sébastien Aupetit
 */
public class Exercice6 implements Exercice {
  /** La liste des fichiers d'entrées. */
  List < File > inputs = new ArrayList < File >();
  /** Le fichier de sortie. */
  File output;

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#checkOuputFiles()
   */
  @Override
  public boolean checkOuputFiles() {
    final Record record = new Record();

    final int key = -1;
    for (int i = 0; i < output.getSize(); ++i) {
      output.readRecord(i, record);
      final int k = Integer.parseInt(record.getField("key"));
      if (key > k) {
        return false;
      }
    }

    int sum = 0;
    for (final File file : inputs) {
      sum += file.getSize();
    }

    return output.getSize() == sum;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#configureInputFiles()
   */
  @Override
  public void configureInputFiles() {
    final Random random = new Random(1);

    for (int i = 0; i < 10; ++i) {
      final File file = new File("Input " + (i + 1));
      long key = random.nextInt(10);
      final int count = random.nextInt(50000);
      for (int j = 0; j < count; ++j) {
        final Record record = new Record();
        record.setField("id", (i + 1) + "-" + (j + 1));
        record.setField("key", String.valueOf(key));
        key += random.nextInt(10);
        file.writeRecord(j, record);
      }
      inputs.add(file);
      file.resetStatistics();
    }

    output = new File("Output");
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
