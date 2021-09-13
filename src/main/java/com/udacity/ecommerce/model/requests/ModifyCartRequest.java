package com.udacity.ecommerce.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class ModifyCartRequest {
	
	@JsonProperty
	private String username;
	
	@JsonProperty
	private long itemId;
	
	@JsonProperty
	private int quantity;

}
