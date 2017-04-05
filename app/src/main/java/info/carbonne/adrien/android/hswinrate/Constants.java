package info.carbonne.adrien.android.hswinrate;

public class Constants {
    public interface ACTION {
        public static String DONOTHING_ACTION = "info.carbonne.adrien.android.hswinrate.action.donothing";
        public static String WON_ACTION = "info.carbonne.adrien.android.hswinrate.action.won";
        public static String LOST_ACTION = "info.carbonne.adrien.android.hswinrate.action.lost";
        public static String EXIT_ACTION = "info.carbonne.adrien.android.hswinrate.action.exit";

        public static String STARTFOREGROUND_ACTION = "info.carbonne.adrien.android.hswinrate.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "info.carbonne.adrien.android.hswinrate.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int ONGOING_NOTIFICATION_ID = 777;
    }
}
