import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

public class RMIRegistry extends UnicastRemoteObject implements ObjectRegistryInterface {
    private static HashMap<String, String> hashMapObject = new HashMap<String, String>();

    @Override
    public void addObject(String objectID, String serverAddress) throws RemoteException {
       if (!(hashMapObject.containsKey(objectID))) {
           hashMapObject.put(objectID,serverAddress);
       }
    }

    @Override
    public String resolve(String objectID) throws RemoteException {
        if(hashMapObject.containsKey(objectID)) {
            return hashMapObject.get(objectID);
        }
        return null;
    }

    protected RMIRegistry() throws RemoteException {
    }

    public static void main (String Args[]) {
        Registry r = null;
        try{
            r = LocateRegistry.createRegistry(2023);
        }catch(RemoteException a){
            a.printStackTrace();
        }

        try{
            RMIRegistry registry = new RMIRegistry();
            r.rebind("registry", registry );

            System.out.println("Place server ready");
        }catch(Exception e) {
            System.out.println("Place server main " + e.getMessage());
        }
    }
}
