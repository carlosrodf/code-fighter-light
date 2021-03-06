package codeFighter.core;

import codeFighter.exceptions.FighterException;
import codeFighter.utils.FighterMethod;
import codeFighter.utils.FighterTest;
import codeFighter.utils.FighterTestResult;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodf
 */
public class Fighter {

    private FighterMethod fighterMethod;
    private final String userID;
    private final String TEMPLATE_PATH;

    private String algorithmTemplate;
    private String manifestTemplate;

    public Fighter(String userID) {
        this.userID = userID;
        this.TEMPLATE_PATH = "codeFighter/resources/";
        this.fighterMethod = null;
        initTemplate();
    }

    public void loadMethodToTest(FighterMethod fighterMethod) throws FighterException {
        if (fighterMethod.isValid()) {
            this.fighterMethod = fighterMethod;
        } else {
            throw new FighterException("The method is not valid. Check if all the method's parameters are set");
        }
    }

    public void updateTestCode(String newCode) throws FighterException {
        if (fighterMethod != null) {
            this.fighterMethod.setMethodCode(newCode);
        } else {
            throw new FighterException("A FighterMethod has not been loaded to this fighter");
        }
    }

    public void createTempJavaFile() throws FighterException {
        try {
            String code = algorithmTemplate;
            code = code.replace("{{##:testCode:##}}", fighterMethod.getMethodCode());
            code = code.replace("{{##:className:##}}", "Algorithm_" + userID);

            File file = new File("Algorithm_" + userID + ".java");

            BufferedWriter writer;
            writer = new BufferedWriter(new FileWriter(file));
            writer.write(code);
            writer.close();
        } catch (Exception e) {
            throw new FighterException("There was a problem writing the .java file", e);
        }
    }

    public void deleteTempFiles() throws FighterException {
        try {
            Runtime.getRuntime().exec("rm Algorithm_" + userID + ".java");
            Runtime.getRuntime().exec("rm Algorithm_" + userID + ".class");
            Runtime.getRuntime().exec("rm Algorithm_" + userID + ".jar");
            Runtime.getRuntime().exec("rm Manifest_" + userID + ".txt");
        } catch (Exception e) {
            throw new FighterException("Unable to delete all source files", e);
        }
    }

    public List<FighterTestResult> runTests() throws FighterException {
        List<FighterTestResult> testResults = new ArrayList<>();
        for (FighterTest test : fighterMethod.getTests()) {
            Object actual = executeForResult(test.getParameterValues());
            FighterTestResult result = new FighterTestResult(test.getParameterValues(),
                    test.getExpectedReturnValue(),
                    actual,
                    test.isHidden());
            testResults.add(result);
        }
        return testResults;
    }

    public Object executeForResult(Object... parameterValues) throws FighterException {
        try {
            File file = new File("Algorithm_" + userID + ".jar");

            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};

            ClassLoader cl = new URLClassLoader(urls);
            Class cls = cl.loadClass("Algorithm_" + userID);
            Object instance = cls.newInstance();
            Method metodo = cls.getMethod(fighterMethod.getMethodName(), fighterMethod.getParameterTypes());

            return metodo.invoke(instance, parameterValues);
        } catch (MalformedURLException ex) {
            throw new FighterException("There was an error getting the method's .jar file", ex);
        } catch (ClassNotFoundException ex) {
            throw new FighterException("There was an error loading the test class", ex);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new FighterException("CodeFighter was unable to instanciate de test class", ex);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new FighterException("The provided method name was not found", ex);
        } catch (IllegalArgumentException | InvocationTargetException ex) {
            throw new FighterException("The provided method parameter types and values don't match with the code", ex);
        }

    }

    public void compileExecutable() throws FighterException {
        try {
            generateClassFile();
            createManifestFile();
            generateJarFile();
        } catch (Exception ex) {
            throw new FighterException(ex);
        }
    }

    private void generateClassFile() throws IOException, InterruptedException {
        Process p;
        p = Runtime.getRuntime().exec("javac Algorithm_" + userID + ".java");
        p.waitFor();
    }

    private void createManifestFile() throws IOException {
        String code = manifestTemplate;
        code = code.replace("{{##:userID:##}}", userID);

        File file = new File("Manifest_" + userID + ".txt");

        BufferedWriter writer;
        writer = new BufferedWriter(new FileWriter(file));
        writer.write(code);
        writer.close();
    }

    private void generateJarFile() throws IOException, InterruptedException {
        Process p;
        p = Runtime.getRuntime().exec("jar cfm Algorithm_" + userID + ".jar Manifest_" + userID + ".txt Algorithm_" + userID + ".class");
        p.waitFor();
    }

    private void initTemplate() {
        this.algorithmTemplate = "public class {{##:className:##}} {\n"
                + "\n"
                + "    public static void main(String[] args) {\n"
                + "\n"
                + "    }\n"
                + "\n"
                + "    {{##:testCode:##}}\n"
                + "\n"
                + "}";
        this.manifestTemplate = "Main-Class: Algorithm_{{##:userID:##}}";
    }

}
