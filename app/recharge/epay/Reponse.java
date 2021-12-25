package recharge.epay;

import javax.xml.namespace.QName;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.Deserializer;
import org.apache.axis.encoding.Serializer;
import org.apache.axis.encoding.ser.BeanDeserializer;
import org.apache.axis.encoding.ser.BeanSerializer;

public class Reponse implements java.io.Serializable {
    private String responsecode;

    private String tranid;

    private String descriptionvn;

    private String descriptionen;

    private String status;

    private String url;

    private String mac;

    public Reponse() {
    }

    public Reponse(String responsecode, String tranid, String descriptionvn, String descriptionen, String status,
                   String url, String mac) {
        this.responsecode = responsecode;
        this.tranid = tranid;
        this.descriptionvn = descriptionvn;
        this.descriptionen = descriptionen;
        this.status = status;
        this.url = url;
        this.mac = mac;
    }

    /**
     * Gets the responsecode value for this Reponse.
     *
     * @return responsecode
     */
    public String getResponsecode() {
        return responsecode;
    }

    /**
     * Sets the responsecode value for this Reponse.
     *
     * @param responsecode
     */
    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode;
    }

    /**
     * Gets the tranid value for this Reponse.
     *
     * @return tranid
     */
    public String getTranid() {
        return tranid;
    }

    /**
     * Sets the tranid value for this Reponse.
     *
     * @param tranid
     */
    public void setTranid(String tranid) {
        this.tranid = tranid;
    }

    /**
     * Gets the descriptionvn value for this Reponse.
     *
     * @return descriptionvn
     */
    public String getDescriptionvn() {
        return descriptionvn;
    }

    /**
     * Sets the descriptionvn value for this Reponse.
     *
     * @param descriptionvn
     */
    public void setDescriptionvn(String descriptionvn) {
        this.descriptionvn = descriptionvn;
    }

    /**
     * Gets the descriptionen value for this Reponse.
     *
     * @return descriptionen
     */
    public String getDescriptionen() {
        return descriptionen;
    }

    /**
     * Sets the descriptionen value for this Reponse.
     *
     * @param descriptionen
     */
    public void setDescriptionen(String descriptionen) {
        this.descriptionen = descriptionen;
    }

    /**
     * Gets the status value for this Reponse.
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status value for this Reponse.
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the url value for this Reponse.
     *
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the url value for this Reponse.
     *
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the mac value for this Reponse.
     *
     * @return mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * Sets the mac value for this Reponse.
     *
     * @param mac
     */
    public void setMac(String mac) {
        this.mac = mac;
    }

    private Object __equalsCalc = null;

    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Reponse))
            return false;
        Reponse other = (Reponse) obj;
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true
                && ((this.responsecode == null && other.getResponsecode() == null)
                || (this.responsecode != null && this.responsecode.equals(other.getResponsecode())))
                && ((this.tranid == null && other.getTranid() == null)
                || (this.tranid != null && this.tranid.equals(other.getTranid())))
                && ((this.descriptionvn == null && other.getDescriptionvn() == null)
                || (this.descriptionvn != null && this.descriptionvn.equals(other.getDescriptionvn())))
                && ((this.descriptionen == null && other.getDescriptionen() == null)
                || (this.descriptionen != null && this.descriptionen.equals(other.getDescriptionen())))
                && ((this.status == null && other.getStatus() == null)
                || (this.status != null && this.status.equals(other.getStatus())))
                && ((this.url == null && other.getUrl() == null)
                || (this.url != null && this.url.equals(other.getUrl())))
                && ((this.mac == null && other.getMac() == null)
                || (this.mac != null && this.mac.equals(other.getMac())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;

    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getResponsecode() != null) {
            _hashCode += getResponsecode().hashCode();
        }
        if (getTranid() != null) {
            _hashCode += getTranid().hashCode();
        }
        if (getDescriptionvn() != null) {
            _hashCode += getDescriptionvn().hashCode();
        }
        if (getDescriptionen() != null) {
            _hashCode += getDescriptionen().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getUrl() != null) {
            _hashCode += getUrl().hashCode();
        }
        if (getMac() != null) {
            _hashCode += getMac().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static TypeDesc typeDesc = new TypeDesc(Reponse.class, true);

    static {
        typeDesc.setXmlType(new QName("http://tempuri.org/", "reponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("responsecode");
        elemField.setXmlName(new QName("http://tempuri.org/", "responsecode"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tranid");
        elemField.setXmlName(new QName("http://tempuri.org/", "tranid"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptionvn");
        elemField.setXmlName(new QName("http://tempuri.org/", "descriptionvn"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("descriptionen");
        elemField.setXmlName(new QName("http://tempuri.org/", "descriptionen"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new QName("http://tempuri.org/", "status"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("url");
        elemField.setXmlName(new QName("http://tempuri.org/", "url"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mac");
        elemField.setXmlName(new QName("http://tempuri.org/", "mac"));
        elemField.setXmlType(new QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static Serializer getSerializer(String mechType, Class _javaType, QName _xmlType) {
        return new BeanSerializer(_javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static Deserializer getDeserializer(String mechType, Class _javaType, QName _xmlType) {
        return new BeanDeserializer(_javaType, _xmlType, typeDesc);
    }

}
