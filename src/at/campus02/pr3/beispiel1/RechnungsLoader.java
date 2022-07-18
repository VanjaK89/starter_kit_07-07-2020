package at.campus02.pr3.beispiel1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RechnungsLoader {
    public static List<Rechnung> loadAll(String fileName) throws RechnungsLoaderException {

        List<Rechnung> rechnungsListe = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            FileReader fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(fileReader);

            String line;
            Rechnung rechnung = null;
            List<RechnungsPosition> rechnungsPositionen = null;
            while ((line = bufferedReader.readLine()) != null) {
                // spalte die einzelnen Begriffe in einzelne Arrayfelder auf
                String[] spalten = line.split(" ");
                String spalte1 = spalten[0];

                // checkt, ob eine neue Rechnung angefangen wird.
                if ("Rechnung".equals(spalte1)) {
                    if (null != rechnung) {
                        // speichert die Vorgängerrechnung in die List
                        rechnung.setPositionen(rechnungsPositionen);
                        rechnungsListe.add(rechnung);
                    }
                    // neue Instanz von der variable Rechnung erstellen.
                    rechnung = new Rechnung();
                    // Rechnungsnummer dazu eintragen
                    String rechnungsNummer = spalten[1];
                    rechnung.setRechnungsNr(rechnungsNummer);

                    rechnungsPositionen = new ArrayList<>();
                } else {
                    // da wir oben abgefragt haben, ob eine neue Rechnung angefangen wird, können wir
                    // davon ausgehen, dass jetzt nur mehr eine Position gelesen werden kann.

                    // hier legen wir eine neue Instanz einer Rechnungsposition an.
                    RechnungsPosition rechnungsposition = new RechnungsPosition();

                    String bezeichnung = spalten[1];
                    rechnungsposition.setBezeichnung(bezeichnung);

                    String strPreis = spalten[2];

                    // so wird der String aus der Datei zu einem double konvertiert.
                    // geht dabei etwas schief, so wird die Exception unten abgefangen
                    Double preis = new Double(strPreis);
                    rechnungsposition.setPreis(preis);

                    rechnungsPositionen.add(rechnungsposition);
                }
            }

            // die letzte Rechnung noch hinzufügen
            if (null != rechnung && null != rechnungsPositionen) {
                rechnung.setPositionen(rechnungsPositionen);
                rechnungsListe.add(rechnung);
            }

        } catch (FileNotFoundException e) {
            throw new RechnungsLoaderException(e);
        } catch (IOException e) {
            throw new RechnungsLoaderException(e);
        } catch (Exception e) {
            throw new RechnungsLoaderException(e);
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return rechnungsListe;
    }

}
