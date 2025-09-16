/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.filter.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blueprints")
/**
 *
 * @author hcadavid
 */
public class BlueprintAPIController {
    
    private BlueprintsServices bps;
    
    public BlueprintAPIController(BlueprintsServices bps){
        this.bps = bps;
    }
    
    @RequestMapping(value = "/",method = RequestMethod.POST)
    public ResponseEntity<?> createBlueprint(@RequestBody Blueprint bp){
        try {
            bps.addNewBlueprint(bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintPersistenceException e1) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e1);
            return new ResponseEntity<>("Error, Can't create new blueprint",HttpStatus.FORBIDDEN);   
        } catch (Exception e2) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e2);
            return new ResponseEntity<>("Unknown error",HttpStatus.FORBIDDEN);  
        }
    }
    
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ResponseEntity<?> getBlueprints(){
        try {
            Set<Blueprint> blueprints = bps.getAllBlueprints();
            return new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Can not get the Blueprints",HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value= "/{author}",method= RequestMethod.GET)
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable("author") String author){
        try {
            Set<Blueprint> blueprints = bps.getBlueprintsByAuthor(author);
            if (blueprints == null || blueprints.isEmpty()) {
            return new ResponseEntity<>("No blueprints found for author: " + author, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(blueprints,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Can not get the Blueprints for that author",HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value= "/{author}/{name}",method= RequestMethod.GET)
    public ResponseEntity<?> getBlueprintsByAuthorAndName(@PathVariable("author") String author, 
            @PathVariable("name") String name){
        try {
            Blueprint bp = bps.getBlueprint(author, name);
            return new ResponseEntity<>(bp,HttpStatus.ACCEPTED);
        } catch (Exception e) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e);
            return new ResponseEntity<>("Can not get the Blueprints for that author and name",HttpStatus.NOT_FOUND);
        }
    }
    
    @RequestMapping(value = "/{author}/{name}",method = RequestMethod.PUT)
    public ResponseEntity<?> updateBlueprint(@PathVariable("author") String author, 
            @PathVariable("name") String name, @RequestBody Blueprint bp){
        
        try {
            bps.updateBlueprintsByAuthorAndName(author, name, bp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (BlueprintNotFoundException e1) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e1);
            return new ResponseEntity<>("Error, Can't create new blueprint",HttpStatus.FORBIDDEN);   
        } catch (Exception e2) {
            Logger.getLogger(BlueprintAPIController.class.getName()).log(Level.SEVERE, null, e2);
            return new ResponseEntity<>("Unknown error",HttpStatus.FORBIDDEN);  
        }
    }
}

