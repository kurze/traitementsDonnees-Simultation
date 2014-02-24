package fr.univtours.polytech.di.multimedia.exercices;

import fr.univtours.polytech.di.multimedia.primitives.Processor;

/**
 * Exercice 7
 * @author S�bastien Aupetit
 */
public class Exercice7 implements Exercice {
  /** Le nombre de sonde. */
  static final int nbNetworkRecorders = 10;

  /** Le nombre de processur d'analyse. */
  static final int nbAnomalyDetectors = 5;

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#checkOuputFiles()
   */
  @Override
  public boolean checkOuputFiles() {
    // rien � tester
    return true;
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#configureInputFiles()
   */
  @Override
  public void configureInputFiles() {
    // Rien � faire
  }

  /**
   * @see fr.univtours.polytech.di.multimedia.exercices.Exercice#getProcessor()
   */
  @Override
  public Processor getProcessor() {
    return null;
  }

}
