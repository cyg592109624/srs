package android.app;
import android.content.res.Configuration;
import android.os.RemoteException;
/**
 * Created by ChuHui on 2017/9/4.
 */
public abstract interface IActivityManager {
    public abstract Configuration getConfiguration() throws RemoteException;

    public abstract void updateConfiguration(Configuration paramConfiguration)
            throws RemoteException;
}