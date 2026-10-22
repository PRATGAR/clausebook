package com.acme.contracts.support;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class XmlText {

    private XmlText() {
    }

    public static String child(Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        if (nodes.getLength() == 0) {
            throw new IllegalArgumentException("missing <" + tag + ">");
        }
        return nodes.item(0).getTextContent();
    }
}
