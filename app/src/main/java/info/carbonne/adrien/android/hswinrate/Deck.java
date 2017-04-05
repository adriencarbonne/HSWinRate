package info.carbonne.adrien.android.hswinrate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Deck implements Serializable {
    public String Name;
    public int Won;
    public int Lost;

    @Override
    public String toString() {
        return Name + " " + getWinRateText();
    }

    public String getWinRateText() {
        Double winrate = getWinrate();
        String winratetext = "No stats yet";
        if (winrate != null) {
            winratetext = Double.toString(winrate) + "% (" + Won + "-" + Lost + ")";
        }
        return winratetext;
    }

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
