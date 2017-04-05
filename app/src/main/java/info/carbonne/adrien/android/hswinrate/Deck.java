package info.carbonne.adrien.android.hswinrate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Deck implements Serializable {
    public String Name;
    public int Won;
    public int Lost;

    public Double getWinrate() {
        Double winrate = null;
        if (Won + Lost > 0) {
            winrate = (Won * 100.0) / (Won + Lost);
            BigDecimal bd = new BigDecimal(winrate);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            winrate = bd.doubleValue();
        }
        return  winrate;
    }
}
