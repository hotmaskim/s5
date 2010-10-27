package ensicaen.tb.mvc.eleves.dao;

import java.util.Collection;

import ensicaen.tb.mvc.eleves.entities.Eleve;

public interface IDAO {
	
	Collection<Eleve> getAll();
	Eleve getOne(int id);
	void saveOne(Eleve e);
	void deleteOne(Eleve e);
	
}
