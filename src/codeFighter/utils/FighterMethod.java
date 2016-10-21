package codeFighter.utils;

/**
 *
 * @author carlosrodf
 */
public class FighterMethod {
    
    private final String methodName;
    private final String methodCode;
    private Class<?>[] parameterTypes;
    private Object[] parameterValues;
    
    public FighterMethod(String methodCode, String methodName){
        this.methodCode = methodCode;
        this.methodName = methodName;
        this.parameterTypes = null;
        this.parameterValues = null;
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
