package com.joalbano.crud.exception;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionResponse implements Serializable{
	
	private static final long serialVersionUID = 8173630712775749558L;
	
	private Date timestamp;
	private String message;
	private String details;
	
}
