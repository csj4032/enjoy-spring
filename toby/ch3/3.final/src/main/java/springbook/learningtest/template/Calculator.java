package springbook.learning.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {

	public Integer calcSum(String filePath) throws IOException {
		return lineReadTemplate(filePath, (line, value) -> value + Integer.valueOf(line), 0);
	}

	public Integer calcMultiply(String filePath) throws IOException {
		return lineReadTemplate(filePath, (String line, Integer res) -> res *= Integer.valueOf(line), 1);
	}

	public String concatenate(String filePath) throws IOException {
		return lineReadTemplate(filePath, (String line, String res) -> res + line, "");
	}

	private <T> T lineReadTemplate(String filePath, LineCallback<T> callback, T initVal) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			T res = initVal;
			String line;
			while ((line = br.readLine()) != null) {
				res = callback.doSomethingWithLine(line, res);
			}
			br.close();
			return res;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw e;
		}
	}
}