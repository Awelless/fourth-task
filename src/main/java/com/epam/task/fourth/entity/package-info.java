@XmlSchema(
        namespace = "http://www.example.com/tariffs",
        xmlns = @XmlNs(prefix = "", namespaceURI = "http://www.example.com/tariffs"),
        elementFormDefault = XmlNsForm.QUALIFIED
)
package com.epam.task.fourth.entity;

import javax.xml.bind.annotation.XmlNs;
import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlSchema;