package org.sap.api.model;

import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.ParameterType;
import org.hibersap.annotations.Table;

import lombok.Data;
/*
 * @Auth prasad
 * @Date 09/12/2021
 */
@Data
@Bapi("ZATKAT_RFC_UPDATE_UBL_DATA")
public class InvoiceRfcUBLUpdate {

	@Import
	//@Table
	@Parameter(value="IT_UBL_DATA",type=ParameterType.TABLE_STRUCTURE)
	private List<InvoiceUblDetails> invoiceUblDetails;
	
}
