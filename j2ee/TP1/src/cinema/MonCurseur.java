package cinema;

import java.io.*;
import java.sql.*;

public class MonCurseur {
	private ResultSet curs;
	private 	int cpt, nbChamps;
	private ResultSetMetaData info;
			
			
	private	PrintWriter aff_entete(PrintWriter output) throws SQLException
		{
			cpt=0;
			output.println("<table BORDER=1><tr>");
			
			for (int i=1;i<= nbChamps;i++)
			{
				output.print("<th>"+info.getColumnName(i)+"</th>");
		
			}
			output.println("</tr>");
			return output;
		}
		
	private	PrintWriter aff_corps(PrintWriter output)throws SQLException
		{
			while ( curs.next() )
        		{
        		output.println ("<tr>");
          		for (int i=1; i<=nbChamps ; i++ )
            			output.println("<td>"+curs.getString(i)+"</td>");
			cpt++;
          		output.println ("</tr>");
        		}
			output.println("</tr>");
			return output;
		}
		
	private PrintWriter aff_fin(PrintWriter output)
		{
			output.println("</table>"+ cpt+ " ligne(s) <br>");
			return output;
		}
		
		
		
	public MonCurseur(Connection c, String laRequete)throws SQLException
		{
			Statement  requete = c.createStatement();
			curs = requete.executeQuery(laRequete);
			info = curs.getMetaData();
     			nbChamps = info.getColumnCount();
		}
		
	public PrintWriter affiche(PrintWriter output)throws SQLException
		{
			aff_entete(output) ; aff_corps(output) ; aff_fin (output);
      return output ;

		}
		
	/*public PrintWriter aff_zone_liste(PrintWriter output, String nom_zone, int numcol)throws SQLException
		{
			/* A completer */
		//}
		
	/*public PrintWriter aff_cases_a_cocher(PrintWriter output, String nom_zone, int numcol)throws SQLException
		{
			/* A completer */
		//}
	
		
}
