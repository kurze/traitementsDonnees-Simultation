package fr.univtours.polytech.di.multimedia.exercices;

import fr.univtours.polytech.di.multimedia.primitives.Processor;

/**
 * Interface définissant un exercice.
 * @author Sébastien Aupetit
 */
public interface Exercice {

  /**
   * Le nombre par défaut d'enregistrements contenus dans un buffer associé à un
   * fichier.
   */
  public static final int fileBufferSize = 100;

  /**
   * Le nombre par défaut d'enregistrements contenus dans un buffer
   * transmissible via un réseau.
   */
  public static final int networkBufferSize = 16;

  /**
   * Vérifie si les fichiers résultats obtenus après le traitement sont valides.
   * @return true si les fichiers résultats sont valides, false sinon
   */
  public boolean checkOuputFiles();

  /**
   * Crée et initialise les fichiers de données à traiter.
   */
  public void configureInputFiles();

  /**
   * Retourne un processeur configuré prêt à être exécuté.
   * @return le processeur
   */
  public Processor getProcessor();
}
