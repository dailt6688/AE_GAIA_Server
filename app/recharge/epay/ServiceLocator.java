package recharge.epay;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Stub;

public class ServiceLocator extends org.apache.axis.client.Service implements Service {

    public ServiceLocator() {
    }


    public ServiceLocator(EngineConfiguration config) {
        super(config);
    }

    public ServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ServiceSoap
    private String ServiceSoap_address = "http://192.168.0.87:8015/service.asmx";

    public String getServiceSoapAddress() {
        return ServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private String ServiceSoapWSDDServiceName = "ServiceSoap";

    public String getServiceSoapWSDDServiceName() {
        return ServiceSoapWSDDServiceName;
    }

    public void setServiceSoapWSDDServiceName(String name) {
        ServiceSoapWSDDServiceName = name;
    }

    public ServiceSoap getServiceSoap() throws ServiceException {
        URL endpoint;
        try {
            endpoint = new URL(ServiceSoap_address);
        } catch (MalformedURLException e) {
            throw new ServiceException(e);
        }
        return getServiceSoap(endpoint);
    }

    public ServiceSoap getServiceSoap(URL portAddress) {
        try {
            ServiceSoapStub _stub = new ServiceSoapStub(portAddress, this);
            _stub.setPortName(getServiceSoapWSDDServiceName());
            return _stub;
        } catch (AxisFault e) {
            return null;
        }
    }

    public void setServiceSoapEndpointAddress(String address) {
        ServiceSoap_address = address;
    }


    // Use to get a proxy class for ServiceSoap12
    private String ServiceSoap12_address = "http://192.168.0.87:8015/service.asmx";

    public String getServiceSoap12Address() {
        return ServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private String ServiceSoap12WSDDServiceName = "ServiceSoap12";

    public String getServiceSoap12WSDDServiceName() {
        return ServiceSoap12WSDDServiceName;
    }

    public void setServiceSoap12WSDDServiceName(String name) {
        ServiceSoap12WSDDServiceName = name;
    }

    public ServiceSoap getServiceSoap12() throws ServiceException {
        URL endpoint;
        try {
            endpoint = new URL(ServiceSoap12_address);
        } catch (MalformedURLException e) {
            throw new ServiceException(e);
        }
        return getServiceSoap12(endpoint);
    }

    public ServiceSoap getServiceSoap12(URL portAddress) {
        try {
            ServiceSoap12Stub _stub = new ServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getServiceSoap12WSDDServiceName());
            return _stub;
        } catch (AxisFault e) {
            return null;
        }
    }

    public void setServiceSoap12EndpointAddress(String address) {
        ServiceSoap12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
        try {
            if (ServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                ServiceSoapStub _stub = new ServiceSoapStub(new URL(ServiceSoap_address), this);
                _stub.setPortName(getServiceSoapWSDDServiceName());
                return _stub;
            }
            if (ServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                ServiceSoap12Stub _stub = new ServiceSoap12Stub(new URL(ServiceSoap12_address), this);
                _stub.setPortName(getServiceSoap12WSDDServiceName());
                return _stub;
            }
        } catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public Remote getPort(QName portName, Class serviceEndpointInterface) throws ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("ServiceSoap".equals(inputPortName)) {
            return getServiceSoap();
        } else if ("ServiceSoap12".equals(inputPortName)) {
            return getServiceSoap12();
        } else {
            Remote _stub = getPort(serviceEndpointInterface);
            ((Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public QName getServiceName() {
        return new QName("http://tempuri.org/", "Service");
    }

    private HashSet ports = null;

    public Iterator getPorts() {
        if (ports == null) {
            ports = new HashSet();
            ports.add(new QName("http://tempuri.org/", "ServiceSoap"));
            ports.add(new QName("http://tempuri.org/", "ServiceSoap12"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(String portName, String address) throws ServiceException {

        if ("ServiceSoap".equals(portName)) {
            setServiceSoapEndpointAddress(address);
        } else if ("ServiceSoap12".equals(portName)) {
            setServiceSoap12EndpointAddress(address);
        } else { // Unknown Port Name
            throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(QName portName, String address) throws ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}