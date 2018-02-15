package junit_test_generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static java.nio.file.StandardCopyOption.*;

public class CopyFiles {

	public static void main(String[] args) throws IOException {
		String programFolder = "/Users/matias/develop/code/qf/src/main/java/";
		String testFolder = "/Users/matias/develop/code/qf/src/test/java/";
		String destinationFolder = "/Users/matias/develop/code/qf/src/test/resources/gen/";

		String[] names = new String[] { "bitcount", "bucketsort",

				"find_first_in_sorted", "find_in_sorted", "flatten", "gcd", "get_factors", "hanoi",
				"is_valid_parenthesization", "kheapsort", "knapsack", "kth", "lcs_length", "levenshtein", "lis",
				"longest_common_subsequence", "max_sublist_sum", "mergesort", "next_palindrome", "next_permutation",
				"pascal", "possible_change", "powerset", "quicksort", "rpn_eval", "shunting_yard", "sieve", "sqrt",
				"subsequences", "to_base", "wrap", };

		for (String prog : names) {
			File progFile = new File(
					programFolder + File.separator + "java_programs" + File.separator + prog + ".java");
			if (!progFile.exists())
				throw new IllegalArgumentException("FNF " + progFile);
			else {
				File dest = new File(destinationFolder + File.separator + prog + File.separator + "src" + File.separator
						+ "java_programs");
				dest.mkdirs();

				Files.copy(progFile.toPath(), new File(dest.getPath() + File.separator + prog + ".java").toPath(),
						REPLACE_EXISTING);
			}

			File testFile = new File(
					testFolder + File.separator + "java_programs_test" + File.separator + prog + "Test.java");
			if (!progFile.exists())
				throw new IllegalArgumentException("FNF");
			else {
				File dest = new File(destinationFolder + File.separator + prog + File.separator + "src" + File.separator
						+ "java_programs_test" + File.separator);
				dest.mkdirs();
				Files.copy(testFile.toPath(), new File(dest.getPath() + File.separator + prog + "Test.java").toPath(),
						REPLACE_EXISTING);
			}

		}

	}

}
