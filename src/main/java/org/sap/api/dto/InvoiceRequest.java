package org.sap.api.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;

@Data
@XmlRootElement(name="InvoiceRequest",namespace="http://www.sap.com/eDocument/SA/Invoice/v1.0")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceRequest {
	@XmlElement(name="Invoice",namespace="http://www.sap.com/eDocument/SA/InvoiceTypeDef/v1.0")
	private InvoiceType invoice;
}
