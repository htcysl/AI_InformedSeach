public class EightPuzzleAction extends Action {
	
	public final int slideSlotX;
	public final int slideSlotY;
	public final int emptySlotX;
	public final int emptySlotY;
	
	EightPuzzleAction(int slideSlotX, int slideSlotY, int emptySlotX, int emptySlotY) 
	{
		this.slideSlotX = slideSlotX;
		this.slideSlotY = slideSlotY;
		this.emptySlotX = emptySlotX;
		this.emptySlotY = emptySlotY;		
	}
	
}
