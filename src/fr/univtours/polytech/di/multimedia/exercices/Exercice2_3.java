package fr.univtours.polytech.di.multimedia.exercices;

import java.util.Random;

import fr.univtours.polytech.di.multimedia.primitives.File;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileInputScanning;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileOutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.OutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;
import fr.univtours.polytech.di.multimedia.primitives.SimpleProcessor;

/**
 * Exercice 2.3
 * @author S�bastien Aupetit
 */
public class Exercice2_3 implements Exercice {
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
			  new ForwardFileInputScanning(inputFile,fileBufferSize), 
			  new FilteredForwardFileOutputScanning(outputFile,fileBufferSize)
			  );
  }
}

class FilteredForwardFileOutputScanning extends
		ForwardFileOutputScanning {

	public FilteredForwardFileOutputScanning(File file, int bufferSize) {
		super(file, bufferSize);
		// TODO Auto-generated constructor stub
	}

	public void writeRecord(final Record record) {
		if ((record.getNumericKey() % 2) == 0) {
			buffer.setRecord(bufferIndex, record);
			bufferIndex++;
			if (bufferIndex == buffer.getSize()) {
				flushRecords();
			}
		}
	}
}