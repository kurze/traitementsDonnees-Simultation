package fr.univtours.polytech.di.multimedia;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import fr.univtours.polytech.di.multimedia.exercices.Exercice;
import fr.univtours.polytech.di.multimedia.exercices.Exercice1_1;
import fr.univtours.polytech.di.multimedia.exercices.Exercice1_2;
import fr.univtours.polytech.di.multimedia.exercices.Exercice1_3;
import fr.univtours.polytech.di.multimedia.exercices.Exercice2_1;
import fr.univtours.polytech.di.multimedia.exercices.Exercice2_2;
import fr.univtours.polytech.di.multimedia.exercices.Exercice2_3;
import fr.univtours.polytech.di.multimedia.exercices.Exercice3_1;
import fr.univtours.polytech.di.multimedia.exercices.Exercice3_2;
import fr.univtours.polytech.di.multimedia.exercices.Exercice3_3;
import fr.univtours.polytech.di.multimedia.exercices.Exercice4;
import fr.univtours.polytech.di.multimedia.exercices.Exercice5;
import fr.univtours.polytech.di.multimedia.exercices.Exercice6;
import fr.univtours.polytech.di.multimedia.exercices.Exercice7;
import fr.univtours.polytech.di.multimedia.exercices.Exercice8;
import fr.univtours.polytech.di.multimedia.primitives.Processor;

/**
 * La fenêtre principale de l'application.
 * @author Sébastien Aupetit
 */
public class MainFrame extends JFrame {

  private static final long serialVersionUID = 8131618408356111981L;

  /** La console. */
  private final JTextArea console;

  /** L'image. */
  private final JLabel image;

  /**
   * Le constructeur.
   * @param maximizeFrame Indique si la fenêtre est initialement maximisée
   *          (true) ou non (false).
   */
  public MainFrame(final boolean maximizeFrame) {
    super();
    setTitle("Simulation d'algorithme à mémoire externe");
    setMinimumSize(getSize());
    setResizable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBackground(Color.gray);

    getContentPane().setLayout(new BorderLayout(5, 5));
    getContentPane().setLayout(
        new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

    final JPanel exercicePanel = new JPanel();
    final JPanel graphPanel = new JPanel();
    final JPanel consolePanel = new JPanel();

    getContentPane().add(exercicePanel, BorderLayout.NORTH);
    getContentPane().add(graphPanel, BorderLayout.CENTER);
    getContentPane().add(consolePanel, BorderLayout.SOUTH);

    // Exercices
    exercicePanel.setLayout(new BorderLayout());
    exercicePanel.add(new JLabel("Liste des exercices :"), BorderLayout.NORTH);
    final JPanel btnPanel = new JPanel();
    exercicePanel.add(btnPanel, BorderLayout.CENTER);

    // btnPanel
    btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    btnPanel.add(getExerciceButton("Exercice 1.1", new Exercice1_1()));
    btnPanel.add(getExerciceButton("Exercice 1.2", new Exercice1_2()));
    btnPanel.add(getExerciceButton("Exercice 1.3", new Exercice1_3()));

    btnPanel.add(getExerciceButton("Exercice 2.1", new Exercice2_1()));
    btnPanel.add(getExerciceButton("Exercice 2.2", new Exercice2_2()));
    btnPanel.add(getExerciceButton("Exercice 2.3", new Exercice2_3()));

    btnPanel.add(getExerciceButton("Exercice 3.1", new Exercice3_1()));
    btnPanel.add(getExerciceButton("Exercice 3.2", new Exercice3_2()));
    btnPanel.add(getExerciceButton("Exercice 3.3", new Exercice3_3()));

    btnPanel.add(getExerciceButton("Exercice 4", new Exercice4()));

    btnPanel.add(getExerciceButton("Exercice 5", new Exercice5()));

    btnPanel.add(getExerciceButton("Exercice 6", new Exercice6()));

    btnPanel.add(getExerciceButton("Exercice 7", new Exercice7()));

    btnPanel.add(getExerciceButton("Exercice 8", new Exercice8()));

    // graph
    graphPanel.setLayout(new BorderLayout());
    graphPanel.add(new JLabel(
        "Représentation graphique : (cliquer sur l'image pour agrandir)"),
        BorderLayout.NORTH);
    final Icon icon = new ImageIcon("main.gif");
    image = new JLabel(icon);
    image.addMouseListener(new MouseAdapter() {

      @Override
      public void mouseClicked(final MouseEvent e) {
        final JFrame frame = new JFrame("Representation graphique");
        final JLabel zoom = new JLabel(image.getIcon());
        final JScrollPane pane = new JScrollPane();
        pane.getViewport().add(zoom);
        pane.setMinimumSize(new Dimension(300, 300));
        frame.add(pane, BorderLayout.CENTER);
        frame.pack();
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
      }
    });
    final JScrollPane imageScrollpane = new JScrollPane();
    imageScrollpane.getViewport().add(image);
    imageScrollpane.setMinimumSize(new Dimension(100, 200));
    imageScrollpane.setPreferredSize(new Dimension(800, 300));
    imageScrollpane.setMaximumSize(new Dimension(Short.MAX_VALUE, 600));
    graphPanel.add(imageScrollpane, BorderLayout.CENTER);

    // console
    consolePanel.setLayout(new BorderLayout());
    consolePanel.add(new JLabel("Console :"), BorderLayout.NORTH);

    console = new JTextArea();
    console.setEditable(false);
    final JScrollPane consoleScrollpane = new JScrollPane();
    consoleScrollpane.getViewport().add(console);
    consoleScrollpane.setMinimumSize(new Dimension(100, 200));
    consoleScrollpane.setMaximumSize(new Dimension(Short.MAX_VALUE, 600));
    consoleScrollpane.setPreferredSize(new Dimension(800, 300));
    consolePanel.add(consoleScrollpane, BorderLayout.CENTER);

    // Redirection de stdout et stderr vers notre console
    final JTextAreaOutputStream out = new JTextAreaOutputStream(console);
    System.setOut(new PrintStream(out));
    System.setErr(new PrintStream(out));

    // auto organisation de la fenêtre
    pack();

    // doit-on maximiser la fenêtre ?
    if (maximizeFrame) {
      setExtendedState(Frame.MAXIMIZED_BOTH);
    }
  }

  /**
   * Crée un JButton dont l'action exécute l'exercice.
   * <p/>
   * L'exercice est exécuté de façon asynchrone dans un thread de manière à
   * permettre de visualiser les messages sur la console.
   * @param message Le libellé du bouton
   * @param exercice Une instance d'exercice
   * @return l'instance du bouton
   */
  private JButton getExerciceButton(final String message,
      final Exercice exercice) {
    final JButton btn = new JButton(message);
    btn.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(final ActionEvent e) {
        final Runnable runnable = new Runnable() {
          @Override
          public void run() {
            exercice.configureInputFiles();
            final Processor processor = exercice.getProcessor();
            if (processor == null) {
              System.out
                  .println("L'exercice n'est pas implémentée. Réessayez plus tard.");
            } else {
              String fileName;
              try {
                fileName = processor.generateGraph();
                image.setIcon(new ImageIcon(fileName));
                new java.io.File(fileName).delete();
              } catch (final IOException e) {
                e.printStackTrace();
              }
              processor.run();
              processor.displayStatistics();
              if (exercice.checkOuputFiles()) {
                System.out.println("*** implémentation correcte ***");
              } else {
                System.out
                    .println("*** ATTENTION : implémentation incorrecte ***");
              }
              System.out.println("");
            }
          }
        };

        new Thread(runnable).start();
      }
    });
    return btn;
  }
}
