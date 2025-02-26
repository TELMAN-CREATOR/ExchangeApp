package com.Telman1.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.Telman1.dto.DtoExcahange;
import com.Telman1.service.IExchangeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class ExchangeServiceImpl implements IExchangeService{
	final private String apiUrl="https://v6.exchangerate-api.com/v6/e046fe4c95d65f096aa3c0d4/latest/USD";
	

	@Override
	public DtoExcahange getCurrency() {
		DtoExcahange dtoExcahange=new DtoExcahange();
		
		
		
		try {
			
		
		
		RestTemplate restTemplate=new RestTemplate();
		String response=restTemplate.getForObject(apiUrl, String.class);
		
		ObjectMapper objectMapper=new ObjectMapper();
		JsonNode node =objectMapper.readTree(response);
		
		JsonNode conversion =node.path("conversion_rates");
			
			dtoExcahange.setAZN(conversion.get("AZN").asDouble());
			dtoExcahange.setUSD(conversion.get("USD").asDouble());
			dtoExcahange.setEUR(conversion.get("EUR").asDouble());
			dtoExcahange.setRUB(conversion.get("RUB").asDouble());
			dtoExcahange.setTRY(conversion.get("TRY").asDouble());
			dtoExcahange.setGBP(conversion.get("GBP").asDouble());
			
		   
	
		 } catch (Exception e) {
			e.getMessage();
		}
		return dtoExcahange;
		
		
	}
	

}
