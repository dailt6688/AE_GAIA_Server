package recharge.epay;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServiceSoap extends Remote {
    Reponse deposit(String merchantid, String stan, String termtxndatetime, String txnAmount, String fee, String userName, String issuerID, String tranID, String bankID, String mac, String respUrl) throws RemoteException;
    Reponse getStatus(String tranid, String merchantcode, String mackey) throws RemoteException;
    Reponse comfirm(String merchantcode, String tranid, String txnAmount, String confirmCode, String mackey) throws RemoteException;
}
