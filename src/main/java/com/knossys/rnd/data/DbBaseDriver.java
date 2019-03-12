package com.knossys.rnd.data;

/**
 * @author vvelsen
 */
public class DbBaseDriver {
	
	private String driverType="ABSTRACT";
	
	/**
	 * @return
	 */
	public String getDriverType() {
		return driverType;
	}

	/**
	 * @param driverType
	 */
	public void setDriverType(String driverType) {
		this.driverType = driverType;
	}
}
