package recharge.epay;

import java.net.URL;

import javax.xml.rpc.ServiceException;

public interface Service extends javax.xml.rpc.Service {
    String getServiceSoapAddress();

    ServiceSoap getServiceSoap() throws ServiceException;

    ServiceSoap getServiceSoap(URL portAddress) throws ServiceException;
    String getServiceSoap12Address();

    ServiceSoap getServiceSoap12() throws ServiceException;

    ServiceSoap getServiceSoap12(URL portAddress) throws ServiceException;
}
