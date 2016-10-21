package codeFighter.utils;

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
    private Object[] parameterValues;
    
    private List<FighterTest> tests;
    
    public FighterMethod(String methodCode, String methodName){
        this.methodCode = methodCode;
        this.methodName = methodName;
        this.parameterTypes = null;
        this.parameterValues = null;
        this.tests = new ArrayList<>();
    }
    
    public boolean isValid(){
        if (!methodName.equals("") && !methodCode.equals("")){
            if(parameterTypes == null && parameterValues == null){
                return true;
            }else if(parameterTypes == null || parameterValues == null){
                return false;
            }else{
                return parameterTypes.length == parameterValues.length;
            }
        }else{
            return false;
        }
    }
    
    public void addTest(FighterTest test){
        this.tests.add(test);
    }
    
    public void addTest(Object[] parameterValues, Object expectedReturnValue,boolean hidden){
        if(hidden){
            this.tests.add(FighterTest.createHiddenTest(parameterValues, expectedReturnValue));
        }else{
            this.tests.add(FighterTest.createPublicTest(parameterValues, expectedReturnValue));
        }
    }
    
    public void removeAllTests(){
        this.tests.clear();
    }
    
    public void setParameterTypes(Class<?>... parameterTypes){
        this.parameterTypes = parameterTypes;
    }
    
    public void setParameterValues(Object... parameterValues){
        this.parameterValues = parameterValues;
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

    public Object[] getParameterValues() {
        return parameterValues;
    }
    
}
