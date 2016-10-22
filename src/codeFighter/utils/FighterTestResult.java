package codeFighter.utils;

/**
 *
 * @author carlosrodf
 */
public class FighterTestResult {

    private final Object[] inputParams;
    private final Object expected;
    private final Object actual;
    private final boolean passed;
    private final boolean hidden;

    public FighterTestResult(Object[] inputParams, Object expected, Object actual, boolean hidden) {
        this.inputParams = inputParams;
        this.expected = expected;
        this.actual = actual;
        this.passed = expected.equals(actual);
        this.hidden = hidden;
    }

    public Object[] getInputParams() {
        return (hidden) ? getDummyParams() : inputParams;
    }
    
    public String getFormatedInputParams(){
        String formatedParams = "[";
        if(this.hidden){
            for(Object obj : getDummyParams()){
                formatedParams += "***,";
            }
        }else{
            for(Object obj : inputParams){
                formatedParams += obj+",";
            }
        }
        return formatedParams.substring(0,formatedParams.length()-1)+"]";
    }

    public Object getExpected() {
        return (hidden) ? "***" : expected;
    }

    public Object getActual() {
        return (hidden) ? "***" : actual;
    }

    public boolean isPassed() {
        return passed;
    }

    public boolean isHidden() {
        return hidden;
    }

    private Object[] getDummyParams() {
        Object[] dummyParams = new Object[inputParams.length];
        for (int i = 0; i < inputParams.length; i++) {
            dummyParams[i] = "***";
        }
        return dummyParams;
    }

}
