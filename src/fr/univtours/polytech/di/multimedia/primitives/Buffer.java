package fr.univtours.polytech.di.multimedia.primitives;

/**
 * Classe représentant une liste d'enregistrement stockée en mémoire.
 * @author Sébastien Aupetit
 */
public class Buffer {

  /**
   * Le nombre d'enregistrement du buffer.
   */
  private final int size;
  /**
   * Les enregistrements du buffer.
   */
  private final Record data[];
  /**
   * Un tableau indiquant si un enregistrement contient un contenu intelligible
   * ou non.
   */
  private final boolean usable[];

  /**
   * Le constructeur.
   * @param size Le nombre d'enregistrement contenu dans le buffer.
   */
  public Buffer(final int size) {
    this.size = size;
    data = new Record[size];
    for (int i = 0; i < size; ++i) {
      data[i] = new Record();
    }
    usable = new boolean[size];
    invalidateAllRecords();
  }

  /**
   * Obtenir un enregistrement.
   * @param index la position de l'enregistrement dans le buffer.
   * @return l'enregistrement
   */
  public Record getRecord(final int index) {
    if (usable[index]) {
      return data[index];
    } else {
      throw new IllegalArgumentException(
          "Vous ne pouvez pas lire un enregistrement vide");
    }
  }

  /**
   * Obtenir la taille du buffer en enregistrement.
   * @return la taille
   */
  public int getSize() {
    return size;
  }

  /**
   * Obtenir le nombre d'enregistrements ayant une valeur intelligible.
   * @return le nombre d'enregistrement
   */
  public int getValidRecordCount() {
    int count = 0;
    for (int i = 0; i < size; ++i) {
      if (isValid(i)) {
        count++;
      }
    }
    return count;
  }

  /**
   * Marque tous les enregistrements comme étant tous non intelligibles.
   */
  public void invalidateAllRecords() {
    for (int i = 0; i < usable.length; ++i) {
      usable[i] = false;
    }
  }

  /**
   * Marque un enregistrement comme étant non intelligible.
   * @param index la position de l'enregistrement
   */
  public void invalidateRecord(final int index) {
    usable[index] = false;
  }

  /**
   * Permet de savoir si un enregistrement est intelligible ou non.
   * @param index la position de l'enregistrement.
   * @return true si l'enregistrement est intelligible, false sinon.
   */
  public boolean isValid(final int index) {
    return usable[index];
  }

  /**
   * Définit le contenu d'un enregistrement du buffer.
   * @param index la position de l'enregistrement à définir.
   * @param record l'enregistrement à copier.
   */
  public void setRecord(final int index, final Record record) {
    data[index].copy(record);
    usable[index] = true;
  }
}
