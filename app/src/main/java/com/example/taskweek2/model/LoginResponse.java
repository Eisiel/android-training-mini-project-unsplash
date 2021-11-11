package com.example.taskweek2.model;

public class LoginResponse {
	private LoginData data;
	private String message;
	private boolean status;
	private String token;

	public LoginData getData(){
		return data;
	}

	public String getMessage(){
		return message;
	}

	public boolean isStatus(){
		return status;
	}

	public String getToken(){
		return token;
	}
}
