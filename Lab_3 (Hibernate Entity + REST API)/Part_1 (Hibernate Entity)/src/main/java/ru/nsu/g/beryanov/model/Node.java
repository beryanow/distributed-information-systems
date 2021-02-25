package ru.nsu.g.beryanov.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"tag"})
@XmlRootElement(name = "node")
public class Node {
    @XmlElement(required = true)
    protected List<Node.Tag> tag;
    @XmlAttribute(name = "id")
    protected Integer id;
    @XmlAttribute(name = "version")
    protected Integer version;
    @XmlAttribute(name = "timestamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlAttribute(name = "uid")
    protected Integer uid;
    @XmlAttribute(name = "user")
    protected String user;
    @XmlAttribute(name = "changeset")
    protected Integer changeset;
    @XmlAttribute(name = "lat")
    protected Double lat;
    @XmlAttribute(name = "lon")
    protected Double lon;

    public List<Node.Tag> getTag() {
        if (tag == null) {
            tag = new ArrayList<Node.Tag>();
        }
        return this.tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer value) {
        this.version = value;
    }

    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer value) {
        this.uid = value;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String value) {
        this.user = value;
    }

    public Integer getChangeset() {
        return changeset;
    }

    public void setChangeset(Integer value) {
        this.changeset = value;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double value) {
        this.lat = value;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double value) {
        this.lon = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Tag {
        @XmlAttribute(name = "k")
        protected String k;
        @XmlAttribute(name = "v")
        protected String v;

        public String getK() {
            return k;
        }

        public void setK(String value) {
            this.k = value;
        }

        public String getV() {
            return v;
        }

        public void setV(String value) {
            this.v = value;
        }
    }
}
