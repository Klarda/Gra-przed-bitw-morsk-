import java.io.Serializable;
import java.util.ArrayList;

public class Interfejs implements Serializable {
    //    sprawdzanie czy liczba statkow jest rowna liczbie na planszy
    int rozmiar_planszy = 7;
    int statek1 = 4;
    int statek2 = 3;
    int statek3 = 2;
    int statek4 = 1;
    boolean[][] tablica_gracza;
    int[][] tablica_rozwiazana;
    ArrayList<Ruch> lista_ruchow = new ArrayList<>();
    ArrayList<Ruch> lista_cofnietych_ruchow = new ArrayList<>();

    Interfejs(){
        tablica_gracza = new boolean[rozmiar_planszy][rozmiar_planszy];
        tablica_rozwiazana = new int[rozmiar_planszy][rozmiar_planszy];
//        wyzerowanie tablice gracza
        for(int i=0; i<rozmiar_planszy; i++){
            for(int j=0; j<rozmiar_planszy; j++){
                tablica_gracza[i][j]= false;
            }
        }
//        tworzenie tablicy rozwiazana
        for(int i=0; i<rozmiar_planszy; i++){
            for(int j=0; j<rozmiar_planszy; j++){
                tablica_rozwiazana[i][j]= 0;
            }
        }
    }

    void generujPlansze() {
//        pola ze stałą liczba
        tablica_rozwiazana[0][3] = 1;
        tablica_rozwiazana[1][1] = 3;
        tablica_rozwiazana[1][5] = 3;
        tablica_rozwiazana[2][3] = 1;
        tablica_rozwiazana[3][1] = 2;
        tablica_rozwiazana[3][3] = 2;
        tablica_rozwiazana[4][2] = 2;
        tablica_rozwiazana[4][5] = 3;
        tablica_rozwiazana[5][0] = 1;
        tablica_rozwiazana[5][3] = 1;
        tablica_rozwiazana[5][6] = 2;
        tablica_rozwiazana[6][2] = 2;
        tablica_rozwiazana[6][4] = 2;
    }

    boolean sprawdzanie_planszy(){
// czy statki są dobrze rozłożone względem wartości pól
        boolean dobrze_rozlozone_wzgledem_wartosci_pol = sprawdz_czy_statki_sa_dobrze_rozlozone_wzgledem_wartosci_pol();
// czy ilość statków się zgadza
        boolean czy_ilosc_statkow_sie_zgadza = sprawdz_czy_ilosc_statkow_sie_zgadza();

        return (dobrze_rozlozone_wzgledem_wartosci_pol && czy_ilosc_statkow_sie_zgadza);
    }

    boolean sprawdz_czy_statki_sa_dobrze_rozlozone_wzgledem_wartosci_pol(){

//   czy pole[x][y] w tablicy rozwiazanej > 0
        for(int i=0; i<rozmiar_planszy; i++){
            for(int j=0; j<rozmiar_planszy; j++){
                if(tablica_rozwiazana[i][j]>0){
                    if(zliczanieStatkowDookolaLiczby(i,j) != tablica_rozwiazana[i][j]){
                        return false;
                    }
                }
            }
        }

        return true;
    }

    boolean sprawdz_czy_ilosc_statkow_sie_zgadza(){
        //sprawdza zarazem czy statki sie nie stykaja, poniewaz gdy beda sie stykac,
        //zaliczy niepoprawna ilosc statkow i zwroci false
        boolean[][] tablica_gracza_do_sprawdzania = new boolean[7][7] ;
        for (int i = 0; i < 7; i++) { //stworzenie pomoczniej tablicy
            for (int j = 0; j < 7; j++) {
                tablica_gracza_do_sprawdzania[i][j] = tablica_gracza[i][j];
            }
        }
        int _statek1 = 0;
        int _statek2 = 0;
        int _statek3 = 0;
        int _statek4 = 0;

        for(int i=0; i<rozmiar_planszy; i++){
            for(int j=0; j<rozmiar_planszy; j++){
                if(tablica_gracza_do_sprawdzania[i][j]){ //jesli znajdzie pierwsza odpowiedz z lewej, z gory
                    tablica_gracza_do_sprawdzania[i][j] = false;
                    int dlugoscStatku = 1;
                    boolean jestDalejStatekZPrawej = true;
                    boolean jestDalejStatekZDolu = true;

                    for (int k=j+1; k<j+4 && k<rozmiar_planszy; k++){ //sprawdz ile jest dalszych odpowiedzi z prawej
                        if (jestDalejStatekZPrawej) {
                            if (tablica_gracza_do_sprawdzania[i][k]) {
                                dlugoscStatku++;
                                tablica_gracza_do_sprawdzania[i][k] = false;
                            }
                            else if (!tablica_gracza_do_sprawdzania[i][k]){
                                jestDalejStatekZPrawej = false;
                            }
                        }
                    }

                    for (int k=i+1; k<i+4 && k<rozmiar_planszy; k++){ //sprawdz ile jest dalszych odpowiedzi ponizej
                        if (jestDalejStatekZDolu) {
                            if (tablica_gracza_do_sprawdzania[k][j]) {
                                dlugoscStatku++;
                                tablica_gracza_do_sprawdzania[k][j] = false;
                            }
                            else if (!tablica_gracza_do_sprawdzania[k][j]){
                                jestDalejStatekZDolu = false;
                            }
                        }
                    }

                    if (dlugoscStatku == 1) _statek1 ++;
                    if (dlugoscStatku == 2) _statek2 ++;
                    if (dlugoscStatku == 3) _statek3 ++;
                    if (dlugoscStatku == 4) _statek4 ++;
                }
            }
        }

        return _statek1 == statek1 && _statek2 == statek2 && _statek3 == statek3 && _statek4 == statek4;
    }

    int zliczanieStatkowDookolaLiczby(int x, int y){
        int licznik=0;
        if(x-1>=0 && tablica_gracza[x - 1][y])licznik++;
        if(y-1>=0 && tablica_gracza[x][y - 1])licznik++;
        if(x+1<7 && tablica_gracza[x + 1][y])licznik++;
        if(y+1<7 && tablica_gracza[x][y + 1])licznik++;

        return licznik;
    };

    boolean czy_pole_jest_puste(int x, int y){
        if(tablica_rozwiazana[x][y]==0 && tablica_gracza[x][y] == false) {
            return true;
        }
        else{
            return false;
        }
    };

    int wartoscPolaLiczby(int x, int y){
        return tablica_rozwiazana[x][y];
    }

    void zmien_na_statek(int x, int y){
// zmienia pole na true
        tablica_gracza[x][y] = true;
// dodaje to listy Ruchow
        lista_ruchow.add(new Ruch(x,y));
        lista_cofnietych_ruchow.clear();

    };

    boolean czy_to_jest_statek(int x, int y){
        return tablica_gracza[x][y];
    }


    void cofnij_ruch(){
// sprawdza czy lista ruchów jest pusta
        if(lista_ruchow.isEmpty()== false){
// zmniejsza liste ruchów
            Ruch ruch = lista_ruchow.get(lista_ruchow.size()-1);
// dodaje do listy cofniętych ruchów
            lista_cofnietych_ruchow.add(ruch);
            tablica_gracza[ruch.x][ruch.y] = false;
            lista_ruchow.remove(lista_ruchow.size() -1);
        }


    }

}

