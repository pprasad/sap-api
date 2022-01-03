package org.sap.api.model;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

import lombok.Data;

@Data
@BapiStructure
public class InvoiceUblDetails {
	@Parameter("INVOICE_IDENTIFIER")
	private String invoiceId;
	
	@Parameter("UBL")
	private String ubl;
}
