package recharge.epay;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Vector;

import javax.xml.namespace.QName;

import org.apache.axis.client.Call;
import org.apache.axis.client.Stub;
import org.apache.axis.constants.Style;
import org.apache.axis.constants.Use;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.ser.ArrayDeserializerFactory;
import org.apache.axis.encoding.ser.ArraySerializerFactory;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.encoding.ser.EnumDeserializerFactory;
import org.apache.axis.encoding.ser.EnumSerializerFactory;
import org.apache.axis.encoding.ser.SimpleDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListDeserializerFactory;
import org.apache.axis.encoding.ser.SimpleListSerializerFactory;
import org.apache.axis.encoding.ser.SimpleSerializerFactory;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis.utils.JavaUtils;

public class ServiceSoap12Stub extends Stub implements ServiceSoap {
    private Vector cachedSerClasses = new Vector();
    private Vector cachedSerQNames = new Vector();
    private Vector cachedSerFactories = new Vector();
    private Vector cachedDeserFactories = new Vector();

    static OperationDesc [] _operations;

    static {
        _operations = new OperationDesc[3];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        OperationDesc oper;
        ParameterDesc param;
        oper = new OperationDesc();
        oper.setName("Deposit");
        param = new ParameterDesc(new QName("http://tempuri.org/", "merchantid"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "stan"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "termtxndatetime"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "txnAmount"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "fee"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "userName"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "IssuerID"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "tranID"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "bankID"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "mac"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "respUrl"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://tempuri.org/", "reponse"));
        oper.setReturnClass(Reponse.class);
        oper.setReturnQName(new QName("http://tempuri.org/", "DepositResult"));
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);
        _operations[0] = oper;

        oper = new OperationDesc();
        oper.setName("getStatus");
        param = new ParameterDesc(new QName("http://tempuri.org/", "tranid"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "merchantcode"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "mackey"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://tempuri.org/", "reponse"));
        oper.setReturnClass(Reponse.class);
        oper.setReturnQName(new QName("http://tempuri.org/", "getStatusResult"));
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);
        _operations[1] = oper;

        oper = new OperationDesc();
        oper.setName("comfirm");
        param = new ParameterDesc(new QName("http://tempuri.org/", "merchantcode"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "tranid"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "txnAmount"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "confirmCode"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        param = new ParameterDesc(new QName("http://tempuri.org/", "mackey"), ParameterDesc.IN, new QName("http://www.w3.org/2001/XMLSchema", "string"), String.class, false, false);
        param.setOmittable(true);
        oper.addParameter(param);
        oper.setReturnType(new QName("http://tempuri.org/", "reponse"));
        oper.setReturnClass(Reponse.class);
        oper.setReturnQName(new QName("http://tempuri.org/", "comfirmResult"));
        oper.setStyle(Style.WRAPPED);
        oper.setUse(Use.LITERAL);
        _operations[2] = oper;

    }

    public ServiceSoap12Stub() throws org.apache.axis.AxisFault {
        this(null);
    }

    public ServiceSoap12Stub(URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        this(service);
        super.cachedEndpoint = endpointURL;
    }

    public ServiceSoap12Stub(javax.xml.rpc.Service service) {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.2");
        Class cls;
        QName qName;
        QName qName2;
        Class beansf = BeanSerializerFactory.class;
        Class beandf = BeanDeserializerFactory.class;
        Class enumsf = EnumSerializerFactory.class;
        Class enumdf = EnumDeserializerFactory.class;
        Class arraysf = ArraySerializerFactory.class;
        Class arraydf = ArrayDeserializerFactory.class;
        Class simplesf = SimpleSerializerFactory.class;
        Class simpledf = SimpleDeserializerFactory.class;
        Class simplelistsf = SimpleListSerializerFactory.class;
        Class simplelistdf = SimpleListDeserializerFactory.class;
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
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                    cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                    cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public Reponse deposit(String merchantid, String stan, String termtxndatetime, String txnAmount, String fee, String userName, String issuerID, String tranID, String bankID, String mac, String respUrl) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/Deposit");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new QName("http://tempuri.org/", "Deposit"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        Object _resp = _call.invoke(new Object[] {merchantid, stan, termtxndatetime, txnAmount, fee, userName, issuerID, tranID, bankID, mac, respUrl});

            if (_resp instanceof RemoteException) {
                throw (RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (Reponse) _resp;
                } catch (Exception _exception) {
                    return (Reponse) JavaUtils.convert(_resp, Reponse.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public Reponse getStatus(String tranid, String merchantcode, String mackey) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/getStatus");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new QName("http://tempuri.org/", "getStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        Object _resp = _call.invoke(new Object[] {tranid, merchantcode, mackey});

            if (_resp instanceof RemoteException) {
                throw (RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (Reponse) _resp;
                } catch (Exception _exception) {
                    return (Reponse) JavaUtils.convert(_resp, Reponse.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

    public Reponse comfirm(String merchantcode, String tranid, String txnAmount, String confirmCode, String mackey) throws RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("http://tempuri.org/comfirm");
        _call.setEncodingStyle(null);
        _call.setProperty(Call.SEND_TYPE_ATTR, Boolean.FALSE);
        _call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        _call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
        _call.setOperationName(new QName("http://tempuri.org/", "comfirm"));

        setRequestHeaders(_call);
        setAttachments(_call);
        try {        Object _resp = _call.invoke(new Object[] {merchantcode, tranid, txnAmount, confirmCode, mackey});

            if (_resp instanceof RemoteException) {
                throw (RemoteException)_resp;
            }
            else {
                extractAttachments(_call);
                try {
                    return (Reponse) _resp;
                } catch (Exception _exception) {
                    return (Reponse) JavaUtils.convert(_resp, Reponse.class);
                }
            }
        } catch (org.apache.axis.AxisFault axisFaultException) {
            throw axisFaultException;
        }
    }

}