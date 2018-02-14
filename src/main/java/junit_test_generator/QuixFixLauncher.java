package junit_test_generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

/**
 * 
 * @author Matias Martinez
 *
 */
public class QuixFixLauncher {

	public static String[] prepareCommand(String programToExecute, Object jsonInput) {

		List<String> arguments = new ArrayList<>();
		arguments.add(programToExecute);
		if (jsonInput instanceof Iterable) {
			Iterable it = (Iterable) jsonInput;
			for (Object object : it) {
				arguments.add(object.toString());
			}

		} else {
			arguments.add(jsonInput.toString());
		}

		System.out.println("Input-->" + jsonInput);
		String[] command = arguments.toArray(new String[] {});
		return command;
	}

	/**
	 * Each line is a test case
	 * 
	 * @param filejson
	 * @return
	 */
	public static List<String> read(String filejson) {
		List<String> s = new ArrayList<>();
		try {
			File file = new File(filejson);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				s.add(line);

			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static String format(Object out, boolean cutDecimal) {
		Object jsonOutObtained = transformToString(out, cutDecimal);

		String obtained = jsonOutObtained.toString();//removeSymbols(jsonOutObtained.toString());
		return obtained;
	}

	@Deprecated
	public static String[] executeSingleTest(String packageName, String programToExecute, String inputs)
			throws Exception {
		System.out.println("\nprogram " + programToExecute + " inputs" + inputs);
		JsonArray jelement = (JsonArray) new JsonParser().parse(inputs);
		Object jsonInput = jelement.get(0);
		Object jsonOutPutExpected = jelement.get(1);

		String[] command = QuixFixLauncher.prepareCommand(programToExecute, jsonInput);

		Object out = JavaDeserialization.execute(command, packageName);
		if (out == null) {
			System.err.println("output null");
			throw new Exception("Result NULL");
		}
		Object jsonOutObtained = transformToString(out, !(jsonOutPutExpected.toString().contains(".0")));

		String expected = removeSymbols(jsonOutPutExpected.toString());
		String obtained = removeSymbols(jsonOutObtained.toString());
		System.out.println("outPut expected-->" + expected);

		System.out.println("outPut obtained-->" + obtained);
		boolean correct = expected.equals(obtained);
		System.out.println("correct?" + correct);

		return new String[] { expected, obtained };
	}

	@Deprecated
	public static String[] execute(String packageName, String programToExecute, String inputs) throws Exception {
		System.out.println("\nprogram " + programToExecute + " inputs" + inputs);
		JsonArray jelement = (JsonArray) new JsonParser().parse(inputs);
		Object jsonInput = jelement.get(0);
		Object jsonOutPutExpected = jelement.get(1);

		String[] command = QuixFixLauncher.prepareCommand(programToExecute, jsonInput);

		Object out = JavaDeserialization.execute(command, packageName);
		if (out == null) {
			System.err.println("output null");
			throw new Exception("Result NULL");
		}
		Object jsonOutObtained = transformToString(out, !(jsonOutPutExpected.toString().contains(".0")));

		String expected = removeSymbols(jsonOutPutExpected.toString());
		String obtained = removeSymbols(jsonOutObtained.toString());
		System.out.println("outPut expected-->" + expected);

		System.out.println("outPut obtained-->" + obtained);
		boolean correct = expected.equals(obtained);
		System.out.println("correct?" + correct);

		return new String[] { expected, obtained };
	}

	public static String removeSymbols(String r) {
		if (r.length() >= 2 && r.charAt(0) == '"' && r.charAt(r.length() - 1) == '"') {
			r = r.substring(1, r.length() - 1);
		}
		r = r.replaceAll("\\(", "[").replaceAll("\\)", "]").replaceAll(" ", "");
		return r;
	}

	public static String transformToString(Object out, boolean mustRemoveDecimal) {
		if (out instanceof Iterable) {
			String r = "[";
			for (Object o : (Iterable) out) {
				r += transformToString(o, mustRemoveDecimal) + ",";
			}
			if (r.length() >= 2) {
				r = r.trim().substring(0, r.length() - 1);
			}

			return r + "]";
		} else {
			String s = "";
			if (out instanceof String && !isInteger(out.toString()))
				s +=  out.toString() ;
			else {
				s = (mustRemoveDecimal && out.toString().endsWith(".0")
						? out.toString().substring(0, out.toString().length() - 2) : out.toString());
			}
			return s;
		}

	}

	public static boolean isInteger(String s) {
		boolean isValidInteger = false;
		try {
			Integer.parseInt(s);
			isValidInteger = true;
		} catch (NumberFormatException ex) {
		}

		return isValidInteger;
	}
}
