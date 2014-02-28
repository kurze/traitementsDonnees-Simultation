package fr.univtours.polytech.di.multimedia.exercices;

import fr.univtours.polytech.di.multimedia.primitives.InputScanning;
import fr.univtours.polytech.di.multimedia.primitives.OutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Processor;
import fr.univtours.polytech.di.multimedia.primitives.Record;

public class KeySeparatorProcessor extends Processor {

	/** The input reader. */
	protected InputScanning inputReader;

	/** The output writer. */
	protected OutputScanning outputWriterPair;

	/** The output writer. */
	protected OutputScanning outputWriterImpair;
	
	/**
	 * The Constructor.
	 * @param inputReader le flux d'entrée
	 * @param outputWriter le flux de sortie
	 */
	public KeySeparatorProcessor(final InputScanning inputReader,
			final OutputScanning outputWriterPair, final OutputScanning outputWriterImpair) {
		this.inputReader = inputReader;
		this.outputWriterPair = outputWriterPair;
		this.outputWriterImpair = outputWriterImpair;
		declareInputDrawable(inputReader);
		declareOutputDrawable(outputWriterPair);
		declareOutputDrawable(outputWriterImpair);
	}

	/**
	 * @see fr.univtours.polytech.di.multimedia.primitives.Processor#run()
	 */
	@Override
	public void run() {
		final Record record = new Record();

		while (inputReader.getFirstAvailableRecord(record)) {
			int n = record.getNumericKey();
			if(n%2 == 0)
				outputWriterPair.writeRecord(record);
			else
				outputWriterImpair.writeRecord(record);
			inputReader.markFirstAvailableRecordAsRead();
		}
		outputWriterPair.flushRecords();
		outputWriterImpair.flushRecords();
	}
}
