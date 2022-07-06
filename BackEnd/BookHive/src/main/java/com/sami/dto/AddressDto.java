package com.cgi.dto;

public class AddressDto {
	private String address;

	public AddressDto() {
		super();
	}

	public AddressDto(String address) {
		super();
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
