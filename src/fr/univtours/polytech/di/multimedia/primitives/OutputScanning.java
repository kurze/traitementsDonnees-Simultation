package fr.univtours.polytech.di.multimedia.primitives;

/**
 * Une classe de base d�finissant les m�thodes minimales � fournir pour
 * impl�menter un flux de sortie.
 * @author S�bastien Aupetit
 */
public abstract class OutputScanning extends Drawable {

  /**
   * Ecrit les enregistrements qui sont en attente d'�criture.
   */
  public abstract void flushRecords();

  /**
   * Ajout le contenu (une copie) d'un enregistrement � l'ensemble des
   * enregistrements � �crire. Les enregistrements sont �crits les uns � la
   * suite des autres.
   * @param record l'enregistrement � �crire
   */
  public abstract void writeRecord(Record record);
}
