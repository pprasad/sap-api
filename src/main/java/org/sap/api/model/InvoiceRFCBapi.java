package org.sap.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Export;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.annotations.Table;

import lombok.Data;
/*
 * @Auth prasad
 * @Date 09/12/2021
 */
@Data
@Bapi("ZATKAT_RFC_GET_UBL_DATA")
public class InvoiceRFCBapi {
   @Import
   @Parameter("IV_FDATE")
   private Date ivFDate;
   
   @Import
   @Parameter(value="IV_TDATE")
   private Date ivTDate;
   
   @Export
   @Parameter(value="IT_UBL")
   private List<String> ubl;
   
   @Table
   @Parameter(value="IT_DETAILS")
   private List<ItemDetails> itemDetails=new ArrayList<>();
   
   @Table
   @Parameter(value="IT_ITEM")
   private List<ItItem> items=new ArrayList<>();
}
