import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Pomoc implements ActionListener {
    public void actionPerformed(ActionEvent e){
// tworzenie okna  i przypisanie mu tekstu
        JFrame parent = new JFrame();
        JOptionPane.showMessageDialog(parent, "Na pustych polach diagramu należy rozmieścić flotyllę złożoną z dziesięciu okrętów:\n" +
                "jednego pancernika - zajmującego cztery kratki, dwóch krążowników - zajmujących po trzy kratki,  \n" + "trzech niszczycieli - każdy mieści się na dwóch kratkach i czterech łodzi podwodnych - każda w jednej kratce. \n"+"  Cyfra w danym polu wskazuje, ile sąsiednich pól (mających wspólny bok z polem z cyfrą) ma być zajętych przez części floty. \n"+"Ponadto, dwu pól mających wspólny bok nie mogą zająć dwa różne okręty.");
    }
}