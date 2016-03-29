package dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication;

public class CommunicationConstants
{
    public static final String TAG = "GCM_Globals";
    public static final String GCM_SENDER_ID = "554684504341";
    public static final String BASE_URL = "https://android.googleapis.com/gcm/send";
    public static final String PREFS_NAME = "GCM_Communication";

    // replace it with your own gcm api key here
    public static final String GCM_API_KEY = "AIzaSyD8wc9PFh7jSFBZd6KvgP64cE6502al2go";
    public static final int SIMPLE_NOTIFICATION = 22;
    public static final long GCM_TIME_TO_LIVE = 60L * 60L * 24L * 7L * 4L; // 4 Weeks
    public static int mode = 0;

    public static String alertText = "";
    public static String titleText = "";
    public static String contentText = "";
}