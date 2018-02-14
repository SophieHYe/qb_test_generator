package junit_test_generator;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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

	@SuppressWarnings("rawtypes")
	public void createTestCase(List<String> inputs, String program, String output) throws Exception {
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
		comp.addInputResource("/Users/matias/develop/code/qf/src/main/java/");
		comp.setBinaryOutputDirectory(outputBinDirectory);
		comp.getEnvironment().setShouldCompile(true);
		comp.buildModel();
		Factory f = (Factory) comp.getFactory();
		String name = program.substring(0, 1).toUpperCase() + program.substring(1) + "Test";
		CtClass ctclass = f.createClass(name);
		CtPackage p = f.Core().createPackage();
		// p.setSimpleName("java_programs_test");
		// ctclass.setParent(p);
		ctclass.setVisibility(ModifierKind.PUBLIC);

		int i = 0;
		for (String input_i : inputs) {

			CtMethod ctm = f.createMethod();
			ctm.setSimpleName("test_" + (i++));
			ctm.setVisibility(ModifierKind.PUBLIC);
			ctm.setType(f.Type().VOID.unbox());
			CtBlock block = new CtBlockImpl<>();

			ctm.setBody(block);
			ctclass.addMethod(ctm);

			ctm.addThrownType(f.createCtTypeReference(Exception.class));

			String packageName = "correct_java_programs";

			JsonArray jelement = (JsonArray) new JsonParser().parse(input_i);
			JsonElement jsonInput = jelement.get(0);
			JsonElement jsonOutPutExpected = jelement.get(1);
			boolean isOutputDecimal = (jsonOutPutExpected.toString().contains(".0"));
			String expected = QuixFixLauncher.removeSymbols(jsonOutPutExpected.toString());

			// String[] command = QuixFixLauncher.prepareCommand(program,
			// jsonInput);
			Type[] parametersTypes = JavaDeserialization.getTypesOfParameters(program, program.toUpperCase(),
					packageName);
			
			Class returnType = JavaDeserialization.getReturnType(program, program.toUpperCase(),
					packageName);
			// String realArguments = "\"" + packageName + "\"," + "\"" +
			// program + "\",\""
			// + input_i.replace("\"", "\\\"");

			CtCodeSnippetStatement stmtInvProgramUnderRepair = f.Core().createCodeSnippetStatement();
			stmtInvProgramUnderRepair.setValue(returnType.getCanonicalName()
					+ " result = " + packageName + "." + program.toUpperCase() + "."
					+ program.toLowerCase() +"("+ (getParametersString(parametersTypes,  jsonInput))+ ")");
			// execute(String methodName, String className, String packageName,
			// String[] args)
			// /+ (createCommand(program.toLowerCase(), program.toUpperCase(),
			// packageName, command)));
			block.addStatement(stmtInvProgramUnderRepair);

			CtCodeSnippetStatement stmtCall = f.Core().createCodeSnippetStatement();

			stmtCall.setValue("String resultFormatted = " + QuixFixLauncher.class.getCanonicalName() + ".format("
					+ "result.toString()," + (!isOutputDecimal) + ")");
			//block.addStatement(stmtCall);
			CtCodeSnippetStatement stmtAssert = f.Core().createCodeSnippetStatement();
			String expectedObject = getParameterObject(jsonOutPutExpected, returnType.getName());
			//String expectedString =  expected.replace("\"","\\\"");
			stmtAssert.setValue("org.junit.Assert.assertEquals(" 
	//	+ "\"" 
					+ 
					expectedObject//expectedString 
	//		+ "\"" 
					+ ", result)");
			block.addStatement(stmtAssert);

			AnnotationFactory af = f.Annotation();
			af.annotate(ctm, org.junit.Test.class, "timeout", TIMEOUT);

		}
		System.out.println("Class :\n" + ctclass);
		// comp.addInputResource("/Users/matias/develop/code/qf/target/classes/");

		comp.setBinaryOutputDirectory(outputBinDirectory);
		comp.setSourceOutputDirectory(outputSrcDirectory);
		comp.getFactory().getEnvironment().setShouldCompile(true);
		comp.getFactory().getEnvironment().setNoClasspath(true);
		comp.getModelBuilder().addInputSource(new File("/Users/matias/develop/code/qf/src/main/java/"));
		comp.getModelBuilder().compile();
		JDTBasedSpoonCompiler jdtSpoonModelBuilder = new JDTBasedSpoonCompiler(f);
		jdtSpoonModelBuilder.setSourceOutputDirectory(outputSrcDirectory);
		jdtSpoonModelBuilder.generateProcessedSourceFiles(OutputType.COMPILATION_UNITS);
		// jdtSpoonModelBuilder.setBinaryOutputDirectory(outputBinDirectory);
		jdtSpoonModelBuilder.compile(InputType.CTTYPES);
		jdtSpoonModelBuilder.generateProcessedSourceFiles(OutputType.CLASSES);

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
		///
		String[] names = new String[] { "bitcount", "bucketsort",

				"find_first_in_sorted", "find_in_sorted", "flatten", "gcd", "get_factors", "hanoi",
				"is_valid_parenthesization", "kheapsort", "knapsack", "kth", "lcs_length", "levenshtein", "lis",
				"longest_common_subsequence", "max_sublist_sum", "mergesort", "next_palindrome", "next_permutation",
				"pascal", "possible_change", "powerset", "quicksort", "rpn_eval", "shunting_yard", "sieve", "sqrt",
				"subsequences", "to_base", "wrap", };
		for (String prog : names) {

			List<String> tests = QuixFixLauncher.read(DIR_JSON + File.separator + prog + ".json");

			String programToExecute = prog;

			String out = ROOT_SOURCES_DIR;
			QuixBugsTestGenerator ct = new QuixBugsTestGenerator();
			ct.createTestCase(tests, programToExecute, out);

		}

	}
/**
 * By Sophie Ye.
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
				parameterStr += getParameterObject(elementJSON, thisType)+ ",";
			}
		} else {
			System.out.println("Incompatible types: Object cannot be converted.");
		}
		return parameterStr.substring(0, parameterStr.length() - 1);
	}



private static String getParameterObject( JsonElement j, String thisType) {
	String parameterStr = "";
	if ("java.util.ArrayList".equals(thisType)) {
		String arrStr = j.toString().replace("[", "(").replace("]", ")");
		parameterStr = parameterStr + "new " + thisType + "(java.util.Arrays.asList" + arrStr + ")";
	} else if (thisType.contains("[]")) {
		String arrStr = j.toString().replace("[", "{").replace("]", "}");
		parameterStr = parameterStr + "new " + thisType + "" + arrStr ;
	} else if ("java.lang.Object".equals(thisType)) {
		new JsonParser().parse(j.toString());
		String str = j.toString().replace("\"", "\\\"");
		parameterStr = parameterStr + "new com.google.gson.JsonParser().parse(\"" + str + "\")";
	} else {
		parameterStr = parameterStr + "(" + thisType + ")" + j.toString() ;
	}
	return parameterStr;
}

}
