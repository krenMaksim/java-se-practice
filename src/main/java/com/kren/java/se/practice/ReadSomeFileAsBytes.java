package com.kren.java.se.practice;

import java.io.FileInputStream;
import java.io.IOException;

class ReadSomeFileAsBytes {

	public static void main(String[] args) throws IOException {
		String filePath = ReadSomeFileAsBytes.class.getClassLoader().getResource("some_file.txt").getFile();
		try (FileInputStream input = new FileInputStream(filePath)) {
			System.out.println("available bytes: " + 	input.available());

			int i = input.read();
			while (i != -1) {
				System.out.print((char) i);
				i = input.read();
			}
		}
	}

}
