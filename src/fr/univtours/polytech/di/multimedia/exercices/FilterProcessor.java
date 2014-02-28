package fr.univtours.polytech.di.multimedia.exercices;

import fr.univtours.polytech.di.multimedia.primitives.InputScanning;
import fr.univtours.polytech.di.multimedia.primitives.OutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;

public abstract class FilterProcessor extends Processor {

	  /** The input reader. */
	  protected InputScanning inputReader;

	  /** The output writer. */
	  protected OutputScanning outputWriter;
	  
	  /**
	   * The Constructor.
	   * @param inputReader le flux d'entrée
	   * @param outputWriter le flux de sortie
	   */
	  public FilterProcessor(final InputScanning inputReader,
	      final OutputScanning outputWriter) {
	    this.inputReader = inputReader;
	    this.outputWriter = outputWriter;
	    declareInputDrawable(inputReader);
	    declareOutputDrawable(outputWriter);
	  }
	  
	  /**
	   * @see fr.univtours.polytech.di.multimedia.primitives.Processor#run()
	   */
	  @Override
	  public void run() {
	    final Record record = new Record();

	    while (inputReader.getFirstAvailableRecord(record)) {
	     if(filterRecord(record)) {
	      outputWriter.writeRecord(record);
	     }
	     inputReader.markFirstAvailableRecordAsRead();
	    }
	    outputWriter.flushRecords();
	  }

	protected abstract boolean filterRecord(final Record record);
}
