package models;



public class Line {
	
	private int lineNumber;
	private int startChar;
	private int endChar;
	
	
	
	public Line(int lineNumber, int startChar, int endChar) {
		super();
		this.lineNumber = lineNumber;
		this.startChar = startChar;
		this.endChar = endChar;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}
	public int getStartChar() {
		return startChar;
	}
	public void setStartChar(int startChar) {
		this.startChar = startChar;
	}
	public int getEndChar() {
		return endChar;
	}
	public void setEndChar(int endChar) {
		this.endChar = endChar;
	}
	

}
