package index;


import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

/**
 * Classe permettant de parser un fichier XML et de récupérer la liste des mots
 * @author Brizai Olivier & Maxime Thoraval
 *
 */
public class XHTMLFileParser implements ContentHandler {
	
	private HashMap<String, Double> _mots;
	
	/**
	 * Constructeur par défaut
	 */
	public XHTMLFileParser(){
		 super();
		 _mots = new HashMap<String, Double> ();
	}

	/**
	 * Retourne la liste des mots d'un fichier avec leur occurence
	 * @param fichier Le fichier dont on veut retourner les mots
	 * @return une hashmap contenant la liste des mots en clé, et leur nombre d'occurences en objet
	 * @throws IOException
	 */
	public HashMap<String, Double> getMots(String fichier)  throws IOException{
		return _mots;	
	}

	@Override
	public void characters(char[] arg0, int arg1, int arg2) throws SAXException {
		String lecture = new String(arg0,arg1,arg2);
		StringTokenizer token = new StringTokenizer(lecture," ,;:.\"!?-‚Äú‚Äù\n\r\t\f");
		System.out.println(token.countTokens());
		while(token.hasMoreTokens()){
			String mot = ((String)token.nextElement()).toLowerCase();
			if(_mots.get(mot) != null)
				_mots.put(mot, _mots.get(mot) + 1);
			else
				_mots.put(mot, 1.0);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endElement(String arg0, String arg1, String arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endPrefixMapping(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ignorableWhitespace(char[] arg0, int arg1, int arg2)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingInstruction(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDocumentLocator(Locator arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skippedEntity(String arg0) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startElement(String arg0, String arg1, String arg2,
			Attributes arg3) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startPrefixMapping(String arg0, String arg1)
			throws SAXException {
		// TODO Auto-generated method stub
		
	}
}
