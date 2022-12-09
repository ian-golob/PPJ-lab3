package semantic;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    @ParameterizedTest
    @MethodSource("provideNewTestDirectoryNames")
    public void integrationTest(String directoryName) throws IOException, ClassNotFoundException {
        String pathPrefix = "./src/test/resources/" + directoryName;

        String inFileName = pathPrefix + "/test.in";
        String outFileName = pathPrefix + "/test.out";
        String myFileName = pathPrefix + "/test.my";


        //run analyzer
        try(InputStream input = new FileInputStream(inFileName);
            PrintStream output = new PrintStream(new FileOutputStream(myFileName))){

            SemantickiAnalizator sa = new SemantickiAnalizator();

            sa.analyzeInput(input, output, System.err);
        }

        String myOutput = Files.readString(Path.of(myFileName));
        String correctOutput = Files.readString(Path.of(outFileName));

        assertEquals(normalizeString(correctOutput), normalizeString(myOutput));
    }


    private static Stream<Arguments> provideNewTestDirectoryNames() {

        File[] officialExamplesDirectories
                = new File("./src/test/resources/official-test-examples").listFiles(File::isDirectory);
        File[] exampleDirectories1112
                = new File("./src/test/resources/11-12-test-examples").listFiles(File::isDirectory);

        List<String> args = new ArrayList<>();

        for(File file: officialExamplesDirectories){
            args.add("official-test-examples/"+file.getName());
        }

        for(File file: exampleDirectories1112){
            args.add("11-12-test-examples/"+file.getName());
        }

        return args.stream().map(Arguments::of);
    }

    public static String normalizeString(String s){
        return s.replace("\r\n", "\n")
                .replace("\r", "\n").trim();
    }
}
