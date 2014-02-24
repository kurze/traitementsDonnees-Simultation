/**
 *
 */
package fr.univtours.polytech.di.multimedia.exercices;

import fr.univtours.polytech.di.multimedia.primitives.OutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Record;

/**
 * Une classe virtuelle repr�sentant un processus d'analyse d'anomalies.
 * @author S�bastien Aupetit
 */
public class AnomalyDetector extends OutputScanning {
  private long recordsWritten;
  private final int id;

  /**
   * Le constructeur.
   * @param id un num�ro identifiant de l'analyseur
   */
  public AnomalyDetector(final int id) {
    recordsWritten = 0;
    this.id = id;
  }

  @Override
  protected void displayStatistics() {
    System.out.println("Anomaly detector " + id + " # enregistrements re�us : "
        + recordsWritten);
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.OutputScanning#flushRecords()
   */
  @Override
  public void flushRecords() {
    // on effectue ici la d�tection des anomalies
  }

  /**
   * R�initialise les statistiques.
   */
  public void resetStatistics() {
    recordsWritten = 0;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.OutputScanning#writeRecord(fr.univtours.polytech.di.multimedia.primitives.Record)
   */
  @Override
  public void writeRecord(final Record record) {
    // on effectue ici la d�tection des anomalies
    recordsWritten++;
  }

}
