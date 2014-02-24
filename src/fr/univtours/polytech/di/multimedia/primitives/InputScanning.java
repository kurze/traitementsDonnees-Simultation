package fr.univtours.polytech.di.multimedia.primitives;

/**
 * Une classe de base d�finissant les m�thodes minimales � fournir pour
 * impl�menter un flux d'entr�e.
 * @author S�bastien Aupetit
 */
public abstract class InputScanning extends Drawable {

  /**
   * Copie dans record le contenu du premier enregistrement disponible dans le
   * flux.
   * @param record l'enregistrement qui r�cup�re les donn�es.
   * @return true si un moins 1 enregistrement est disponible (et donc copi�),
   *         false sinon
   *         <p/>
   *         Utilisation :
   * 
   *         <pre>
   * Record record = new Record();
   * while (in.getFirstAvailableRecord(record)) {
   *   // faire le traitement de record
   *   // indiquer que l'enregistrement a �t� utilis�
   *   in.markFirstAvailableRecordAsRead();
   * }
   * </pre>
   */
  public abstract boolean getFirstAvailableRecord(final Record record);

  /**
   * Marque le premier enregistrement disponible comme n'�tant plus disponible.
   * <p/>
   * L'appel � cette fonction doit �tre pr�c�d� d'un getFirstAvailableRecord.
   */
  public abstract void markFirstAvailableRecordAsRead();

}
