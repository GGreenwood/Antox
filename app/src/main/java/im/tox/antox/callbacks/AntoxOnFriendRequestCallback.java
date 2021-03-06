package im.tox.antox.callbacks;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import im.tox.antox.Constants;
import im.tox.antox.ToxService;
import im.tox.jtoxcore.callbacks.OnFriendRequestCallback;

/**
 * Created by soft on 02/03/14.
 */
public class AntoxOnFriendRequestCallback implements OnFriendRequestCallback {

    private static final String TAG = "im.tox.antox.TAG";
    public static final String FRIEND_KEY = "im.tox.antox.FRIEND_KEY";
    public static final String FRIEND_MESSAGE = "im.tox.antox.FRIEND_MESSAGE";

    private Context ctx;

    public AntoxOnFriendRequestCallback(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public void execute(String publicKey, String message){
        Log.d(TAG, "Friend request callback");
    }
}
