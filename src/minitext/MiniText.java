package minitext;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.*;

public class MiniText extends JFrame {
    private final JEditorPane eingabeFeld;
    private final MeineAktionen neuAct, oeffnenAct, speichernAct, speichernUnterAct, webAct, beendenAct, infoAct, druckenAct;
    private final HTMLEditorKit htmlFormat;
    private JPopupMenu kontext;
    private File datei;
    private String tempName, permName;
    private String ohneName = " - ohneName";


    class MeineAktionen extends AbstractAction {
        public MeineAktionen(String text, ImageIcon icon, String bildschirm, KeyStroke shortcut, String actionText) {
            super(text, icon);
            putValue(SHORT_DESCRIPTION, bildschirm);
            putValue(ACCELERATOR_KEY, shortcut);
            putValue(ACTION_COMMAND_KEY, actionText);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("neu"))
                dateiNeu();
            if (e.getActionCommand().equals("oeffnen"))
                dateiLaden();
            if (e.getActionCommand().equals("speichern"))
                dateiSpeichern();

            if (e.getActionCommand().equals("speichernUnter"))
                dateiSpechernUnter();
            if (e.getActionCommand().equals("drucken")) {
                if (e.getSource() instanceof JButton)
                    drucken(false);
                if (e.getSource() instanceof JMenuItem)
                    drucken(true);
            }
            if (e.getActionCommand().equals("webladen"))
                webLaden();
            if (e.getActionCommand().equals("info"))
                info();
            if (e.getActionCommand().equals("ende"))
                beenden();

        }
    }

    class MeinKontextMenuListener extends MouseAdapter {
        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            if (e.isPopupTrigger())
                kontext.show(e.getComponent(), e.getX(), e.getY());

        }
    }


    public MiniText(String title) {
        super(title);
        this.permName = getTitle();
        setTitle(permName + ohneName);
        this.tempName = this.permName;


        neuAct = new MeineAktionen("Neu", new ImageIcon("icons/new24.gif"), "Erstellt ein neues Dokument", KeyStroke.getKeyStroke('N', InputEvent.CTRL_DOWN_MASK), "neu");
        oeffnenAct = new MeineAktionen("Öffnen...", new ImageIcon("icons/open24.gif"), "Öffnet ein vorhandenes Dokument", KeyStroke.getKeyStroke('O', InputEvent.CTRL_DOWN_MASK), "oeffnen");
        speichernAct = new MeineAktionen("Speichern...", new ImageIcon("icons/save24.gif"), "Speichert das aktuelles Dokument", KeyStroke.getKeyStroke('S', InputEvent.CTRL_DOWN_MASK), "speichern");
        speichernUnterAct = new MeineAktionen("Speichern unter...", null, "Speichert das aktuelles Dokument", null, "speichernUnter");
        webAct = new MeineAktionen("Webseite...", new ImageIcon("icons/webComponent24.gif"), "Öffnet eine Webseite", null, "webladen");
        beendenAct = new MeineAktionen("Beenden", null, "", null, "ende");
        infoAct = new MeineAktionen("Info", new ImageIcon("icons/information24.gif"), "Info", null, "info");
        druckenAct = new MeineAktionen("Drucken...", new ImageIcon("icons/print24.gif"), "Druckt das aktuelles Dokument", KeyStroke.getKeyStroke('P', InputEvent.CTRL_DOWN_MASK), "drucken");
        setLayout(new BorderLayout());

        eingabeFeld = new JEditorPane();
        htmlFormat = new HTMLEditorKit();
        //eingabeFeld.setEditorKit(htmlFormat);
        eingabeFeld.addMouseListener(new MeinKontextMenuListener());


        add(new JScrollPane(eingabeFeld), BorderLayout.CENTER);
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(600, 200));

        menu();
        kontextMenu();


        add(new JScrollPane(symbolleiste()), BorderLayout.NORTH);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void menu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu dateiMenue = new JMenu("Datei");

        JMenu helpMenue = new JMenu("Hilfe");
        helpMenue.add(infoAct);

        JMenu dateiOeffnen = new JMenu("Öffnen...");
        dateiMenue.add(neuAct);
        dateiMenue.add(dateiOeffnen);
        dateiOeffnen.add(oeffnenAct);
        dateiOeffnen.add(webAct);

        dateiMenue.addSeparator();

        dateiMenue.add(speichernAct);
        dateiMenue.add(speichernUnterAct);

        dateiMenue.addSeparator();

        dateiMenue.add(druckenAct);

        dateiMenue.addSeparator();

        dateiMenue.add(beendenAct);

        menuBar.add(dateiMenue);
        menuBar.add(helpMenue);
        this.setJMenuBar(menuBar);

    }


    private JToolBar symbolleiste() {
        JToolBar leiste = new JToolBar();


        leiste.add(neuAct);
        leiste.add(oeffnenAct);
        leiste.add(speichernAct);
        leiste.add(druckenAct);

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

        /*Action bold = new StyledEditorKit.BoldAction();
        bold.putValue(Action.SHORT_DESCRIPTION, "Fett formatieren");
        bold.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/bold24.gif"));
        leiste.add(bold);

        Action italic = new StyledEditorKit.ItalicAction();
        italic.putValue(Action.SHORT_DESCRIPTION, "Kursiv formatieren");
        italic.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/italic24.gif"));
        leiste.add(italic);

        Action underline = new StyledEditorKit.UnderlineAction();
        underline.putValue(Action.SHORT_DESCRIPTION, "Unterstrichen formatieren");
        underline.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/underline24.gif"));
        leiste.add(underline);

        leiste.addSeparator();

        Action linksAbsatz = new StyledEditorKit.AlignmentAction("Linksbündig", StyleConstants.ALIGN_LEFT);
        linksAbsatz.putValue(Action.SHORT_DESCRIPTION, "Linksbündig ausrichten");
        linksAbsatz.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/alignLeft24.gif"));
        leiste.add(linksAbsatz);


        Action zentriertAbsatz = new StyledEditorKit.AlignmentAction("Zentriert", StyleConstants.ALIGN_CENTER);
        zentriertAbsatz.putValue(Action.SHORT_DESCRIPTION, "Zentriert ausrichten");
        zentriertAbsatz.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/alignCenter24.gif"));
        leiste.add(zentriertAbsatz);


        Action rechtsAbsatz = new StyledEditorKit.AlignmentAction("Rechts", StyleConstants.ALIGN_RIGHT);
        rechtsAbsatz.putValue(Action.SHORT_DESCRIPTION, "Rechtsbündig ausrichten");
        rechtsAbsatz.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/alignRight24.gif"));
        leiste.add(rechtsAbsatz);


        Action blockAbsatz = new StyledEditorKit.AlignmentAction("Blocksatz", StyleConstants.ALIGN_JUSTIFIED);
        blockAbsatz.putValue(Action.SHORT_DESCRIPTION, "Blocksatz ausrichten");
        blockAbsatz.putValue(Action.LARGE_ICON_KEY, new ImageIcon("icons/alignJustify24.gif"));
        leiste.add(blockAbsatz);

         */

        leiste.addSeparator();

        leiste.add(infoAct);


        return (leiste);
    }

    private void kontextMenu() {
        kontext = new JPopupMenu();
        kontext.add(neuAct);
        kontext.add(oeffnenAct);
        kontext.addSeparator();
        kontext.add(webAct);

    }

    private void info() {
        if (JOptionPane.showConfirmDialog(this, "Minitext Version 5.0 \nProgrammiert von: Emre Akdag 2023", "Info", JOptionPane.DEFAULT_OPTION) == JOptionPane.DEFAULT_OPTION)
            eingabeFeld.setText("");
    }

    private void dateiNeu() {
        if (JOptionPane.showConfirmDialog(this, "Wollen Sie wirklich ein neues Dokument anlegen?", "Neues Dokument", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            eingabeFeld.setText("");
            this.datei = null;
            setTitle(permName + ohneName);


        }
    }

    private void dateiLaden() {
        MiniTextDialoge dialog = new MiniTextDialoge();
        File dateiLokal = dialog.oeffnenDialogZeigen();

        if (dateiLokal != null) {
            try {
                eingabeFeld.read(new FileReader(dateiLokal), null);
                this.datei = dateiLokal;
                setTitle(tempName + " - " + datei);

            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Beim Laden hat es ein Problem gegeben", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    private void dateiSpeichern() {
        if (datei == null) {
            MiniTextDialoge dialog = new MiniTextDialoge();
            this.datei = dialog.speichernDialogZeigen();
            setTitle(tempName + " - " + datei);

        }

        if (datei != null) {
            try {
                OutputStream output = new FileOutputStream(this.datei);
                htmlFormat.write(output, eingabeFeld.getDocument(), 0, eingabeFeld.getDocument().getLength());
                eingabeFeld.write(new FileWriter(this.datei));


            } catch (IOException | BadLocationException e) {
                JOptionPane.showConfirmDialog(this, "Beim Speichern hat es ein Problem gegeben", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }


    }

    private void dateiSpechernUnter() {
        MiniTextDialoge dialog = new MiniTextDialoge();
        File dateiLokal = dialog.speichernDialogZeigen();
        if (dateiLokal != null) {
            this.datei = dateiLokal;
            dateiSpeichern();
            setTitle(tempName + " - " + datei);
        }
    }

    private void drucken(boolean dialogZeigen) {
        try {
            if (dialogZeigen == true) {
                eingabeFeld.print();
            } else
                eingabeFeld.print(null, null, false, null, null, true);
        } catch (PrinterException e) {
            JOptionPane.showMessageDialog(this, "Beim Drucken hat es ein Problem gegeben.", "Fehler", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void webLaden() {
        String adresse;
        adresse = JOptionPane.showInputDialog(this, "Bitte geben Sie die URL der Seite ein.");

        if (adresse != null) {
            eingabeFeld.setText("");
            try {
                eingabeFeld.setPage(adresse);
                datei = null;
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Beim Laden ist ein Problem aufgetreten.");
            }
        }


    }


    private void beenden() {
        if (JOptionPane.showConfirmDialog(this, "Sind Sie sicher ?", "Warnung!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
            System.exit(0);
    }
}
