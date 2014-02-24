package fr.univtours.polytech.di.multimedia.primitives;

/**
 * Une classe de base définissant les méthodes minimales à fournir pour
 * implémenter un flux d'entrée.
 * @author Sébastien Aupetit
 */
public abstract class InputScanning extends Drawable {

  /**
   * Copie dans record le contenu du premier enregistrement disponible dans le
   * flux.
   * @param record l'enregistrement qui récupère les données.
   * @return true si un moins 1 enregistrement est disponible (et donc copié),
   *         false sinon
   *         <p/>
   *         Utilisation :
   * 
   *         <pre>
   * Record record = new Record();
   * while (in.getFirstAvailableRecord(record)) {
   *   // faire le traitement de record
   *   // indiquer que l'enregistrement a été utilisé
   *   in.markFirstAvailableRecordAsRead();
   * }
   * </pre>
   */
  public abstract boolean getFirstAvailableRecord(final Record record);

  /**
   * Marque le premier enregistrement disponible comme n'étant plus disponible.
   * <p/>
   * L'appel à cette fonction doit être précédé d'un getFirstAvailableRecord.
   */
  public abstract void markFirstAvailableRecordAsRead();

}
