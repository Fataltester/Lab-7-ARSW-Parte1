/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.services;

import edu.eci.arsw.blueprints.filter.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class BlueprintsServices {
   
    
    private BlueprintsPersistence bpp;
    
    private Filter filter;
    
    public BlueprintsServices(BlueprintsPersistence bpp, Filter filter){
        this.bpp = bpp;
        this.filter = filter;
    }
    
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException{
        bpp.saveBlueprint(bp);
    }
    
    public Set<Blueprint> getAllBlueprints() throws BlueprintNotFoundException{
        return filter.filterByPrints(bpp.getAllBlueprint());
    }
    
    /**
     * 
     * @param author blueprint's author
     * @param name blueprint's name
     * @return the blueprint of the given name created by the given author
     * @throws BlueprintNotFoundException if there is no such blueprint
     */
    public Blueprint getBlueprint(String author,String name) throws BlueprintNotFoundException{
        return filter.filterByMethod(bpp.getBlueprint(author, name));
    }
    
    /**
     * 
     * @param author blueprint's author
     * @return all the blueprints of the given author
     * @throws BlueprintNotFoundException if the given author doesn't exist
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException{
        return filter.filterByPrints(bpp.getBlueprintByAuthor(author));
    }
    
    public Blueprint updateBlueprintsByAuthorAndName(String author,
            String bpname, Blueprint bpnew) throws BlueprintNotFoundException{
        return filter.filterByMethod(bpp.updateBlueprintByAuthorAndName(author, bpname, bpnew));
    }

    public Set<Blueprint> deleteBlueprintsByAuthorAndName(String author, String name) throws BlueprintNotFoundException {
        return filter.filterByPrints(bpp.deleteBlueprintByAuthorAndName(author, name));
    }
}
