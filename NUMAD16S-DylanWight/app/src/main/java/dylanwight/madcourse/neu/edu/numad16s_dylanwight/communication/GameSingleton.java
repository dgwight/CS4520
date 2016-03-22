package dylanwight.madcourse.neu.edu.numad16s_dylanwight.communication;

import com.firebase.client.Firebase;

/**
 * Created by DylanWight on 3/21/16.
 */
public class GameSingleton {


    private static GameSingleton instance = null;
        private Firebase currentGame;


        private GameSingleton() {
            // Exists only to defeat instantiation.
        }
        public static GameSingleton getInstance() {
            if(instance == null) {
                instance = new GameSingleton();
            }
            return instance;
        }

    public void setCurrentGame(Firebase currentGame) {
        this.currentGame = currentGame;
    }

    public Firebase getCurrentGame() {
        return this.currentGame;
    }
}
