package edu.vanderbilt.cs278.lili.pubnubclient;

public class SavedState {
	private int colorState = -1;;
	
	public int getColorState(){
		return this.colorState;
	}
	
	public void setColorState(int newcolor){
		this.colorState = newcolor;
	}
}
