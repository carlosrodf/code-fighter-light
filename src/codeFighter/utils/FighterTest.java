package codeFighter.utils;

/**
 *
 * @author carlosrodf
 */
public class FighterTest {
    
    private final Object[] parameterValues;
    private final Object expectedReturnValue;

    private final boolean hidden;
    
    public static FighterTest createHiddenTest(Object expectedReturnValue,Object... parameterValues){
        return new FighterTest(parameterValues,expectedReturnValue,true);
    }
    
    public static FighterTest createPublicTest(Object expectedReturnValue,Object... parameterValues){
        return new FighterTest(parameterValues,expectedReturnValue,false);
    }

    public Object[] getParameterValues() {
        return parameterValues;
    }
    
    public boolean testPassed(Object actualReturnValue){
        return expectedReturnValue.equals(actualReturnValue);
    }
    
    public boolean isHidden() {
        return hidden;
    }
    
    private FighterTest(Object[] parameterValues, Object expectedReturnValue,boolean hidden){
        this.parameterValues = parameterValues;
        this.expectedReturnValue = expectedReturnValue;
        this.hidden = hidden;
    }

    public Object getExpectedReturnValue() {
        return expectedReturnValue;
    }
    
}
