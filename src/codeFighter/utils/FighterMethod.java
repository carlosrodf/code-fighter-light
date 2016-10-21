package codeFighter.utils;

import codeFighter.exceptions.FighterException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author carlosrodf
 */
public class FighterMethod {

    private final String methodName;
    private final String methodCode;
    private Class<?>[] parameterTypes;

    private final List<FighterTest> tests;

    public FighterMethod(String methodName, String methodCode) {
        this.methodCode = methodCode;
        this.methodName = methodName;
        this.parameterTypes = null;
        this.tests = new ArrayList<>();
    }

    public boolean isValid() {
        return !methodName.equals("") && !methodCode.equals("") && parameterTypes != null;
    }

    public void addTest(FighterTest test) throws FighterException {
        if (test.getParameterValues().length == this.parameterTypes.length) {
            this.tests.add(test);
        } else {
            throw new FighterException("Invalid number of parameters");
        }
    }

    public void addTest(boolean hidden, Object expectedReturnValue, Object... parameterValues) throws FighterException {
        if (parameterValues.length == parameterTypes.length) {
            if (hidden) {
                this.tests.add(FighterTest.createHiddenTest(parameterValues, expectedReturnValue));
            } else {
                this.tests.add(FighterTest.createPublicTest(parameterValues, expectedReturnValue));
            }
        } else {
            throw new FighterException("Invalid number of parameters");
        }

    }

    public void removeAllTests() {
        this.tests.clear();
    }

    public void setParameterTypes(Class<?>... parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getMethodCode() {
        return methodCode;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public List<FighterTest> getTests() {
        return tests;
    }

}
