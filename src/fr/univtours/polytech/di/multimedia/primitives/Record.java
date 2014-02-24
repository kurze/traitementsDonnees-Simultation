package fr.univtours.polytech.di.multimedia.primitives;

import java.util.HashMap;

/**
 * Classe représentant un enregistrement.
 * @author Sébastien Aupetit
 */
public class Record {
  /**
   * Table de hashage contenant des paires (nom du champ, valeur du champ).
   */
  private final HashMap < String, String > fields =
      new HashMap < String, String >();

  /**
   * Le constructeur.
   */
  public Record() {
    // nothing to do
  }

  /**
   * L'operateur permettant de copier un enregistrement dans un autre.
   * @param record l'enregistrement à copier
   */
  public void copy(final Record record) {
    if (record == this) {
      throw new IllegalArgumentException(
          "Un enregistrement ne peut pas être copié sur lui même.");
    }
    fields.clear();
    fields.putAll(record.fields);
  }

  /**
   * Permet d'obtenir la valeur d'un champ.
   * @param fieldName le nom du champ.
   * @return la valeur du champ
   * @throws IllegalArgumentException si le nom du champ ne correspond pas à un
   *           champ de l'enregistrement
   */
  public String getField(final String fieldName) {
    if (fields.containsKey(fieldName)) {
      return fields.get(fieldName);
    } else {
      throw new IllegalArgumentException(
          "Ce champ n'existe pas dans l'enregistrement");
    }
  }

  /**
   * Permet d'obtenir sous la forme d'un entier la valeur de l'attribut "key".
   * @return la valeur de l'attribut
   */
  public int getNumericKey() {
    return Integer.valueOf(getField("key"));
  }

  /**
   * Définit la valeur d'un champ.
   * @param fieldName le nom du champ
   * @param fieldValue la valeur du champ
   */
  public void setField(final String fieldName, final String fieldValue) {
    fields.put(fieldName, fieldValue);
  }
}
