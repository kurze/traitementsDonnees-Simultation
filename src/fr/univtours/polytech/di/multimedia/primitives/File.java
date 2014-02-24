package fr.univtours.polytech.di.multimedia.primitives;

import java.io.PrintStream;
import java.util.Vector;

/**
 * Classe d�finissant les fonctions disponibles pour l'acc�s aux enregistrements
 * du fichier.
 * @author S�bastien Aupetit
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
   * @param fileIdentifier Cha�ne de caract�res permettant d'identifier le
   *          fichier (ex: nom de fichier).
   */
  public File(final String fileIdentifier) {
    this.fileIdentifier = fileIdentifier;
  }

  /**
   * Demande l'affichage des statistiques associ�es au fichier.
   */
  @Override
  public void displayStatistics() {
    System.out.println("File " + fileIdentifier + " # enregistrements lus : "
        + recordsRead);
    System.out.println("File " + fileIdentifier
        + " # enregistrements �crits : " + recordsWritten);
    System.out.println("File " + fileIdentifier + " # buffers lus : "
        + buffersRead);
    System.out.println("File " + fileIdentifier + " # buffers �crits : "
        + buffersWritten);
    System.out.println("File " + fileIdentifier + " # d'E/S de lecture : "
        + ioReadCount);
    System.out.println("File " + fileIdentifier + " # d'E/S d'�criture : "
        + ioWrittenCount);
    System.out.println("File " + fileIdentifier + " # d'E/S totale : "
        + ioCount);
  }

  /**
   * G�n�re les instructions DOT/Graphviz associ�es au fichier afin de cr�er une
   * repr�sentation graphique du fichier.
   * @param out le flux dans lequel sont �crits les instructions
   * @return l'identifiant unique de l'objet vers lequel doivent se faire les
   *         liaisons (fl�ches)
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
   * @param record l'enregistrement dans lequel les informations doivent �tre
   *          stock�es
   * @throws IndexOutOfBoundsException si index n'est pas dans l'intervalle
   *           [0;getSize()-1]
   */
  protected void read(final int index, final Record record)
      throws IllegalArgumentException {
    if ((index < 0) || (index >= content.size())) {
      throw new IndexOutOfBoundsException(
          "Impossible de lire un enregistrement au del� du fichier : index invalide");
    } else {
      record.copy(content.get(index));
      recordsRead++;
    }
  }

  /**
   * Lit le fichier � partir d'une position donn�e et essaye de remplir le
   * buffer par son d�but.
   * @param startPosition la position � partir de laquelle s'effectue la lecture
   *          des enregistrements.
   * @param buffer le buffer � remplir.
   * @return le nombre d'enregistrements effectivement lus.
   * @attention le buffer est rempli par le d�but.
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
   * @param record l'enregistrement � remplir.
   * @return true si un enregistrement a pu �tre lu, false sinon.
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
   * Lit le fichier � partie d'une position donn�es et essaye de remplir le
   * buffer par sa fin.
   * @param endPosition la position dans le fichier du dernier enregistrement
   *          qui doit �tre mis dans le buffer.
   * @param buffer le buffer � remplis.
   * @return le nombre d'enregistrements effectivement lus.
   * @attention le buffer est rempli par la fin et le fichier est lu de la fin
   *            vers le d�but.
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
   * R�initialize les statistiques � 0.
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
            "Impossible d'�crire un fichier avec des trous : index invalide");
      } else {
        content.get(index).copy(record);
        recordsWritten++;
      }
    }
  }

  /**
   * Ecrit le contenu d'un buffer dans un fichier en commencant par le d�but.
   * @param startPosition la position de d�part
   * @param buffer le buffer contenant les enregistrements
   * @attantion L'�criture s'arr�te au premier enregistrement non utilisable �
   *            partir du d�but ou lorsque tous les enregistrements ont �t�
   *            �crits.
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
   * @param record l'enregistrement � �crire.
   * @return true si l'enregistrement a pu �tre �cris, false sinon.
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
