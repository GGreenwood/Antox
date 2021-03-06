package im.tox.antox;

import android.nfc.Tag;
import android.util.Log;

import java.net.UnknownHostException;

import im.tox.jtoxcore.JTox;
import im.tox.jtoxcore.ToxException;
import im.tox.jtoxcore.ToxUserStatus;
import im.tox.jtoxcore.callbacks.CallbackHandler;

/**
 * Created by soft on 01/03/14.
 */
public class ToxSingleton {

    private static final String TAG = "im.tox.antox.ToxSingleton";
    public JTox jTox;
    private AntoxFriendList antoxFriendList;
    public CallbackHandler callbackHandler;

    private static volatile ToxSingleton instance = null;

    private ToxSingleton() {
        antoxFriendList = new AntoxFriendList();
        callbackHandler = new CallbackHandler(antoxFriendList);
        try {
            ToxDataFile dataFile = new ToxDataFile();

            /* Choose appropriate constructor depending on if data file exists */
            if(!dataFile.doesFileExist()) {
                Log.d(TAG, "Data file not found");
                jTox = new JTox(antoxFriendList, callbackHandler);
            } else {
                Log.d(TAG, "Data file has been found");
                if(dataFile.isExternalStorageReadable())
                    jTox = new JTox(dataFile.loadFile(), antoxFriendList, callbackHandler);
                else
                    Log.d(TAG, "Data file wasn't available for reading");
            }

            if(UserDetails.username == null)
                UserDetails.username = "antoxUser";
            jTox.setName(UserDetails.username);

            if(UserDetails.note == null)
                UserDetails.note = "using antox";
            jTox.setStatusMessage(UserDetails.note);

            if(UserDetails.status == null)
                UserDetails.status = ToxUserStatus.TOX_USERSTATUS_NONE;
            jTox.setUserStatus(UserDetails.status);

            //jTox.bootstrap("144.76.60.215", 33445, "04119E835DF3E78BACF0F84235B300546AF8B936F035185E2A8E9E0A67C8924F");
            jTox.bootstrap(DhtNode.ipv4, Integer.parseInt(DhtNode.port), DhtNode.key);

            /* Save data file */
            if(dataFile.isExternalStorageWritable())
                dataFile.saveFile(jTox.save());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (ToxException e) {
            e.printStackTrace();
            Log.d(TAG, e.getError().toString());
        }
    }

    public static ToxSingleton getInstance() {
        /* Double-checked locking */
        if(instance == null) {
            synchronized (ToxSingleton.class) {
                if(instance == null) {
                    instance = new ToxSingleton();
                }
            }
        }

        return instance;
    }
}
