/**
 *
 */
package fr.univtours.polytech.di.multimedia.exercices;

import java.util.Random;

import fr.univtours.polytech.di.multimedia.primitives.InputScanning;
import fr.univtours.polytech.di.multimedia.primitives.Record;

/**
 * Classe virtuelle représentant une sonde sur un réseau.
 * @author Sébastien Aupetit
 */
public class NetworkRecorder extends InputScanning {
  private Record currentRecord;
  private final Random random;
  private long serial;
  private final int id;
  private int remaining = 0;

  /**
   * Le constructeur
   * @param id un numéro identifiant de la sonde
   */
  public NetworkRecorder(final int id) {
    this.id = id;
    random = new Random(id);
    serial = 0;
    remaining = random.nextInt(50000);
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.InputScanning#getFirstAvailableRecord(fr.univtours.polytech.di.multimedia.primitives.Record)
   */
  @Override
  public boolean getFirstAvailableRecord(final Record record) {
    if ((currentRecord == null) && (remaining > 0)) {
      currentRecord = new Record();
      currentRecord.setField("id", "Sonde-" + id + "-" + serial);
      currentRecord.setField("key", String.valueOf(serial));
      serial += random.nextInt(10);
      remaining--;
    }
    if (currentRecord != null) {
      record.copy(currentRecord);
      return true;
    } else {
      return false;
    }
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.primitives.InputScanning#markFirstAvailableRecordAsRead()
   */
  @Override
  public void markFirstAvailableRecordAsRead() {
    currentRecord = null;
  }

}
