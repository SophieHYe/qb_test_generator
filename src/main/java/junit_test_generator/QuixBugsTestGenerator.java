package junit_test_generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import spoon.Launcher;
import spoon.OutputType;
import spoon.SpoonModelBuilder.InputType;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtCodeSnippetStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.ModifierKind;
import spoon.reflect.factory.AnnotationFactory;
import spoon.reflect.factory.Factory;
import spoon.support.compiler.jdt.JDTBasedSpoonCompiler;
import spoon.support.reflect.code.CtBlockImpl;

/**
 * Class that generates the test cases from the json files.
 * 
 * @author Matias Martinez
 *
 */
public class QuixBugsTestGenerator {

	public static int TIMEOUT = 2000;
	public static int DELTA_FLOAT_COMPARISON = 0;

	public static double[] DELTAS_TESTS_SQRT = new double[] { 0.01, 0.5, 0.3, 0.2, 0.01, 0.05, 0.03 };

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createTestCase(List<String> inputs, String program, String output, String packageName)
			throws Exception {
		System.out.println("generating test case for program : " + program);

		boolean includecomplete = false;
		File outputBinDirectory = new File(
				output + ((includecomplete) ? (program + File.separator + "bin" + File.separator) : ""));
		File outputSrcDirectory = new File(
				output + ((includecomplete) ? (program + File.separator + "src" + File.separator) : ""));
		outputSrcDirectory.mkdirs();
		if (!outputBinDirectory.exists()) {
			outputBinDirectory.mkdirs();
		}
		final Launcher comp = new Launcher();
		comp.setBinaryOutputDirectory(outputBinDirectory);
		comp.getEnvironment().setShouldCompile(true);
		comp.buildModel();
		Factory f = (Factory) comp.getFactory();
		String name = program.substring(0, 1).toUpperCase() + program.substring(1) + "Test";
		CtClass ctclass = f.createClass(name);
		CtPackage p = f.Core().createPackage();
		 p.setSimpleName("java_programs_test");
		 ctclass.setParent(p);
		ctclass.setVisibility(ModifierKind.PUBLIC);

		int i_nr_testcase = 0;
		for (String input_i : inputs) {

			CtMethod ctm = f.createMethod();
			ctm.setSimpleName("test_" + (i_nr_testcase));
			ctm.setVisibility(ModifierKind.PUBLIC);
			ctm.setType(f.Type().VOID.unbox());
			CtBlock block = new CtBlockImpl<>();

			ctm.setBody(block);
			ctclass.addMethod(ctm);

			ctm.addThrownType(f.createCtTypeReference(Exception.class));

			JsonArray jelement = (JsonArray) new JsonParser().parse(input_i);
			JsonElement jsonInput = jelement.get(0);
			JsonElement jsonOutPutExpected = jelement.get(1);
			String expected = QuixFixLauncher.removeSymbols(jsonOutPutExpected.toString());

			Type[] parametersTypes = getTypesOfParameters(program, program.toUpperCase(), packageName);

			Class returnType = getReturnType(program, program.toUpperCase(), packageName);
			boolean isOutputDecimal = returnType.getSimpleName().toLowerCase().equals("double");

			CtCodeSnippetStatement stmtInvProgramUnderRepair = f.Core().createCodeSnippetStatement();
			stmtInvProgramUnderRepair.setValue(
					returnType.getCanonicalName() + " result = " + packageName + "." + program.toUpperCase() + "."
							+ program.toLowerCase() + "(" + (getParametersString(parametersTypes, jsonInput)) + ")");

			block.addStatement(stmtInvProgramUnderRepair);

			if (isNumber(returnType) || returnType.isPrimitive()) {
				CtCodeSnippetStatement stmtAssert = f.Core().createCodeSnippetStatement();
				if (returnType.getSimpleName().toLowerCase().equals("double")) {
					stmtAssert.setValue("org.junit.Assert.assertEquals( (" + returnType.getCanonicalName() + ") "
							+ expected + ", result, " + getFloatComparisonDelta(program, i_nr_testcase) + ")");
				} else {
					stmtAssert.setValue("org.junit.Assert.assertEquals( (" + returnType.getCanonicalName() + ") "
							+ expected + ", result" + ")");

				}
				block.addStatement(stmtAssert);

			} else {
				CtCodeSnippetStatement stmtCall = f.Core().createCodeSnippetStatement();

				stmtCall.setValue("String resultFormatted = " + QuixFixLauncher.class.getCanonicalName() + ".format("
						+ "result," + (!isOutputDecimal) + ")");
				block.addStatement(stmtCall);
				CtCodeSnippetStatement stmtAssert = f.Core().createCodeSnippetStatement();
				stmtAssert.setValue("org.junit.Assert.assertEquals(" + "\""
						+ QuixFixLauncher.format(expected, (!isOutputDecimal)) + "\"" + ", resultFormatted)");
				block.addStatement(stmtAssert);
			}
			AnnotationFactory af = f.Annotation();
			af.annotate(ctm, org.junit.Test.class, "timeout", TIMEOUT);
			i_nr_testcase++;
		}
		System.out.println("Class :\n" + ctclass);

		comp.setBinaryOutputDirectory(outputBinDirectory);
		comp.setSourceOutputDirectory(outputSrcDirectory);
		comp.getFactory().getEnvironment().setShouldCompile(true);
		comp.getFactory().getEnvironment().setNoClasspath(true);
		comp.getModelBuilder().compile();
		JDTBasedSpoonCompiler jdtSpoonModelBuilder = new JDTBasedSpoonCompiler(f);
		jdtSpoonModelBuilder.setSourceOutputDirectory(outputSrcDirectory);
		jdtSpoonModelBuilder.generateProcessedSourceFiles(OutputType.COMPILATION_UNITS);
		// jdtSpoonModelBuilder.setBinaryOutputDirectory(outputBinDirectory);
		jdtSpoonModelBuilder.compile(InputType.CTTYPES);
		jdtSpoonModelBuilder.generateProcessedSourceFiles(OutputType.CLASSES);

	}

	public boolean isNumber(Class type) {

		return Number.class.isAssignableFrom(type);
	}

	/**
	 * By Sophie Ye, and refactored.
	 * 
	 * @param types
	 * @param input
	 * @return
	 */
	public static String getParametersString(Type[] types, JsonElement input) {

		JsonArray inputArray;
		if (input.isJsonArray()) {
			inputArray = input.getAsJsonArray();
		} else {
			inputArray = new JsonArray();
			inputArray.add(input);
		}
		String parameterStr = "";
		if (types.length == inputArray.size()) {
			for (int i = 0; i < types.length; i++) {
				JsonElement elementJSON = inputArray.get(i);
				String thisType = types[i].getTypeName();
				parameterStr += getParameterObject(elementJSON, thisType) + ",";
			}
		} else {
			System.out.println("Incompatible types: Object cannot be converted.");
		}
		return parameterStr.substring(0, parameterStr.length() - 1);
	}

	private static String getParameterObject(JsonElement jsonElement, String thisType) {
		String parameterStr = "";
		if ("java.util.ArrayList".equals(thisType)) {
			String arrStr = jsonElement.toString().replace("[", "(").replace("]", ")");
			parameterStr = parameterStr + "new " + thisType + "(java.util.Arrays.asList" + arrStr + ")";
		} else if (thisType.contains("[]")) {
			String arrStr = jsonElement.toString().replace("[", "{").replace("]", "}");
			parameterStr = parameterStr + "new " + thisType + "" + arrStr;
		} else if ("java.lang.Object".equals(thisType)) {

			if (jsonElement instanceof Iterable) {
				List<String> arguments = new ArrayList<>();
				Iterable it = (Iterable) jsonElement;
				for (Object object : it) {

					arguments.add(getParameterObject((JsonElement) object, "java.lang.Object"));
				}
				String arrStr = arguments.toString().replace("[", "(").replace("]", ")");
				parameterStr = "new java.util.ArrayList(java.util.Arrays.asList" + arrStr + ")";
			} else {
				//
				parameterStr = (jsonElement.toString());
			}
		} else {
			parameterStr = parameterStr + "(" + thisType + ")" + jsonElement.toString();
		}
		return parameterStr;
	}

	/**
	 * First argument: folder where tests are written
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {

		String ROOT_DIR = null;
		String ROOT_SOURCES_DIR = null;

		String packageName = null;

		System.out.println(
				"Usage: Argument 1: Root dir, Arg 2: path to folder of JSON files; Arg 3: package of buggy subject");

		if (args.length > 0) {

			File froot = new File(args[0]);
			if (!froot.exists()) {
				throw new IllegalArgumentException("File not found " + froot.getAbsolutePath());
			}
			ROOT_DIR = froot.getAbsolutePath();
			ROOT_SOURCES_DIR = ROOT_DIR;
		}

		else {
			ROOT_DIR = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath())
					.getParentFile().getParent();
			ROOT_SOURCES_DIR = ROOT_DIR + "/src/test/java/";
		}
		String DIR_JSON = null;

		if (args.length >= 2) {

			DIR_JSON = args[1];
		} else {
			DIR_JSON = ROOT_DIR + "/src/test/resources/json_testcases/";
		}

		if (args.length >= 3) {

			packageName = args[2];
		} else {
			packageName = "correct_java_programs";
		}

		String[] names = new String[] { "bitcount", "bucketsort",

				"find_first_in_sorted", "find_in_sorted", "flatten", "gcd", "get_factors", "hanoi",
				"is_valid_parenthesization", "kheapsort", "knapsack", "kth", "lcs_length", "levenshtein", "lis",
				"longest_common_subsequence", "max_sublist_sum", "mergesort", "next_palindrome", "next_permutation",
				"pascal", "possible_change", "powerset", "quicksort", "rpn_eval", "shunting_yard", "sieve", "sqrt",
				"subsequences", "to_base", "wrap", };
		for (String prog : names) {

			List<String> tests = read(DIR_JSON + File.separator + prog + ".json");

			String programToExecute = prog;

			String out = ROOT_SOURCES_DIR;
			QuixBugsTestGenerator ct = new QuixBugsTestGenerator();
			ct.createTestCase(tests, programToExecute, out, packageName);

		}

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

	public static Type[] getTypesOfParameters(String methodName, String className, String packageName)
			throws Exception {
		// Get parameter type for class method.
		// try {
		Class target_class = Class.forName(packageName + "." + className);
		Method[] method = target_class.getDeclaredMethods();

		for (Method m : method) {
			if (!m.getName().equals(methodName)) {
				continue;
			}

			Type[] types = m.getParameterTypes();
			return types;
		}
		return null;
	}

	public static Class getReturnType(String methodName, String className, String packageName) throws Exception {
		// Get parameter type for class method.
		// try {
		Class target_class = Class.forName(packageName + "." + className);
		Method[] method = target_class.getDeclaredMethods();

		for (Method m : method) {
			if (!m.getName().equals(methodName)) {
				continue;
			}

			Class type = m.getReturnType();
			return type;
		}
		return null;
	}

	private static double getFloatComparisonDelta(String program, int nrTestcase) {
		if (program.toLowerCase().equals("sqrt")) {
			return DELTAS_TESTS_SQRT[nrTestcase];
		} else {
			return DELTA_FLOAT_COMPARISON;
		}

	}

}
