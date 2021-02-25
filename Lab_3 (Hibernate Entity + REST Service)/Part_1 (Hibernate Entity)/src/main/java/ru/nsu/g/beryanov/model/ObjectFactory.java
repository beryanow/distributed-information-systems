package ru.nsu.g.beryanov.model;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
    public ObjectFactory() {}

    public Node createNode() {
        return new Node();
    }

    public Node.Tag createNodeTag() {
        return new Node.Tag();
    }
}
