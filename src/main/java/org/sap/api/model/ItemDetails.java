package org.sap.api.model;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;

import org.hibersap.annotations.BapiStructure;
import org.hibersap.annotations.Parameter;

import lombok.Data;

@Data
@BapiStructure
public class ItemDetails {
   
	@Parameter("PROFILEID")
	private String profileId;
	
	@Parameter("INVOICE_IDENTIFIER")
	private String invoiceIdentified;
	
	@Parameter("INVOICE_UID")
	private String invoiceUID;
	
	@Parameter("INVOICE_DATE")
	private Date invoiceDate;
	
	@Parameter("INVOICE_TIME")
	private Date invoiceTime;
	
	@Parameter("INVOICE_TYPE")
	private String invoiceType;
	
	@Parameter("NOTES")
	private String notes;
	
	@Parameter("QRCODE")
	private String qrCode;
	
	@Parameter("SELLER_VAT_NUMBER")
	private String sellerVatNo;
	
	@Parameter("SELLER_ID")
	private String sellerId;
	
	@Parameter("SELLER_STREET")
	private String sellerStreet;
	
	@Parameter("SELLER_ADD_STREET")
	private String sellerAddStreet;
	
	@Parameter("SELLER_BUILDING")
	private String sellerBuilding;
	
	@Parameter("SELLER_ADD_BUILDING")
	private String sellerAddBuilding;
	
	@Parameter("SELLER_CITY")
	private String sellerCity;
	
	@Parameter("SELLER_STATE")
	private String sellerState;
	
	@Parameter("SELLER_COUNTRY")
	private String sellerCountry;
	
	@Parameter("SELLER_POST")
	private String sellerPost;
	
	@Parameter("BUYER_VAT_NUMBER")
	private String buyerVatNo;
	
	@Parameter("BUYER_ID")
	private String buyerId;
	
	@Parameter("BUYER_NAME")
	private String buyerName;
	
	@Parameter("BUYER_STREET")
	private String buyerStreet;
	
	@Parameter("BUYER_ADD_STREET")
	private String buyerAddStreet;
	
	@Parameter("BUYER_BUILDING")
	private String buyerBuilding;
	
	@Parameter("BUYER_ADD_BUILDING")
	private String buyerAddBulding;
	
	@Parameter("BUYER_CITY")
	private String buyerCity;
	
	@Parameter("BUYER_POST")
	private String buyerPost;
	
	@Parameter("BUYER_STATE")
	private String buyerState;
	
	@Parameter("BUYER_COUNTRY")
	private String buyerCountry;
	
	@Parameter("CURRENCY_CODE")
	private String currencyCode;
	
	@Parameter("PAYMENT_CODE")
	private String paymentCode;

	@Parameter("ALLW_TAX_SCHEME")
	private String taxScheme;
	
	@Parameter("INVOICE_AMOUNT")
	private BigDecimal invoiceAmt;
	
	@Parameter("INV_TOT_NO_VAT")
	private BigDecimal lineExtensionAmount;
	
	@Parameter("INV_TOT_VAT")
	private BigDecimal taxExclusiveAmount;
	
	@Parameter("INV_DUE_AMT")
	private BigDecimal invoiceDueAmt;
	
	@Parameter("VAT_CATEGORY_RATE")
	private BigDecimal vatCateRate;
	
 }
