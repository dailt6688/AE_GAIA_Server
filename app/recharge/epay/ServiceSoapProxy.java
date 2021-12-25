package recharge.epay;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.Stub;

public class ServiceSoapProxy implements ServiceSoap {
    private String _endpoint = null;
    private ServiceSoap serviceSoap = null;

    public ServiceSoapProxy() {
        _initServiceSoapProxy();
    }

    public ServiceSoapProxy(String endpoint) {
        _endpoint = endpoint;
        _initServiceSoapProxy();
    }

    private void _initServiceSoapProxy() {
        try {
            serviceSoap = (new ServiceLocator()).getServiceSoap();
            if (serviceSoap != null) {
                if (_endpoint != null)
                    ((Stub) serviceSoap)._setProperty("service.endpoint.address", _endpoint);
                else
                    _endpoint = (String) ((Stub) serviceSoap)._getProperty("service.endpoint.address");
            }

        } catch (ServiceException serviceException) {
        }
    }

    public String getEndpoint() {
        return _endpoint;
    }

    public void setEndpoint(String endpoint) {
        _endpoint = endpoint;
        if (serviceSoap != null)
            ((Stub) serviceSoap)._setProperty("service.endpoint.address", _endpoint);

    }

    public ServiceSoap getServiceSoap() {
        if (serviceSoap == null)
            _initServiceSoapProxy();
        return serviceSoap;
    }

    public Reponse deposit(String merchantid, String stan, String termtxndatetime, String txnAmount, String fee, String userName, String issuerID, String tranID, String bankID, String mac, String respUrl) throws RemoteException {
        if (serviceSoap == null)
            _initServiceSoapProxy();
        return serviceSoap.deposit(merchantid, stan, termtxndatetime, txnAmount, fee, userName, issuerID, tranID, bankID, mac, respUrl);
    }

    public Reponse getStatus(String tranid, String merchantcode, String mackey) throws RemoteException {
        if (serviceSoap == null)
            _initServiceSoapProxy();
        return serviceSoap.getStatus(tranid, merchantcode, mackey);
    }

    public Reponse comfirm(String merchantcode, String tranid, String txnAmount, String confirmCode, String mackey) throws RemoteException {
        if (serviceSoap == null)
            _initServiceSoapProxy();
        return serviceSoap.comfirm(merchantcode, tranid, txnAmount, confirmCode, mackey);
    }


}