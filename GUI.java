import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class GUI implements ActionListener{
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JPanel left;
    private JPanel right;
    private JPanel down;
    private JPanel aktuelleAufgabe;
    private JPanel neueAufgabe;
    private JPanel ausgabe;
    private JPanel controlButtons;
    private JButton[] buttons;
    private JTextArea taAusgabe;
    private JTextField tfAktuelleAufgabe;
    private JTextField tfNeueAusgabe;
    private JLabel lbaktuelleAufgabe;
    private JLabel lbNeueAufgabe;
    private List<String> toDoListe = new List<String>();

    public GUI() {
        createGUI();
        testListeErzeugen();
        ausgeben();
        aktuellesAusgeben();
    }

    private void createGUI() {
        mainFrame = new JFrame("To-Do-Liste");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setSize(700, 550);
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);

        mainPanel = new JPanel(new BorderLayout());
        mainFrame.add(mainPanel, BorderLayout.CENTER);
        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(10, 200));
        mainFrame.add(space, BorderLayout.WEST);
        left = new JPanel(new GridLayout(2, 1, 5, 5));
        left.setPreferredSize(new Dimension(300, 490));
        mainPanel.add(left, BorderLayout.WEST);
        right = new JPanel();
        right.setPreferredSize(new Dimension(360, 490));
        mainPanel.add(right, BorderLayout.EAST);

        down = new JPanel(new FlowLayout());
        mainPanel.add(down, BorderLayout.SOUTH);
        aktuelleAufgabe = new JPanel(new GridLayout(5, 1, 10, 10));
        aktuelleAufgabe.setPreferredSize(new Dimension(250, 220));
        left.add(aktuelleAufgabe);
        neueAufgabe = new JPanel(new GridLayout(4, 1, 15, 15));
        neueAufgabe.setPreferredSize(new Dimension(250, 220));
        left.add(neueAufgabe);
        taAusgabe = new JTextArea();
        lbaktuelleAufgabe = new JLabel("Aktuelle Aufgabe");
        lbNeueAufgabe = new JLabel("Neue Aufgabe");
        tfAktuelleAufgabe = new JTextField();
        tfNeueAusgabe = new JTextField();
        controlButtons = new JPanel(new FlowLayout());

        buttons = new JButton[9];
        String[] buttonText = {"|<", ">", ">|", "Ändern", "Löschen", "Davor einfügen", "Dahinter einfügen", "Liste speichern", "Liste laden"};
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonText[i]);
            buttons[i].addActionListener(this);
        }

        aktuelleAufgabe.add(lbaktuelleAufgabe);
        aktuelleAufgabe.add(tfAktuelleAufgabe);
        aktuelleAufgabe.add(controlButtons);

        for (int i = 0; i < 5; i++) {
            if (i < 3) {
                controlButtons.add(buttons[i]);
            } else {
                aktuelleAufgabe.add(buttons[i]);
            }
        }

        neueAufgabe.add(lbNeueAufgabe);
        neueAufgabe.add(tfNeueAusgabe);

        for (int i = 5; i < 7; i++) {
            neueAufgabe.add(buttons[i]);
        }
        taAusgabe.setPreferredSize(new Dimension(350, 450));
        taAusgabe.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        taAusgabe.setFont(new Font("Arial", Font.PLAIN, 15));
        right.add(taAusgabe);
        down.add(buttons[7]);
        down.add(buttons[8]);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void testListeErzeugen() {
        toDoListe = new List<String>();
        toDoListe.append("Referat zur Kryptologie anfertigen");
        toDoListe.append("Geburtstagseinladungen verschicken");
        toDoListe.append("Für Deutscharbeit üben");
        toDoListe.append("Opa anrufen");
        toDoListe.append("Hamster füttern");
        toDoListe.append("Javabuch kaufen");
    }

    private void aktuellesAusgeben() {
        taAusgabe.append(toDoListe.getContent());
    }

    private void zumAnfangDerListe() {
        toDoListe.toFirst();
        tfAktuelleAufgabe.setText(toDoListe.getContent());
    }

    private void naechstes() {
        toDoListe.next();
        if (!toDoListe.getContent().isEmpty()){
            tfAktuelleAufgabe.setText(toDoListe.getContent());
        }
    }

    private void zumEndeDerListe() {
        toDoListe.toLast();
        tfAktuelleAufgabe.setText(toDoListe.getContent());
    }

    private void aendern() {
        if (toDoListe.hasAccess()) {
            String aenderung = tfAktuelleAufgabe.getText();
            if (!aenderung.isEmpty()) {
                toDoListe.setContent(aenderung);
                taAusgabe.setText("");
                ausgeben();
            }
        }
    }

    private void loeschen() {
        toDoListe.remove();
        taAusgabe.setText("");
        if (toDoListe.hasAccess()){
            toDoListe.next();
        } else {
            tfAktuelleAufgabe.setText("");
        }
        tfAktuelleAufgabe.setText(toDoListe.getContent());
        ausgeben();
    }

    private void davorEinfuegen(String pAufgabe) {
        if (!pAufgabe.isEmpty() && toDoListe.hasAccess()) {
            toDoListe.insert(pAufgabe);
            taAusgabe.setText("");
            tfNeueAusgabe.setText("");
            ausgeben();
        }
    }

    private void dahinterEinfuegen(String pAufgabe) {
        if (!pAufgabe.isEmpty() && toDoListe.hasAccess()) {
            toDoListe.next();
            toDoListe.insert(pAufgabe);
            taAusgabe.setText("");
            tfNeueAusgabe.setText("");
            ausgeben();
        }
    }

    private void ausgeben() {
        toDoListe.toFirst();
        while (toDoListe.hasAccess()) {
            taAusgabe.append(toDoListe.getContent() + "\n");
            toDoListe.next();
        }
    }

    public void listeLaden(File pFile) {
        //Hier Quellcode eingeben
    }

    public void listSpeichern(File pFile) {
        //Hier Quellcode eingeben
    }

    public void actionPerformed(ActionEvent e) {
        JButton temp = (JButton) e.getSource();
        switch (temp.getText()) {
            case "|<":
                zumAnfangDerListe();
                break;

            case ">":
                naechstes();
                break;

            case ">|":
                zumEndeDerListe();
                break;

            case "Ändern":
                aendern();
                break;

            case "Löschen":
                loeschen();
                break;

            case "Davor einfügen":
                davorEinfuegen(tfNeueAusgabe.getText());
                break;

            case "Dahinter einfügen":
                dahinterEinfuegen(tfNeueAusgabe.getText());
                break;

            case "Liste laden":
                //Hier Quellcode eingeben
                break;

            case "Liste speichern":
                //Hier Quellcode eingeben
                break;
        }
    }

    public static void main(String[] args) {
        new GUI();
    }
}
