package org.sap.api.scheduler;

import static org.sap.api.util.SAPUtilConstant.startDate;
import static org.sap.api.util.SAPUtilConstant.isEmpty;
import static org.sap.api.util.SAPUtilConstant.convertDateToLocalDate;
import static org.sap.api.util.SAPUtilConstant.convertDateToLocalTime;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.sap.api.dto.InvoiceRequest;
import org.sap.api.model.InvoiceRFCBapi;
import org.sap.api.model.InvoiceRfcUBLUpdate;
import org.sap.api.model.InvoiceUblDetails;
import org.sap.api.model.ItItem;
import org.sap.api.model.ItemDetails;
import org.sap.api.service.SAPService;
import org.sap.api.util.DefaultNamespacePrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.AddressType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.AttachmentType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.CountryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.CustomerPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.DocumentReferenceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.InvoiceLineType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.ItemType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyIdentificationType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyTaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.PriceType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.SupplierPartyType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxSchemeType;
import oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_21.TaxTotalType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.BaseQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.EmbeddedDocumentBinaryObjectType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.InvoiceTypeCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.InvoicedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.IssuerIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.LineExtensionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.PriceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.RoundingAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_21.TaxAmountType;
import oasis.names.specification.ubl.schema.xsd.invoice_21.InvoiceType;
/*
 * @Auth prasad
 * @Date 09/12/2021
 */
@Component
public class SAPScheduler {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(SAPScheduler.class);
	
	@Autowired
	private SAPService sapService;
	
	@Value("${job_day:1}")
	private int jobDay;
	
	@Value("${job_type:D}")
	private String jobType;

	 @Scheduled(fixedDelay=5000)
	 public void InvoiceSchedulers(){
		 LOGGER.info("***Start Invoice Schedulers*****");
		 LOGGER.info("Job Day:{} , Job Type:{}",jobDay,jobType);
		 Date minDate=startDate(jobDay, jobType);
		 Date maxDate=new Date();
		 LOGGER.info("MinDate:{} ,MaxDate:{}",minDate,maxDate);
		 InvoiceRFCBapi invoiceRFCBapi=sapService.fetchInvoiceDetails(minDate,maxDate);
		 if(invoiceRFCBapi!=null) {
			 LOGGER.info("***Generating UBL Files*****");
			 final Map<String,InvoiceType> invoiceTypes=new HashMap<>();
			 //ItemDetails
			 List<ItemDetails> itemDetails=invoiceRFCBapi.getItemDetails();
			 LOGGER.info("<<<<<<Started ITEM Details>>>>>>");
	    	 if(itemDetails!=null && !itemDetails.isEmpty()) {
	    		 itemDetails.forEach(e->{
	    			 String invoiceId=e.getInvoiceIdentified();
	    			 LOGGER.info("Stared contstructing InvoiceId:{}",invoiceId);
	    			 String profileId="reporting:1.0";
	    			 LOGGER.info("Profile Id:{}",profileId);
	    			 LOGGER.info("Invoice Id:{}",invoiceId);
	    			 //invoice header details
	    			 InvoiceType aInvoice=new InvoiceType();
		 		     aInvoice.setProfileID(profileId);
		 		     aInvoice.setID(invoiceId);
		 		     aInvoice.setUUID(e.getInvoiceUID());
		 		     LOGGER.info("Issue Date:{}",e.getInvoiceDate());
		 		     aInvoice.setIssueDate(convertDateToLocalDate(e.getInvoiceDate()));
		 		     LOGGER.info("Issue Time:{}",e.getInvoiceTime());
		 		     aInvoice.setIssueTime(convertDateToLocalTime(e.getInvoiceTime()));
		 		     InvoiceTypeCodeType inTypeCode=new InvoiceTypeCodeType();
		 		     inTypeCode.setName(e.getPaymentCode());
		 		     inTypeCode.setValue(e.getInvoiceType());
		 		     aInvoice.setInvoiceTypeCode(inTypeCode);
		 		     NoteType nType=new NoteType();
		 		     nType.setValue(e.getNotes());
		 		     aInvoice.setNote(Arrays.asList(nType));
		 		     //Documenet Currency Code
		 		     aInvoice.setDocumentCurrencyCode(e.getCurrencyCode());
		 		     //TaxCurrencyCode
		 		     aInvoice.setTaxCurrencyCode(e.getCurrencyCode());
		 		     //Additional DocumentReference
		 		     DocumentReferenceType dRefType=new DocumentReferenceType();
		 		     dRefType.setID("QR");
		 		     AttachmentType aType=new AttachmentType();
		 		     EmbeddedDocumentBinaryObjectType edbObj=new EmbeddedDocumentBinaryObjectType();
		 		     edbObj.setMimeCode("text/plain");
		 		     edbObj.setValue(e.getQrCode().getBytes());
		 		     aType.setEmbeddedDocumentBinaryObject(edbObj);
		 		     dRefType.setAttachment(aType);
		 		     aInvoice.setAdditionalDocumentReference(Arrays.asList(dRefType));
		 		     //SupplierDetails
	    			 SupplierPartyType sPartyType=new SupplierPartyType();
	    			 //Party Type
	    			 PartyType spType=new PartyType();
	    			 PartyIdentificationType spIdentTypeId=new PartyIdentificationType();
	    			 spIdentTypeId.setID(e.getSellerId());
	    			 PartyIdentificationType spIdentTypeTax=new PartyIdentificationType();
	    			 spIdentTypeTax.setID(e.getSellerVatNo());
	    			 spType.setPartyIdentification(Arrays.asList(spIdentTypeId,spIdentTypeTax));
	    			 //AddressType
	    			 LOGGER.info("<<<<<<Seller Details>>>>>>");
	    			 LOGGER.info("Seller Street:{}",e.getSellerStreet());
	    			 LOGGER.info("Seller Additional Street Name:{}",e.getSellerAddStreet());
	    			 LOGGER.info("Seller BuildingName:{}",e.getSellerBuilding());
	    			 LOGGER.info("Seller PlotIdentification:{}",e.getSellerAddBuilding());
	    			 LOGGER.info("Seller Post:{}",e.getSellerPost());
	    			 LOGGER.info("Seller City:{}",e.getSellerCity());
	    			 LOGGER.info("Seller Country:{}",e.getSellerCountry());
	    			 AddressType saType=new AddressType();
	    			 saType.setStreetName(e.getSellerStreet());
	    			 saType.setAdditionalStreetName(e.getSellerAddStreet());
	    			 saType.setBuildingName(e.getSellerBuilding());
	    			 saType.setPlotIdentification(e.getSellerAddBuilding());
	    			 saType.setCityName(e.getSellerCity());
	    			 saType.setPostalZone(e.getSellerPost());
	    			 saType.setCountrySubentity(e.getSellerCountry());
	    			 saType.setCitySubdivisionName(e.getSellerCity());
	    			 CountryType scType=new CountryType();
	    			 scType.setIdentificationCode(e.getSellerCountry());
	    			 saType.setCountry(scType);
	    			 spType.setPostalAddress(saType);//add Address Details
	    			 PartyTaxSchemeType spTaxSchemaType=new PartyTaxSchemeType();
	    			 spTaxSchemaType.setCompanyID(e.getSellerId());
	    			 TaxSchemeType stSchemeType=new TaxSchemeType();
	    			 stSchemeType.setID(e.getTaxScheme());
	    			 spType.setPartyTaxScheme(Arrays.asList(spTaxSchemaType));//add Taxschema
	    			 //Add partype to SupplierPartyType
	    			 sPartyType.setParty(spType);
	    			 
	    			 //Customer Details
	    			 CustomerPartyType cPartType=new CustomerPartyType();
	    			 //Party Type
	    			 PartyType cpType=new PartyType();
	    			 PartyIdentificationType cpIdentTypeId=new PartyIdentificationType();
	    			 cpIdentTypeId.setID(e.getBuyerId());
	    			 PartyIdentificationType cpIdentTypeTax=new PartyIdentificationType();
	    			 cpIdentTypeTax.setID(e.getBuyerVatNo());
	    			 cpType.setPartyIdentification(Arrays.asList(cpIdentTypeId,cpIdentTypeTax));
	    			 LOGGER.info("<<<<<<Buyer Details>>>>>>");
	    			 LOGGER.info("Buyer Street:{}",e.getBuyerStreet());
	    			 LOGGER.info("Buyer Additional Street Name:{}",e.getBuyerAddStreet());
	    			 LOGGER.info("Buyer BuildingName:{}",e.getBuyerBuilding());
	    			 LOGGER.info("Buyer PlotIdentification:{}",e.getBuyerAddBulding());
	    			 LOGGER.info("Buyer Post:{}",e.getBuyerPost());
	    			 LOGGER.info("Buyer City:{}",e.getBuyerCity());
	    			 LOGGER.info("Buyer Country:{}",e.getBuyerCountry());
	    			 //AddressType
	    			 AddressType caType=new AddressType();
	    			 caType.setStreetName(e.getBuyerStreet());
	    			 caType.setAdditionalStreetName(e.getBuyerAddStreet());
	    			 caType.setBuildingName(e.getBuyerBuilding());
	    			 caType.setPlotIdentification(e.getBuyerAddBulding());
	    			 caType.setCityName(e.getBuyerCity());
	    			 caType.setPostalZone(e.getBuyerPost());
	    			 caType.setCountrySubentity(e.getBuyerCountry());
	    			 caType.setCitySubdivisionName(e.getBuyerCity());
	    			 CountryType ccType=new CountryType();//Required clartification
	    			 ccType.setIdentificationCode(e.getBuyerCountry());
	    			 caType.setCountry(ccType);
	    			 cpType.setPostalAddress(caType);//add Address Details
	    			 PartyTaxSchemeType cpTaxSchemaType=new PartyTaxSchemeType();
	    			 cpTaxSchemaType.setCompanyID(e.getBuyerId());
	    			 TaxSchemeType ctSchemeType=new TaxSchemeType();
	    			 ctSchemeType.setID(e.getTaxScheme());
	    			 cpType.setPartyTaxScheme(Arrays.asList(cpTaxSchemaType));//add Taxschema
	    			 //Add partype to SupplierPartyType
	    			 cPartType.setParty(cpType);
	    			 LOGGER.info("Accounting SupplierParty Object is Empty:{}",isEmpty(sPartyType));
	    			 aInvoice.setAccountingSupplierParty(sPartyType);
	    			 LOGGER.info("Accounting CustomerParty Object is Empty:{}",isEmpty(cPartType));
	    			 aInvoice.setAccountingCustomerParty(cPartType);
	    			 LOGGER.info("Currency Code:{}",e.getCurrencyCode());
	    			 aInvoice.setDocumentCurrencyCode(e.getCurrencyCode());
    				 aInvoice.setTaxCurrencyCode(e.getCurrencyCode());
	    			 invoiceTypes.put(invoiceId,aInvoice);
	    			 LOGGER.info("Completed contstructing InvoiceId:{}",invoiceId);
	    		 });
	    	 }
	    	 LOGGER.info("<<<<<<Completed ITEM Details>>>>>>");
			 //IT Item
	    	 LOGGER.info("<<<<<<Started IT ITem>>>>>>");
		     List<ItItem> itItems=invoiceRFCBapi.getItems();
		     if(itItems!=null && !itItems.isEmpty()){
		    	 itItems.forEach(e->{
		    		 String invoiceId=e.getInvoiceId();
		    		 LOGGER.info("Started IT ITem Construction Invoice No:{}",invoiceId);
		    		 LOGGER.info("CurrencyCode:{}",e.getCurrencyCode());
		    	     //InvoiceLine
		 		     InvoiceLineType lineType=new InvoiceLineType();
		 		     lineType.setID(e.getInvoiceId().trim());
		 		     InvoicedQuantityType invoiceQtyType=new InvoicedQuantityType();
		 		     invoiceQtyType.setUnitCode(e.getUnitCode());
		 		     lineType.setInvoicedQuantity(invoiceQtyType);
		 		     lineType.setInvoicedQuantity(e.getInvoiceQuantity());
		 		     //LineExtensionAmount
		 		     LineExtensionAmountType lineExtensionAmountType=new LineExtensionAmountType();
		 		     lineExtensionAmountType.setValue(e.getLineExtensionAmt());
		 		     lineExtensionAmountType.setCurrencyID(e.getCurrencyCode());
		 		     lineType.setLineExtensionAmount(lineExtensionAmountType);
		 		     //TaxTotal
		 		     TaxTotalType taxTotalType=new TaxTotalType();
		 		     TaxAmountType taxAmountType=new TaxAmountType();
		 		     taxAmountType.setCurrencyID(e.getCurrencyCode());
		 		     taxAmountType.setValue(e.getVatAmt());
		 		     taxTotalType.setTaxAmount(taxAmountType);
		 		     RoundingAmountType roundingAmountType=new RoundingAmountType();
		 		     roundingAmountType.setCurrencyID(e.getCurrencyCode());
		 		     roundingAmountType.setValue(e.getRoundingAmout());
		 		     taxTotalType.setRoundingAmount(roundingAmountType);
		 		     //setting taxtotal
		 		     lineType.setTaxTotal(Arrays.asList(taxTotalType));
		 		     ItemType itemType=new ItemType();
		 		     itemType.setName(e.getItemDescription());
		 		     TaxCategoryType taxCategoryType=new TaxCategoryType();
		 		     taxCategoryType.setID(e.getItemId());
		 		     taxCategoryType.setPercent(e.getVatPercent());
		 		     TaxSchemeType taxSchemeType=new TaxSchemeType();
		 		     taxSchemeType.setID(e.getTaxScheme());
		 		     taxCategoryType.setTaxScheme(taxSchemeType);
		 		     //setting tax catetorytype to itemtype
		 		     itemType.setClassifiedTaxCategory(Arrays.asList(taxCategoryType));
		 		     lineType.setItem(itemType);
		 		     PriceType priceType=new PriceType();
		 		     PriceAmountType priceAmountType=new PriceAmountType();
		 		     priceAmountType.setCurrencyID(e.getCurrencyCode());
		 		     priceAmountType.setValue(e.getPriceAmt());
		 		     BaseQuantityType baseQuantityType=new BaseQuantityType();
		 		     baseQuantityType.setUnitCode(e.getBaseUnitCode());
		 		     baseQuantityType.setValue(e.getBaseQty());
		 		     priceType.setPriceAmount(priceAmountType);
		 		     priceType.setBaseQuantity(baseQuantityType);
		 		     //pricetype setting to linetype
		 		     lineType.setPrice(priceType);
		 		     if(invoiceTypes.containsKey(invoiceId)) {
	    				 LOGGER.info("<<<<<<<InvoiceId Exist on invoiceTypes Map>>>>>>>>");
	    				 LOGGER.info("****Started Proceeding ItemDetails and inserting into InvoiceType****");
	    				 InvoiceType aInvoice=invoiceTypes.get(invoiceId);
	    				 aInvoice.setInvoiceLine(Arrays.asList(lineType));
	    				 invoiceTypes.put(invoiceId,aInvoice);
	    				 LOGGER.info("****Completed Proceeding IT ITem and inserting into InvoiceType****");
	    			 }
		 		     LOGGER.info("Completed IT ITem Construction Invoice No:{}",invoiceId);
		    	 });
		    	 
		     }
		     LOGGER.info("<<<<<<Completed IT ITem>>>>>>");
		     //generated xml files
		     LOGGER.info("<<<<<<Started Generated Xml>>>>>>");
	    	 invoiceTypes.values().forEach(e->{
	    		 InvoiceRequest request=new InvoiceRequest();
	    		 request.setInvoice(e);
	    		 try {
	    			    JAXBContext context=JAXBContext.newInstance(InvoiceRequest.class);//new com.sun.xml.bind.v2.JAXBContextFactory().createContext(new Class[] {InvoiceRequest.class},null);
	    			    Marshaller marshaller=context.createMarshaller();
	    			    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
	    			    marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper",new DefaultNamespacePrefixMapper());
	    			    //marshaller.setProperty("com.sun.xml.internal.bind.namespacePrefixMapper",new DefaultNamespacePrefixMapper());
	    			    //marshaller.marshal(request,new File(e.getProfileIDValue().concat(minDate.toGMTString())+".xml"));
	    			    StringWriter outUblXml=new StringWriter();
	    			    marshaller.marshal(request,outUblXml);
	    			    String invoiceId=e.getIDValue();
	    			    LOGGER.info("InvoiceId:{}",invoiceId);
	    			    LOGGER.info("Profile Id:{}",e.getProfileIDValue());
	    			    LOGGER.info("UBL Payload:{}",outUblXml);
	    			    InvoiceRfcUBLUpdate invoiceUBLUpdate=new InvoiceRfcUBLUpdate();
	    			    InvoiceUblDetails invoiceUblDetails=new InvoiceUblDetails();
	    			    invoiceUblDetails.setInvoiceId(invoiceId);
	    			    invoiceUblDetails.setUbl(outUblXml.toString());
	    			    invoiceUBLUpdate.setInvoiceUblDetails(Arrays.asList(invoiceUblDetails));
	    			    sapService.saveOrupdateInvoiceUBLXml(invoiceUBLUpdate);
	    		 }catch(Exception ex) {
	    			 LOGGER.error("Unable to generate UBL Files:{}",ex);
	    		 }
	    	 });
	    	 LOGGER.info("<<<<<<Completed Generated Xml>>>>>>");
		     LOGGER.info("***End Completed Generating UBL Files*****");
		 }
		 LOGGER.info("***End Invoice Schedulers*****");
	 }
}
