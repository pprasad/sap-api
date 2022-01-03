package org.sap.api.model;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Export;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;

import lombok.Data;

@Data
@Bapi("BAPI_CUSTOMER_GETDETAIL2")
public class CustomerDetailsBapi {
  
	@Import
	@Parameter("CUSTOMERNO")
	private String customerNo;
	
	@Export
	@Parameter("CUSTOMERADDRESS")
	private String customerAddress;
	
}
