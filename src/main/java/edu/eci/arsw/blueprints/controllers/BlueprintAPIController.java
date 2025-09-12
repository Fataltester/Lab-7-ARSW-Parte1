/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.controllers;

import edu.eci.arsw.blueprints.filter.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.services.BlueprintsServices;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author hcadavid
 */
@RestController
@RequestMapping("/blueprints")
public class BlueprintAPIController {
    
    @Autowired
    private BlueprintsServices bps;
    
    
    /**
     * Constructor de BluePrintController.
     * @param bps Servicio de blueprints inyectado.
     * @param filter
     */

    
    @PostMapping("/")
    public ResponseEntity<?> createBlueprint(@RequestBody Blueprint bp){
        HashMap<String, Object> response = new HashMap<>();
        try {
            bps.addNewBlueprint(bp);
            response.put("status", "success");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
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
            return new ResponseEntity<>("Can not get the Blueprints for that author",HttpStatus.NOT_FOUND);
        }
    }
    
    
    
    
    
}

