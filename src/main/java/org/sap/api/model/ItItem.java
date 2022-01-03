package org.sap.api.model;

import java.math.BigDecimal;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

import lombok.Data;

@Data
@BapiStructure
public class ItItem {
   @Parameter("INVOICE_IDENTIFIER")
   private String invoiceId;
   
   @Parameter("INVOICE_ITEM")
   private String invoiceItem;
   
   @Parameter("QUNATITY")
   private BigDecimal invoiceQuantity;
   
   @Parameter("QUNATITY_UOM")
   private String unitCode;
   
   @Parameter("CURRENCY_CODE")
   private String currencyCode;
   
   @Parameter("INV_LINE_NO_VAT")
   private BigDecimal lineExtensionAmt;
   
   @Parameter("VAT_AMOUNT")
   private BigDecimal vatAmt;
   
   @Parameter("ITEM_NET_PRICE")
   private BigDecimal roundingAmout;
   
   @Parameter("ITEM_ID")
   private String itemId;
   
   @Parameter("ITEM_DESCRIPTION")
   private String itemDescription;
   
   @Parameter("BASE_QUANTITY")
   private BigDecimal baseQty;
   
   @Parameter("BASE_QUANTITY_UOM")
   private String baseUnitCode;
   
   @Parameter("ITEM_GROSS_PRICE")
   private BigDecimal priceAmt;
   
   @Parameter("TAX_SCHEME")
   private String taxScheme;
   
   @Parameter("VAT_CATEGORY_RATE")
   private BigDecimal vatPercent;
}
