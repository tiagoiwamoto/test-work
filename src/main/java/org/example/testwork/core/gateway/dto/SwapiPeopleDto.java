package org.example.testwork.core.gateway.dto;

import java.util.List;
import lombok.Data;

@Data
public class SwapiPeopleDto{
	private List<String> films;
	private String homeworld;
	private String gender;
	private String skinColor;
	private String edited;
	private String created;
	private String mass;
	private List<String> vehicles;
	private String url;
	private String hairColor;
	private String birthYear;
	private String eyeColor;
	private List<Object> species;
	private List<String> starships;
	private String name;
	private String height;
}