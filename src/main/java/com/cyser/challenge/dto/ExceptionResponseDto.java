package com.cyser.challenge.dto;

public class ExceptionResponseDto {

	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ResponseDtoException [status=" + status + "]";
	}
	
	
}
