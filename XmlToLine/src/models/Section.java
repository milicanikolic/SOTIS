package models;

public class Section {
	
	private String fileName;
	private String content;
	private String sectionType;
	private int lineNumStart;
	private int colNumStart;
	
	private int lineNumEnd;
	private int colNumEnd;
	private String comment;
	
	public Section(){
		super();
	}

	

	public Section(String fileName, String content, String sectionType,
			int lineNumStart, int colNumStart, int colNumEnd, int lineNumEnd, String comment) {
		super();
		this.fileName = fileName;
		this.content = content;
		this.sectionType = sectionType;
		this.lineNumStart = lineNumStart;
		this.colNumStart = colNumStart;
		this.colNumEnd = colNumEnd;
		this.lineNumEnd = lineNumEnd;
		this.comment=comment;
	}



	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSectionType() {
		return sectionType;
	}

	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}

	public int getLineNumStart() {
		return lineNumStart;
	}

	public void setLineNumStart(int lineNumStart) {
		this.lineNumStart = lineNumStart;
	}

	public int getLineNumEnd() {
		return lineNumEnd;
	}

	public void setLineNumEnd(int lineNumEnd) {
		this.lineNumEnd = lineNumEnd;
	}
	
	
	public int getColNumStart() {
		return colNumStart;
	}



	public void setColNumStart(int colNumStart) {
		this.colNumStart = colNumStart;
	}



	public int getColNumEnd() {
		return colNumEnd;
	}



	public void setColNumEnd(int colNumEnd) {
		this.colNumEnd = colNumEnd;
	}



	public String getComment() {
		return comment;
	}



	public void setComment(String comment) {
		this.comment = comment;
	}



	public String toString(){
		return "File name: "+fileName+" starts at line: "+lineNumStart+" ,col: "+colNumStart+ " ends at line: "+lineNumEnd+" ,col: "+colNumEnd+" section type: "+sectionType
				+" comment: "+comment + " content: "+content;
	}
	
	
}
