package minitext;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MiniText extends JFrame {
    private JTextArea eingabeFeld;


    class MeinListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("neu"))
                dateiNeu();
            if (e.getActionCommand().equals("neuButton"))
                dateiNeu();
            if (e.getActionCommand().equals("laden"))
                dateiLaden();
            if (e.getActionCommand().equals("ladenButton"))
                dateiLaden();
            if (e.getActionCommand().equals("speichern"))
                dateiSpeichern();
            if (e.getActionCommand().equals("speichernButton"))
                dateiSpeichern();
            if (e.getActionCommand().equals("info"))
                info();
            if (e.getActionCommand().equals("infoButton"))
                info();
            if (e.getActionCommand().equals("ende"))
                beenden();
        }
    }

    public MiniText(String title) {
        super(title);
        setLayout(new BorderLayout());
        eingabeFeld = new JTextArea();
        add(new JScrollPane(eingabeFeld), BorderLayout.CENTER);
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(600, 200));

        menu();

        add(new JScrollPane(symbolleiste()), BorderLayout.NORTH);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void menu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu dateiMenue = new JMenu("Datei");
        JMenu helpMenue = new JMenu("Hilfe");
        MeinListener listener = new MeinListener();


        JMenuItem dateiNeu = new JMenuItem();
        dateiNeu.setText("Neu");
        dateiNeu.setIcon(new ImageIcon("icons/new24.gif"));
        dateiNeu.setActionCommand("neu");
        dateiNeu.setToolTipText("Erstellt ein neues Dokument");
        dateiNeu.setAccelerator(KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK));
        dateiNeu.addActionListener(listener);
        dateiMenue.add(dateiNeu);

        JMenuItem dateiLaden = new JMenuItem();
        dateiLaden.setText("Öffnen");
        dateiLaden.setIcon(new ImageIcon("icons/open24.gif"));
        dateiLaden.setActionCommand("laden");
        dateiLaden.setToolTipText("Öffnet ein vorhandenes Dokument");
        dateiLaden.setAccelerator(KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK));
        dateiLaden.addActionListener(listener);
        dateiMenue.add(dateiLaden);

        dateiMenue.addSeparator();

        JMenuItem dateiSpeichern = new JMenuItem();
        dateiSpeichern.setText("Speichern...");
        dateiSpeichern.setIcon(new ImageIcon("icons/save24.gif"));
        dateiSpeichern.setActionCommand("speichern");
        dateiSpeichern.setToolTipText("Speichert das aktuelles Dokument");
        dateiSpeichern.setAccelerator(KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK));
        dateiSpeichern.addActionListener(listener);
        dateiMenue.add(dateiSpeichern);

        JMenuItem dateiSpeichernUnter = new JMenuItem();
        dateiSpeichernUnter.setText("Speichern unter...");
        dateiSpeichernUnter.setActionCommand("speichernUnter");
        dateiSpeichernUnter.setToolTipText("Speichert das aktuelles Dokument");
        dateiSpeichernUnter.addActionListener(listener);
        dateiMenue.add(dateiSpeichernUnter);

        dateiMenue.addSeparator();

        JMenuItem beenden = new JMenuItem();
        beenden.setText("Beenden");
        beenden.setActionCommand("ende");
        beenden.addActionListener(listener);
        dateiMenue.add(beenden);

        JMenuItem info = new JMenuItem();
        info.setText("Info");
        info.setIcon(new ImageIcon("icons/information24.gif"));
        info.setActionCommand("info");
        info.addActionListener(listener);
        helpMenue.add(info);


        menuBar.add(dateiMenue);
        menuBar.add(helpMenue);
        this.setJMenuBar(menuBar);

    }

    private JToolBar symbolleiste() {
        JToolBar leiste = new JToolBar();
        MeinListener listener = new MeinListener();

        JButton dateiNeuButton = new JButton();
        dateiNeuButton.setActionCommand("neuButton");
        dateiNeuButton.setToolTipText("Erstellt ein neues Dokument");
        dateiNeuButton.setIcon(new ImageIcon("icons/new24.gif"));
        dateiNeuButton.addActionListener(listener);
        leiste.add(dateiNeuButton);

        JButton dateiLadenButton = new JButton();
        dateiLadenButton.setActionCommand("ladenButton");
        dateiLadenButton.setToolTipText("Öffnet ein vorhandenes Dokument");
        dateiLadenButton.setIcon(new ImageIcon("icons/open24.gif"));
        dateiLadenButton.addActionListener(listener);
        leiste.add(dateiLadenButton);

        JButton dateiSpeichernButton = new JButton();
        dateiSpeichernButton.setActionCommand("speichernButton");
        dateiSpeichernButton.setToolTipText("Speichert das aktuelles Dokument");
        dateiSpeichernButton.addActionListener(listener);
        dateiSpeichernButton.setIcon(new ImageIcon("icons/save24.gif"));
        leiste.add(dateiSpeichernButton);

        leiste.addSeparator();

        Action cut = new DefaultEditorKit.CutAction();
        cut.putValue(Action.SHORT_DESCRIPTION, "Cut");
        cut.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/cut24.gif"));
        leiste.add(cut);

        Action copy = new DefaultEditorKit.CopyAction();
        copy.putValue(Action.SHORT_DESCRIPTION, "Copy");
        copy.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/copy24.gif"));
        leiste.add(copy);

        Action paste = new DefaultEditorKit.PasteAction();
        paste.putValue(Action.SHORT_DESCRIPTION, "Paste");
        paste.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/paste24.gif"));
        leiste.add(paste);

        leiste.addSeparator();

        JButton infoButton = new JButton();
        infoButton.setActionCommand("infoButton");
        infoButton.setToolTipText("Info");
        infoButton.setIcon(new ImageIcon("icons/information24.gif"));
        infoButton.addActionListener(listener);
        leiste.add(infoButton);


        return (leiste);
    }

    private void info() {
        if (JOptionPane.showConfirmDialog(this, "Minitext Version 5.0 \nProgrammiert von: Emre Akdag 2023", "Info", JOptionPane.DEFAULT_OPTION) == JOptionPane.DEFAULT_OPTION)
            eingabeFeld.setText("");
    }

    private void dateiNeu() {
        if (JOptionPane.showConfirmDialog(this, "Wollen Sie wirklich ein neues Dokument anlegen?", "Neues Dokument", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            eingabeFeld.setText("");
    }

    private void dateiLaden() {
        MiniTextDialoge dialog = new MiniTextDialoge();
        File datei = dialog.oeffnenDialogZeigen();

        if (datei != null) {
            try {
                eingabeFeld.read(new FileReader(datei), null);
            } catch (IOException e) {
                JOptionPane.showConfirmDialog(this, "Beim Laden hat es ein Problem gegeben", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    private void dateiSpeichern() {
        MiniTextDialoge dialog = new MiniTextDialoge();
        File datei = dialog.speichernDialogZeigen();

        if (datei != null) {
            try {
                eingabeFeld.write(new FileWriter(datei));
            } catch (IOException e) {
                JOptionPane.showConfirmDialog(this, "Beim Speichern hat es ein Problem gegeben", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void beenden() {
        if (JOptionPane.showConfirmDialog(this, "Wollen Sie die Notiz wirklich schließen ?", "Warnung !", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            System.exit(0);
    }
}
