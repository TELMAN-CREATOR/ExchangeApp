package com.Telman1.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Telman1.dto.DtoExcahange;
import com.Telman1.service.IExchangeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class ExchangeServiceImpl implements IExchangeService{
	final private String apiUrl="https://v6.exchangerate-api.com/v6/e046fe4c95d65f096aa3c0d4/latest/";
	

	@Override
	public DtoExcahange getCurrency(String fromCurrency, String toCurrency) {
		DtoExcahange dtoExcahange=new DtoExcahange();
		
		
		
		try {
			
		
		
		RestTemplate restTemplate=new RestTemplate();
		String response=restTemplate.getForObject(apiUrl+fromCurrency.toUpperCase(), String.class);
		
		ObjectMapper objectMapper=new ObjectMapper();
		JsonNode node =objectMapper.readTree(response);
		
		JsonNode conversion =node.path("conversion_rates");
		
		if (conversion.get(toCurrency.toUpperCase())==null) {
			throw new Exception();
		}
			
			dtoExcahange.setCurremcy(conversion.get(toCurrency.toUpperCase()).asDouble());
		  
	
		 } catch (Exception e) {
			e.getMessage();
		}
		return dtoExcahange;
		
		
	}
	

}
