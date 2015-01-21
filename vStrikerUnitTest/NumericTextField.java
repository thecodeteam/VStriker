package com.emccode.vstriker.model;

import javafx.scene.control.TextField;

public class NumericTextField extends TextField {
	public NumericTextField() {
		this.setPromptText("Num Only");
	}

	@Override
	public void replaceText(int i, int il, String string)
	{
		if(string.matches("[0-9]")||string.isEmpty())
		{
			super.replaceText(i, il,string);
		}
		
	}
	
	@Override
	public void replaceSelection(String string)
	{		super.replaceSelection(string);
		
	}
	
}
