package recharge.epay;

import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis.AxisEngine;
import org.apache.axis.AxisFault;
import org.apache.axis.NoEndPointException;
import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.encoding.DeserializerFactory;
import org.apache.axis.encoding.SerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

public class ServiceSoapStub extends Stub implements ServiceSoap {
    private Vector cachedSerClasses = new Vector();
    private Vector cachedSerQNames = new Vector();
    private Vector cachedSerFactories = new Vector();
    private Vector cachedDeserFactories = new Vector();

    static OperationDesc[] _operations;

    static {
        _operations = new OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1() {
        OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new OperationDesc();
        oper.setName("Deposit");
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "merchantid"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "stan"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "termtxndatetime"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "txnAmount"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "fee"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "userName"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "IssuerID"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "tranID"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "bankID"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "mac"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "respUrl"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://tempuri.org/", "reponse"));
        oper.setReturnClass(Reponse.class);
        oper.setReturnQName(new QName("http://tempuri.org/", "DepositResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[0] = oper;

        oper = new OperationDesc();
        oper.setName("getStatus");
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "tranid"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "merchantcode"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "mackey"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://tempuri.org/", "reponse"));
        oper.setReturnClass(Reponse.class);
        oper.setReturnQName(new QName("http://tempuri.org/", "getStatusResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[1] = oper;

        oper = new OperationDesc();
        oper.setName("comfirm");
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "merchantcode"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "tranid"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "txnAmount"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "confirmCode"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new QName("http://tempuri.org/", "mackey"), org.apache.axis.description.ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://tempuri.org/", "reponse"));
        oper.setReturnClass(Reponse.class);
        oper.setReturnQName(new QName("http://tempuri.org/", "comfirmResult"));
        oper.setStyle(org.apache.axis.constants.Style.WRAPPED);
        oper.setUse(org.apache.axis.constants.Use.LITERAL);
        _operations[2] = oper;

    }

    public ServiceSoapStub() throws AxisFault {
        this(null);
    }

    public ServiceSoapStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    public ServiceSoapStub(javax.xml.rpc.Service service) {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service) super.service).setTypeMappingVersion("1.2");
        Class cls;
        QName qName;
        QName qName2;
        Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
        Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
        Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
        Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
        Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
        Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
        Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
        Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
        Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
        Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
        qName = new QName("http://tempuri.org/", "reponse");
        cachedSerQNames.add(qName);
        cls = Reponse.class;
        cachedSerClasses.add(cls);
        cachedSerFactories.add(beansf);
        cachedDeserFactories.add(beandf);

    }

    protected Call createCall() throws RemoteException {
        try {
            Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                String key = (String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setEncodingStyle(null);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        Class cls = (Class) cachedSerClasses.get(i);
                        QName qName =
                                (QName) cachedSerQNames.get(i);
                        Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            Class sf = (Class)
                                    cachedSerFactories.get(i);
                            Class df = (Class)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        } else if (x instanceof SerializerFactory) {
                            SerializerFactory sf = (SerializerFactory)
                                    cachedSerFactories.get(i);
                            DeserializerFactory df = (DeserializerFactory)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        } catch (Throwable _t) {
            throw new AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public Reponse deposit(String merchantid, String stan, String termtxndatetime, String txnAmount, String fee, String userName, String issuerID, String tranID, String bankID, String mac, String respUrl) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/Deposit");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("http://tempuri.org/", "Deposit"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{merchantid, stan, termtxndatetime, txnAmount, fee, userName, issuerID, tranID, bankID, mac, respUrl});

            if (_resp instanceof RemoteException) {
                throw (RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (Reponse) _resp;
                } catch (Exception _exception) {
                    return (Reponse) JavaUtils.convert(_resp, Reponse.class);
                }
            }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public Reponse getStatus(String tranid, String merchantcode, String mackey) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/getStatus");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("http://tempuri.org/", "getStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{tranid, merchantcode, mackey});

            if (_resp instanceof RemoteException) {
                throw (RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (Reponse) _resp;
                } catch (Exception _exception) {
                    return (Reponse) JavaUtils.convert(_resp, Reponse.class);
                }
            }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public Reponse comfirm(String merchantcode, String tranid, String txnAmount, String confirmCode, String mackey) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/comfirm");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new QName("http://tempuri.org/", "comfirm"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {
            Object _resp = _call.invoke(new Object[]{merchantcode, tranid, txnAmount, confirmCode, mackey});

            if (_resp instanceof RemoteException) {
                throw (RemoteException) _resp;
            } else {
                extractAttachments(_call);
                try {
                    return (Reponse) _resp;
                } catch (Exception _exception) {
                    return (Reponse) JavaUtils.convert(_resp, Reponse.class);
                }
            }
        } catch (AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

}
