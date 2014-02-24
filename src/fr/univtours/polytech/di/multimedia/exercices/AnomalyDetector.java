/**
 *
 */
package fr.univtours.polytech.di.multimedia.exercices;

import fr.univtours.polytech.di.multimedia.primitives.OutputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Record;

/**
 * Une classe virtuelle représentant un processus d'analyse d'anomalies.
 * @author Sébastien Aupetit
 */
public class AnomalyDetector extends OutputScanning {
  private long recordsWritten;
  private final int id;

  /**
   * Le constructeur.
   * @param id un numéro identifiant de l'analyseur
   */
  public AnomalyDetector(final int id) {
    recordsWritten = 0;
    this.id = id;
  }

  @Override
  protected void displayStatistics() {
    System.out.println("Anomaly detector " + id + " # enregistrements reçus : "
        + recordsWritten);
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.OutputScanning#flushRecords()
   */
  @Override
  public void flushRecords() {
    // on effectue ici la détection des anomalies
  }

  /**
   * Réinitialise les statistiques.
   */
  public void resetStatistics() {
    recordsWritten = 0;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.OutputScanning#writeRecord(fr.univtours.polytech.di.multimedia.primitives.Record)
   */
  @Override
  public void writeRecord(final Record record) {
    // on effectue ici la détection des anomalies
    recordsWritten++;
  }

}
