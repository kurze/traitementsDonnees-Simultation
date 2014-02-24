package fr.univtours.polytech.di.multimedia.primitives;

import java.io.PrintStream;
import java.util.Vector;

/**
 * Classe définissant les fonctions disponibles pour l'accès aux enregistrements
 * du fichier.
 * @author Sébastien Aupetit
 */
public class File extends Drawable {
  private int recordsRead = 0;
  private int recordsWritten = 0;
  private int buffersRead = 0;
  private int buffersWritten = 0;
  private int ioReadCount = 0;
  private int ioWrittenCount = 0;
  private int ioCount = 0;
  private final String fileIdentifier;

  /**
   * Tableau contenant les enregistrements du fichier.
   */
  private final Vector < Record > content = new Vector < Record >();

  /**
   * Constructeur.
   * @param fileIdentifier Chaîne de caractères permettant d'identifier le
   *          fichier (ex: nom de fichier).
   */
  public File(final String fileIdentifier) {
    this.fileIdentifier = fileIdentifier;
  }

  /**
   * Demande l'affichage des statistiques associées au fichier.
   */
  @Override
  public void displayStatistics() {
    System.out.println("File " + fileIdentifier + " # enregistrements lus : "
        + recordsRead);
    System.out.println("File " + fileIdentifier
        + " # enregistrements écrits : " + recordsWritten);
    System.out.println("File " + fileIdentifier + " # buffers lus : "
        + buffersRead);
    System.out.println("File " + fileIdentifier + " # buffers écrits : "
        + buffersWritten);
    System.out.println("File " + fileIdentifier + " # d'E/S de lecture : "
        + ioReadCount);
    System.out.println("File " + fileIdentifier + " # d'E/S d'écriture : "
        + ioWrittenCount);
    System.out.println("File " + fileIdentifier + " # d'E/S totale : "
        + ioCount);
  }

  /**
   * Génère les instructions DOT/Graphviz associées au fichier afin de créer une
   * représentation graphique du fichier.
   * @param out le flux dans lequel sont écrits les instructions
   * @return l'identifiant unique de l'objet vers lequel doivent se faire les
   *         liaisons (flèches)
   */
  @Override
  public String generateGraphRepresentation(final PrintStream out) {
    final String myNodeId = getNodeId();

    out.println("\t" + myNodeId + " [label=<File " + fileIdentifier + "<br/>"
        + content.size()
        + " records>, shape=folder, fillcolor=lightblue, style=\"filled\"];\n");

    return myNodeId;
  }

  /**
   * Obtenir le nombre d'enregistrement du fichier.
   * @return le nombre d'enregistrement du fichier
   */
  public int getSize() {
    return content.size();
  }

  /**
   * Lit un enregistrement.
   * @param index l'indice de l'enregistrement
   * @param record l'enregistrement dans lequel les informations doivent être
   *          stockées
   * @throws IndexOutOfBoundsException si index n'est pas dans l'intervalle
   *           [0;getSize()-1]
   */
  protected void read(final int index, final Record record)
      throws IllegalArgumentException {
    if ((index < 0) || (index >= content.size())) {
      throw new IndexOutOfBoundsException(
          "Impossible de lire un enregistrement au delà du fichier : index invalide");
    } else {
      record.copy(content.get(index));
      recordsRead++;
    }
  }

  /**
   * Lit le fichier à partir d'une position donnée et essaye de remplir le
   * buffer par son début.
   * @param startPosition la position à partir de laquelle s'effectue la lecture
   *          des enregistrements.
   * @param buffer le buffer à remplir.
   * @return le nombre d'enregistrements effectivement lus.
   * @attention le buffer est rempli par le début.
   */
  public int readLeftAlignedBuffer(final int startPosition, final Buffer buffer) {
    int index = 0;
    final int shift = startPosition;
    final Record record = new Record();

    buffer.invalidateAllRecords();
    while ((index < buffer.getSize()) && (shift + index >= 0)
        && (shift + index < getSize())) {
      read(shift + index, record);
      buffer.setRecord(index, record);
      index++;
    }
    if (index > 0) {
      buffersRead++;
      ioReadCount++;
      ioCount++;
    }
    return index;
  }

  /**
   * Lit un enregistrement dans le fichier.
   * @param position la position de l'enregistrement.
   * @param record l'enregistrement à remplir.
   * @return true si un enregistrement a pu être lu, false sinon.
   */
  public boolean readRecord(final int position, final Record record) {
    if ((position >= 0) && (position < getSize())) {
      read(position, record);
      ioReadCount++;
      ioCount++;
      return true;
    } else {
      return false;
    }
  }

  /**
   * Lit le fichier à partie d'une position données et essaye de remplir le
   * buffer par sa fin.
   * @param endPosition la position dans le fichier du dernier enregistrement
   *          qui doit être mis dans le buffer.
   * @param buffer le buffer à remplis.
   * @return le nombre d'enregistrements effectivement lus.
   * @attention le buffer est rempli par la fin et le fichier est lu de la fin
   *            vers le début.
   */
  public int readRightAlignedBuffer(final int endPosition, final Buffer buffer) {
    final Record record = new Record();
    int index = buffer.getSize() - 1;
    int position = endPosition;
    buffer.invalidateAllRecords();

    while ((index >= 0) && (position >= 0)) {
      read(position, record);
      buffer.setRecord(index, record);
      index--;
      position--;
    }

    if (buffer.getValidRecordCount() > 0) {
      buffersRead++;
      ioReadCount++;
      ioCount++;
    }
    return buffer.getValidRecordCount();
  }

  /**
   * Réinitialize les statistiques à 0.
   */
  public void resetStatistics() {
    recordsRead = 0;
    recordsWritten = 0;
    buffersRead = 0;
    buffersWritten = 0;
    ioReadCount = 0;
    ioWrittenCount = 0;
    ioCount = 0;
  }

  /**
   * Ecrit un enregistrement.
   * @param index l'indice de l'enregistrement
   * @param record l'enregistrement contenant les informations
   * @throws IndexOutOfBoundsException si index n'est pas dans l'intervalle
   *           [0;getSize()]
   */
  protected void write(final int index, final Record record) {
    if (index == content.size()) {
      final Record r = new Record();
      r.copy(record);
      content.add(r);
      recordsWritten++;
    } else {
      if ((index < 0) || (index >= content.size())) {
        throw new IndexOutOfBoundsException(
            "Impossible d'écrire un fichier avec des trous : index invalide");
      } else {
        content.get(index).copy(record);
        recordsWritten++;
      }
    }
  }

  /**
   * Ecrit le contenu d'un buffer dans un fichier en commencant par le début.
   * @param startPosition la position de départ
   * @param buffer le buffer contenant les enregistrements
   * @attantion L'écriture s'arrête au premier enregistrement non utilisable à
   *            partir du début ou lorsque tous les enregistrements ont été
   *            écrits.
   */
  public void writeLeftAlignedBuffer(final int startPosition,
      final Buffer buffer) {
    int index = 0;

    while ((index < buffer.getSize()) && buffer.isValid(index)
        && (startPosition + index <= getSize())) {
      write(startPosition + index, buffer.getRecord(index));
      index++;
    }
    if (buffer.getValidRecordCount() > 0) {
      buffersWritten++;
      ioWrittenCount++;
      ioCount++;
    }
  }

  /**
   * Ecrit un enregistrement dans le fichier.
   * @param position la position de l'enregistrement.
   * @param record l'enregistrement à écrire.
   * @return true si l'enregistrement a pu être écris, false sinon.
   */
  public boolean writeRecord(final int position, final Record record) {
    if ((position >= 0) && (position <= getSize())) {
      write(position, record);
      ioWrittenCount++;
      ioCount++;
      return true;
    } else {
      return false;
    }
  }

}
