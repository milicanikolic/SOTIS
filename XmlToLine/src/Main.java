
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import models.Line;
import models.Section;

import org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Main {

	public static void main(String[] args) {
		
		Properties prop=new Properties();
		ClassLoader cl=Thread.currentThread().getContextClassLoader();
		InputStream is=null;
		is=cl.getResourceAsStream("path.properties");
		String path="";
		try {
			prop.load(is);
			path=prop.getProperty("path");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		File folder=new File(path);
		File[] files=folder.listFiles();
		
		ArrayList<Section> sections=new ArrayList<Section>();
		
		for(int i=0; i<files.length; i++){
			if(files[i].isFile()){
				ArrayList<Section> sectionList=parseFile(files[i]);
				for(Section sec:sectionList){
					sections.add(sec);
				}
			}
		}
		
		
		ObjectMapper mapper=new ObjectMapper();
		try {
			mapper.writeValue(new File("res"), sections);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(Section sec:sections){
			System.out.println(sec.toString());
			
		}
		
}
	
	public static String lineFromChar(File file,int startChar, int endChar){
		
		BufferedReader br = null;
		FileReader fr = null;
		ArrayList<Line> lines=new ArrayList<Line>();
		
		int lineNumber=1;
		int start=0;
		int end=0;
		
		

		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);

			String sCurrentLine;

			while ((sCurrentLine = br.readLine()) != null) {
				end+=sCurrentLine.length();
				Line line=new Line(lineNumber, start, end);
				start=++end;
				lineNumber++;
				lines.add(line);
			}
			
			

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

		
		
		String res="";
		int startLine=0;
		int endLine=0;
		int offsetStart=0;
		int offsetEnd=0;
		
		for(Line l:lines){
			if(l.getStartChar()<=startChar && l.getEndChar()>=startChar){
				offsetStart=startChar-l.getStartChar();
				startLine=l.getLineNumber();
			}
			if(l.getStartChar()<=endChar && l.getEndChar()>=endChar){
				offsetEnd=endChar-l.getStartChar();
				if(offsetEnd==0){
					offsetEnd=endChar;
				}
				endLine=l.getLineNumber();
			}
		}
		
			res=String.valueOf(startLine)+"/"+String.valueOf(offsetStart)+"-"+String.valueOf(endLine)+"/"+String.valueOf(offsetEnd);
		
		return res;
	}
	

	
	public static ArrayList<Section> parseFile(File file){
		
		ArrayList<Section> sections=new ArrayList<Section>();
		Section section=null;
		
		String[] fileNames=file.getName().split("\\.");
		String fileName=fileNames[0];
	
		try {
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		
			db = dbf.newDocumentBuilder();
			Document doc=db.parse(file);
			doc.getDocumentElement().normalize();
			File tempFile=null;
			
			NodeList nlText=doc.getElementsByTagName("TEXT");
			for(int i=0; i<nlText.getLength(); i++){
				Node node=nlText.item(i);
				if(node.getNodeType()==Node.ELEMENT_NODE){
					Element element=(Element) node;
					String contentOfFile=node.getTextContent();
					
					
					
					BufferedWriter bw=null;
					try{
						tempFile=new File("tempFile");
						bw=new BufferedWriter(new FileWriter(tempFile));
						bw.write(contentOfFile);
					}catch(Exception e){
						
					}finally{
							try{
								if(bw!=null){
									bw.close();
								}
							}catch(Exception e){
								
							}
						}
					}
				}
			
			
			
			NodeList nl=doc.getElementsByTagName("TAGS");
			
			for(int j=0; j<nl.getLength(); j++){
				Node node=nl.item(j);
				
				NodeList children=node.getChildNodes();
				for(int i=0; i<children.getLength(); i++){
					Node child=children.item(i);
					if(child.getNodeType()==Node.ELEMENT_NODE){
						Element el=(Element) child;
						String sectionType=el.getNodeName();
						String spans=el.getAttribute("spans");
						String[] spansParts=spans.split("~");
						int charStart=Integer.parseInt(spansParts[0]);
						int charEnd=Integer.parseInt(spansParts[1]);
						
						String line=lineFromChar(tempFile, charStart, charEnd);
						String[] linesParsed=line.split("-");
						String[] start=linesParsed[0].split("/");
						String[] end=linesParsed[1].split("/");
						
						int lineStart=Integer.parseInt(start[0]);
						int lineEnd=Integer.parseInt(end[0]);
						
						int colStart=Integer.parseInt(start[1]);
						int colEnd=Integer.parseInt(end[1]);
						
						String content=el.getAttribute("text");
						String comment=el.getAttribute("comment");
						
						section=new Section(fileName, content, sectionType, lineStart,colStart,colEnd, lineEnd, comment);
						sections.add(section);
						
						
						
					}
				}
			}
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return sections;
	}
}
