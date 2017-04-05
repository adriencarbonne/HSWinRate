package info.carbonne.adrien.android.hswinrate;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    public int Version;
    public int CurrentDeck;
    public ArrayList<Deck> Decks;

    public Data() {
        Version = 1;
        CurrentDeck = 0;
        Decks = new ArrayList<Deck>();
    }

    public void write(Context context) {
        try {
            FileOutputStream fileOut = context.openFileOutput(Constants.DATA.FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Data read(Context context) {
        Data data = null;
        try {
            FileInputStream fileIn = context.openFileInput(Constants.DATA.FILENAME);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            data = (Data) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            data = new Data();
            Deck deck = new Deck();
            deck.Name = "My Great Deck";
            deck.Won = 0;
            deck.Lost = 0;
            data.Decks.add(deck);
        }
        return data;
    }
}
