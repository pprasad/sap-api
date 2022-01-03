package org.sap.api.service;

import java.util.Date;

import org.sap.api.model.InvoiceRFCBapi;
import org.sap.api.model.InvoiceRfcUBLUpdate;
/*
 * @Auth prasad
 * @Date 09/12/2021
 */
public interface SAPService {
   
	InvoiceRFCBapi fetchInvoiceDetails(Date minDate,Date maxDate);
	
	boolean saveOrupdateInvoiceUBLXml(InvoiceRfcUBLUpdate invoiceUBLUpdate);
}
