package org.example.testwork.core.gateway.dto;

import java.util.List;
import lombok.Data;

@Data
public class SwapiPeople{
	private String next;
	private Object previous;
	private int count;
	private List<ResultsItem> results;
}