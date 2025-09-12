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
    
    @Autowired
    private final BlueprintsServices bps;
    @Autowired
    private final Filter filter;
    
    /**
     * Constructor de BluePrintController.
     * @param bps Servicio de blueprints inyectado.
     * @param filter
     */
    public BlueprintAPIController(BlueprintsServices bps, Filter filter) {
        this.bps = bps;
        this.filter = filter;
    }
    
    @RequestMapping(method = RequestMethod.POST)
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
    
    @GetMapping("/")
    public ResponseEntity<?> getBlueprints(){
        try {
            Set<Blueprint> blueprints = filter.filterByPrints(bps.getAllBlueprints());
            return ResponseEntity.ok(blueprints);
        } catch (BlueprintNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There are no blueprints");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
    
    @GetMapping("/{author}")
    public ResponseEntity<?> getBlueprintsByAuthor(@PathVariable("author") String author){
        try {
            Set<Blueprint> blueprints = filter.filterByPrints(bps.getBlueprintsByAuthor(author));
            return ResponseEntity.ok(blueprints);
        } catch (BlueprintNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blueprints not found for author: " + author);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
    
    @GetMapping("/{author}/{name}")
    public ResponseEntity<?> getBlueprintsByAuthorAndName(@PathVariable("author") String author, 
            @PathVariable("name") String name){
        try {
            Blueprint bp = filter.filterByMethod(bps.getBlueprint(author, name));
            return ResponseEntity.ok(bp);
        } catch (BlueprintNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Blueprints not found for author: " + author);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
        }
    }
}

