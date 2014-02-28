package fr.univtours.polytech.di.multimedia.exercices;

import java.util.Random;

import fr.univtours.polytech.di.multimedia.primitives.File;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileInputScanning;
import fr.univtours.polytech.di.multimedia.primitives.ForwardFileOutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.InputScanning;
import fr.univtours.polytech.di.multimedia.primitives.OutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;
import fr.univtours.polytech.di.multimedia.primitives.SimpleProcessor;

/**
 * Exercice 5
 * @author Sébastien Aupetit
 */
public class Exercice5 implements Exercice {
  /** Le fichier d'entrée. */
  private File inputFile;
  /** Le fichier de sortie. */
  private File outputFile;

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#checkOuputFiles()
   */
  @Override
  public boolean checkOuputFiles() {
    // test non implémenté pour ne pas donner la solution !!!!
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
		return new GeoProcessor(
				new ForwardFileInputScanning(inputFile,fileBufferSize), 
				new ForwardFileOutputScanning(outputFile,fileBufferSize)
				);
	}

}

class GeoProcessor extends FilterProcessor {

	public GeoProcessor(InputScanning inputReader, OutputScanning outputWriter) {
		super(inputReader, outputWriter);
	}

	@Override
	protected boolean filterRecord(Record record) {
		int x = 2;
		int y = 5;
		int rX = Integer.parseInt(record.getField("x"));
		int rY = Integer.parseInt(record.getField("x"));
		double dist = Math.sqrt(
					Math.pow(x - rX, 2) + 
					Math.pow(y - rY, 2)
				);
		if (dist < 10)
			return true;
		else
			return false;
	}

}
