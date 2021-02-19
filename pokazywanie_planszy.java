import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.*;

class pokazywanie_planszy extends JFrame {
// tworzenie interfejsu, przycisków, pól tekstowych i paneli
    Interfejs plansza = new Interfejs();
    JButton pole[][] = new JButton[7][7] ;
    JButton zapisz = new JButton("Zapisz Grę");
    JButton Pomoc = new JButton("Pomoc");
    JButton Cofnij = new JButton("Cofnij");
    JButton sprawdz = new JButton("Sprawdź");
    JButton wczytaj = new JButton("Wczytaj grę");
    JPanel wyswietlanie = new JPanel();
    JPanel sterowanie = new JPanel();
    JLabel title = new JLabel("Przed bitwą morską");
    JTextField czy_dobrze_tekst = new JTextField(10);

    pokazywanie_planszy() {
        int i, j;
        Container cp = getContentPane();
        cp.setLayout(new GridLayout(1, 2));
        cp.add(wyswietlanie);
        wyswietlanie.setLayout(new GridLayout(7, 7));
//        generowanie planszy z cyframi 7x7
        plansza.generujPlansze();
        for (i = 0; i < 7; i++) {
            for (j = 0; j < 7; j++) {
                pole[i][j] = new JButton("");
                wyswietlanie.add(pole[i][j]);
                (pole[i][j]).addActionListener(new Zaznacz(i, j));
            }
        };
        pokaż_plansze();
//        po kliknieciu zachodzi zmiana
// osbługa przycisków
        cp.add(sterowanie);
        sterowanie.setLayout(new GridLayout(10, 1));
        sterowanie.add(title);
        sterowanie.add(new JLabel(""));
        sterowanie.add(new JLabel(""));
        sterowanie.add(new JLabel(""));
        sterowanie.add(Pomoc);
        Pomoc.addActionListener(new Pomoc());
        sterowanie.add(Cofnij);
        Cofnij.addActionListener(new Cofnij());
        sterowanie.add(wczytaj);
        wczytaj.addActionListener(new Wczytaj());
        sterowanie.add(zapisz);
        zapisz.addActionListener(new Zapisz());
        sterowanie.add(new JLabel(""));
        sterowanie.add(new JLabel(""));
        sterowanie.add(czy_dobrze_tekst);
        sterowanie.add(sprawdz);
        sprawdz.addActionListener(new Sprawdz());

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    void pokaż_plansze(){
//        szuka przez wszystkie elementy
        int i, j;
        for (i = 0; i < 7; i++) {
            for (j = 0; j < 7; j++) {
//        możliwość klikania dla pól bez liczb
                if (plansza.czy_pole_jest_puste(i,j)) {
                    (pole[i][j]).setEnabled(true);
                } else {
//        gdy pole ma liczbę wyświetlamy ją i wyłączamy przycisk
                    pole[i][j].setText(Integer.toString(plansza.wartoscPolaLiczby(i,j)));
                    (pole[i][j]).setEnabled(false);

                }
//        jeśli w danym miejscu jest statek to zmieniamy kolor
                if(plansza.czy_to_jest_statek(i,j)==true){
                    (pole[i][j]).setBackground(Color.GREEN);
                    (pole[i][j]).setText("");
                }
                else{
                    (pole[i][j]).setBackground(null);
                }
                wyswietlanie.add(pole[i][j]);
            }
        }
    };
    class Zapisz implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try{
//      pytanie użytkownika o nazwę pliku
                String nazwa = JOptionPane.showInputDialog("Podaj nazwę pliku: ");
//      zapisujemy do pliku
                FileOutputStream fOut = new FileOutputStream("C:/Users/klard/Documents/Dane/"+nazwa);
                ObjectOutputStream objOut = new ObjectOutputStream(fOut);
                objOut.writeObject(plansza);
                objOut.close();
                fOut.close();
                czy_dobrze_tekst.setText("Zapisano poprawnie!");
            }
// wyjątek gdyby zapis się nie udał
            catch(IOException ioException){
                czy_dobrze_tekst.setText("Nie powiodło się zapisanie!");
            }
        }
    }
    class Sprawdz implements ActionListener {

// wyświetlanie czy dobrze czy źle po sprawdzeniu
        public void actionPerformed(ActionEvent e) {
            if(plansza.sprawdzanie_planszy()){
                czy_dobrze_tekst.setText("Dobrze!");
            }
            else{
                czy_dobrze_tekst.setText("Źle.Spróbuj ponownie!");
            }
        }
    }
    class Wczytaj implements ActionListener {
        public void actionPerformed(ActionEvent e){
            try{
// zapytanie użytkownika o nazwę pliku z zapisaniem
                String nazwa = JOptionPane.showInputDialog("Podaj nazwę pliku: ");
// odczyt z danego pliku
                FileInputStream fIn = new FileInputStream("C:/Users/klard/Documents/Dane/"+nazwa);
                ObjectInputStream objIn = new ObjectInputStream(fIn);
                plansza = (Interfejs) objIn.readObject();
                objIn.close();
                fIn.close();
                czy_dobrze_tekst.setText("Wczytano dane.");
                pokaż_plansze();
            }
// wyjątek
            catch(IOException | ClassNotFoundException i){
                czy_dobrze_tekst.setText("Nie powiodło się wczytywanie!");
            }
        }
    }
    class Zaznacz implements ActionListener {
        int i, j;
        public Zaznacz(int i, int j) {
            this.i = i;
            this.j = j;
        }

        public void actionPerformed(ActionEvent e){
            plansza.zmien_na_statek(i,j);
            pokaż_plansze();
        }
    }
    class Cofnij implements ActionListener {
        public void actionPerformed(ActionEvent e){
            plansza.cofnij_ruch();
            pokaż_plansze();
        }
    }



}
