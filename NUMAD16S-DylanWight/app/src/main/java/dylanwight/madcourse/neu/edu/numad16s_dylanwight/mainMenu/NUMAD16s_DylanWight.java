package dylanwight.madcourse.neu.edu.numad16s_dylanwight.mainMenu;

import android.app.Application;
import android.content.Context;

/**
 * Created by DylanWight on 2/5/16.
 */
public class NUMAD16s_DylanWight extends Application {

    //private static MyApp instance;
    private static Context mContext;

    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //  instance = this;
        mContext = getApplicationContext();
    }
}
