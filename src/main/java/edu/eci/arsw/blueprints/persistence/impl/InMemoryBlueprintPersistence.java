/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();
    

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Point[] firstPts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Point[] secondPts=new Point[]{new Point(200, 300),new Point(400, 100)};
        Point[] thirdPts=new Point[]{new Point(200, 5),new Point(128, 86)};
        Blueprint firtsBp=new Blueprint("juan", "bp1 ",firstPts);
        Blueprint secondBp=new Blueprint("santiago", "bp2 ",secondPts);
        Blueprint thirdBp=new Blueprint("santiago", "bp3",thirdPts);
        Blueprint bp=new Blueprint("_authorname_", "_bpname_ ",pts);
        blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        blueprints.put(new Tuple<>(firtsBp.getAuthor(),firtsBp.getName()), firtsBp);
        blueprints.put(new Tuple<>(secondBp.getAuthor(),secondBp.getName()), secondBp);
        blueprints.put(new Tuple<>(thirdBp.getAuthor(),thirdBp.getName()), thirdBp);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        }        
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException{
        Set<Blueprint> result = new HashSet<>();

        for (Map.Entry<Tuple<String,String>, Blueprint> entry : blueprints.entrySet()) {
            if (entry.getKey().getElem1().equals(author)) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    @Override
    public Set<Blueprint> getAllBlueprint() throws BlueprintNotFoundException {
        Set<Blueprint> blueprintSet = new HashSet<>();

        for (Tuple<String, String> i : blueprints.keySet()){
            blueprintSet.add(blueprints.get(i));
        }

        return blueprintSet;
    }

    @Override
    public Blueprint updateBlueprintByAuthorAndName(String author, String name,
            Blueprint bpnew) throws BlueprintNotFoundException {
        Tuple<String, String> key = new Tuple<>(author, name);
        if (blueprints.replace(key, bpnew) == null) {
            throw new BlueprintNotFoundException("Blueprint not found: " + author + " - " + name);
        }else{
            blueprints.replace(key, bpnew);
            return bpnew;
        }
    }
}
