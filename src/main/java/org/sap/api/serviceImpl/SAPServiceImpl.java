package org.sap.api.serviceImpl;

import java.util.Date;

import org.hibersap.session.Session;
import org.hibersap.session.SessionManager;
import org.hibersap.session.Transaction;
import org.sap.api.model.InvoiceRFCBapi;
import org.sap.api.model.InvoiceRfcUBLUpdate;
import org.sap.api.model.InvoiceUblDetails;
import org.sap.api.service.SAPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/*
 * @Auth prasad
 * @Date 09/12/2021
 */
@Service
public class SAPServiceImpl implements SAPService {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(SAPServiceImpl.class);
	
	@Autowired
	private SessionManager sessionManager;
	
    /*
     * (non-Javadoc)
     * @see org.sap.api.service.SAPService#fetchInvoiceDetails(java.util.Date, java.util.Date)
     * @param minDate
     * @param maxDate
     */
	@Override
	public InvoiceRFCBapi fetchInvoiceDetails(Date minDate,Date maxDate){
		LOGGER.info("****Stared fetchInvoiceDetails*****");
	    Session session=sessionManager.openSession();
	    LOGGER.info("Session:{}",session.isClosed());	
	    InvoiceRFCBapi invoiceRFCBapi=null;
	    try{
	    	session.beginTransaction();
	    	invoiceRFCBapi=new InvoiceRFCBapi();
	    	invoiceRFCBapi.setIvFDate(minDate);
	    	invoiceRFCBapi.setIvTDate(maxDate);
	        session.execute(invoiceRFCBapi);
	        LOGGER.info("Values :{}",invoiceRFCBapi.getUbl());
	    }catch(Exception ex) {
	    	LOGGER.error("Unable to fetchInvoiceDetails:{}",ex);
	    }finally{
	    	if(session!=null) {session.close();}
	    }
	    LOGGER.info("****Completed fetchInvoiceDetails*****");
	    return invoiceRFCBapi;
	}

    /*
     * (non-Javadoc)
     * @see org.sap.api.service.SAPService#saveOrupdateInvoiceUBLXml(org.sap.api.model.InvoiceUBLUpdate)
     * @param InvoiceUBLUpdate
     */
	@Override
	public boolean saveOrupdateInvoiceUBLXml(InvoiceRfcUBLUpdate invoiceUBLUpdate) {
		LOGGER.info("****Stared saveOrupdateInvoiceUBLXml*****");
		Session session=sessionManager.openSession();
	    LOGGER.info("Session:{}",session.isClosed());	
	    Transaction trans=null;
	    boolean flag=false;
	    InvoiceUblDetails ublDetails=null;
	    try{
	    	if(invoiceUBLUpdate!=null && invoiceUBLUpdate.getInvoiceUblDetails()!=null && !invoiceUBLUpdate.getInvoiceUblDetails().isEmpty()) {
	  	       	ublDetails=invoiceUBLUpdate.getInvoiceUblDetails().get(0);
	  	    }
	    	trans=session.beginTransaction();
	        session.execute(invoiceUBLUpdate);
	        trans.commit();
	        flag=true;
	    }catch(Exception ex) {
	    	trans.rollback();
	    	LOGGER.error("Unable to Save InvoiceUBLXml file with invoice:{}",ublDetails.getInvoiceId(),ex);
	    }finally{
	    	if(session!=null){session.close();}
	    }
	    LOGGER.info("****Completed saveOrupdateInvoiceUBLXml*****");
	    return flag;
	}

}
