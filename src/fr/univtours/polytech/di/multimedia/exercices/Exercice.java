package fr.univtours.polytech.di.multimedia.exercices;

import fr.univtours.polytech.di.multimedia.primitives.Processor;

/**
 * Interface d�finissant un exercice.
 * @author S�bastien Aupetit
 */
public interface Exercice {

  /**
   * Le nombre par d�faut d'enregistrements contenus dans un buffer associ� � un
   * fichier.
   */
  public static final int fileBufferSize = 100;

  /**
   * Le nombre par d�faut d'enregistrements contenus dans un buffer
   * transmissible via un r�seau.
   */
  public static final int networkBufferSize = 16;

  /**
   * V�rifie si les fichiers r�sultats obtenus apr�s le traitement sont valides.
   * @return true si les fichiers r�sultats sont valides, false sinon
   */
  public boolean checkOuputFiles();

  /**
   * Cr�e et initialise les fichiers de donn�es � traiter.
   */
  public void configureInputFiles();

  /**
   * Retourne un processeur configur� pr�t � �tre ex�cut�.
   * @return le processeur
   */
  public Processor getProcessor();
}
