public class FinalCounter {
	/**
	 * Counter helper class for async process
	 * @author Feiyi(Joey) Xiang 
	 *         
	 */
    private int val;

    public FinalCounter(int intialVal) {
        val=intialVal;
    }
    public void increment(){
        val++;
    }
    public void decrement(){
        val--;
    }
    public int getVal(){
        return val;
    }
    
}