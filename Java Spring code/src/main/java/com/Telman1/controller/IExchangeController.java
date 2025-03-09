package com.Telman1.controller;

import com.Telman1.dto.DtoExcahange;

public interface IExchangeController {
	
	public DtoExcahange getCurrency(String fromCurrency, String toCurrency) ;

}
