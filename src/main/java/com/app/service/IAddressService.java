package com.app.service;

import java.util.List;

import com.app.pojos.Address;

public interface IAddressService {

	List<Address> getAllAddressesByUserId(Integer userId);

	Address addOrEditAddress(Address addr);

	String deleteAddressById(Integer addrId);

}
