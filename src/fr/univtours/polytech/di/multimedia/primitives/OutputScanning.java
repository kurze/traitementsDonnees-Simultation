package fr.univtours.polytech.di.multimedia.primitives;

/**
 * Une classe de base définissant les méthodes minimales à fournir pour
 * implémenter un flux de sortie.
 * @author Sébastien Aupetit
 */
public abstract class OutputScanning extends Drawable {

  /**
   * Ecrit les enregistrements qui sont en attente d'écriture.
   */
  public abstract void flushRecords();

  /**
   * Ajout le contenu (une copie) d'un enregistrement à l'ensemble des
   * enregistrements à écrire. Les enregistrements sont écrits les uns à la
   * suite des autres.
   * @param record l'enregistrement à écrire
   */
  public abstract void writeRecord(Record record);
}
