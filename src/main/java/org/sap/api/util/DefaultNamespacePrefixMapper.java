package org.sap.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
/*
 * @Auth prasad
 * @Date 09/12/2021
 */
public class DefaultNamespacePrefixMapper extends NamespacePrefixMapper{
	
	private Logger LOGGER=LoggerFactory.getLogger(DefaultNamespacePrefixMapper.class);

	//private Map<String, String> namespaceMap = new HashMap<>();
	
	private static final String N0_URI="http://www.sap.com/eDocument/SA/Invoice/v1.0";
	private static final String N0_PREFIX="n0";
	
	private static final String N1_URI="http://www.sap.com/eDocument/SA/InvoiceTypeDef/v1.0";
	private static final String N1_PREFIX="n1";
	
	private static final String N2_URI="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2";
	private static final String N2_PREFIX="n2";
	
	private static final String N3_URI="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2";
	private static final String N3_PREFIX="n3";

	/**
	 * Create mappings.
	 *//*
	public DefaultNamespacePrefixMapper() {
		namespaceMap.put("http://www.sap.com/eDocument/SA/Invoice/v1.0", "n0");
		namespaceMap.put("http://www.sap.com/eDocument/SA/InvoiceTypeDef/v1.0", "n1");
		//namespaceMap.put("http://www.w3.org/2003/05/soap-envelope/", "soap");
		namespaceMap.put("urn:sap.com:proxy:S4B:/1SAI/TASF5B3B596835F4069014C:755","prx");
		namespaceMap.put("urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2","n2");
		namespaceMap.put("urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2","n3");
	}
	*/
	

	/* (non-Javadoc)
	 * Returning null when not found based on spec.
	 * @see com.sun.xml.bind.marshaller.NamespacePrefixMapper#getPreferredPrefix(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
		//return namespaceMap.getOrDefault(namespaceUri, suggestion);
		if(N0_URI.equalsIgnoreCase(namespaceUri)) {
			return N0_PREFIX;
		}else if(N1_URI.equalsIgnoreCase(namespaceUri)) {
			return N1_PREFIX;
		}else if(N2_URI.equalsIgnoreCase(namespaceUri)) {
			return N2_PREFIX;
		}else if(N3_URI.equalsIgnoreCase(namespaceUri)) {
			return N3_PREFIX;
		}else {
			return suggestion;
		}
	}
	
	 @Override
	    public String[] getPreDeclaredNamespaceUris() {
	        return new String[] {N0_URI,N1_URI,N2_URI,N3_URI};
	    }
}
