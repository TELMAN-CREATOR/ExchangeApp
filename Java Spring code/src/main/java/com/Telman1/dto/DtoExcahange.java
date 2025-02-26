package com.Telman1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DtoExcahange {
	
	private Double USD;
	private Double AZN;
	private Double TRY;
	private Double EUR;
	private Double RUB;
	private Double GBP;
}
    