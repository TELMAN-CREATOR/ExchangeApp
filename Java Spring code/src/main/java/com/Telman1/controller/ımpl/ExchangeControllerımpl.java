package com.Telman1.controller.ımpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Telman1.controller.IExchangeController;
import com.Telman1.dto.DtoExcahange;
import com.Telman1.service.impl.ExchangeServiceImpl;
@RestController
@RequestMapping("rest/api/exchange")
public class ExchangeControllerımpl implements IExchangeController {
	
	@Autowired
	private ExchangeServiceImpl exchangeServiceImpl;

	@Override
	@GetMapping("/{fromCurrency}/{toCurrency}")
	public DtoExcahange getCurrency(@PathVariable(value = "fromCurrency") String fromCurrency, @PathVariable(value = "toCurrency") String toCurrency) {
		
		return exchangeServiceImpl.getCurrency(fromCurrency, toCurrency);
	}

}
