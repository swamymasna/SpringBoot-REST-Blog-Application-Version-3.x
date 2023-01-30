package com.swamy.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Test {

	public static void main(String[] args) {

//		try {
//			Stream<String> stream = Files.lines(Paths.get("POST-EMAIL-BODY-TEMPLATE.txt"));
//			stream.forEach(System.out::println);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		try {
			List<String> allLines = Files.readAllLines(Paths.get("POST-EMAIL-BODY-TEMPLATE.txt"));

			allLines.forEach(line -> {
				System.out.println(line);
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
