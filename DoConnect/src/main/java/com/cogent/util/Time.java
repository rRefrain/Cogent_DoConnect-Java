package com.cogent.util;

import java.time.LocalDateTime;  
import java.time.format.DateTimeFormatter;

public class Time {
	
	public static String getTimeNow() {
		LocalDateTime datetime1 = LocalDateTime.now();  
	    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");  
	    String formatDateTime = datetime1.format(format);   
	    return formatDateTime;
	}
}
