package fr.univtours.polytech.di.multimedia.exercices;

import java.util.Random;

import fr.univtours.polytech.di.multimedia.exercices.Exercice2_1.FilteredProc;
import fr.univtours.polytech.di.multimedia.primitives.File;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileInputScanning;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileOutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;
import fr.univtours.polytech.di.multimedia.primitives.SimpleProcessor;

/**
 * Exercice 2.2
 * @author Sébastien Aupetit
 */
public class Exercice2_2 implements Exercice {
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
    for (int i = 0; i < outputFile.getSize(); ++i) {
      outputFile.readRecord(i, record);
      if (Integer.parseInt(record.getField("key")) % 2 != 0) {
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
    final Random random = new Random(1);
    final Record record = new Record();

    inputFile = new File("input");

    for (int i = 0; i < 100000; ++i) {
      record.setField("id", String.valueOf(i));
      record.setField("key", String.valueOf(random.nextInt(100)));
      inputFile.writeRecord(i, record);
    }

    outputFile = new File("pair");
    inputFile.resetStatistics();
    outputFile.resetStatistics();
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#getProcessor()
   */
  @Override
  public Processor getProcessor() {
	  return new SimpleProcessor(
			  new FilteredForwardFileInputScanning(inputFile,fileBufferSize), 
			  new ForwardFileOutputScanning(outputFile,fileBufferSize)
			  );
  }
 
}

class FilteredForwardFileInputScanning extends ForwardFileInputScanning {

	public FilteredForwardFileInputScanning(File file, int bufferSize) {
		super(file, bufferSize);
	}

	public boolean getFirstAvailableRecord(final Record record) {
		while (true) {
			if (buffer.getValidRecordCount() == 0) {
				if (!readBuffer()) {
					return false;
				}
			}
			Record tmp = buffer.getRecord(bufferIndex);
			if ((tmp.getNumericKey() % 2) == 0) {
				record.copy(tmp);
				return true;
			}
			markFirstAvailableRecordAsRead();
		}
	}
}
