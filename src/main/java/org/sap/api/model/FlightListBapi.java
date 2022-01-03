package org.sap.api.model;

import org.hibersap.annotations.Bapi;
import org.hibersap.annotations.Convert;
import org.hibersap.annotations.Import;
import org.hibersap.annotations.Parameter;
import org.hibersap.conversion.BooleanConverter;

import lombok.Data;

@Data
@Bapi("BAPI_SFLIGHT_GETLIST")
public class FlightListBapi {
	@Import
	@Parameter("FROMCOUNTRYKEY")
	private String fromCountryKey;

	@Import
	@Parameter("FROMCITY")
	private String fromCity;

	@Import
	@Parameter("TOCOUNTRYKEY")
	private String toCountryKey;

	@Import
	@Parameter("TOCITY")
	private String toCity;

	@Import
	@Parameter("AIRLINECARRIER")
	private String airlineCarrier;

	@Import
	@Parameter("AFTERNOON")
	@Convert(converter = BooleanConverter.class)
	private boolean afternoon;

	@Import
	@Parameter("MAXREAD")
	private int maxRead;

	public FlightListBapi(String fromCountryKey, String fromCity, String toCountryKey, String toCity,
			String airlineCarrier, boolean afternoon, int maxRead) {

		this.fromCountryKey = fromCountryKey;
		this.fromCity = fromCity;
		this.toCountryKey = toCountryKey;
		this.toCity = toCity;
		this.airlineCarrier = airlineCarrier;
		this.afternoon = afternoon;
		this.maxRead = maxRead;
	}
}
