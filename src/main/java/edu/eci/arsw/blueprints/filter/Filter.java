/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arsw.blueprints.filter;

import edu.eci.arsw.blueprints.model.Blueprint;
import java.util.Set;

/**
 *
 * @author Santiago
 */
public interface Filter {
    
    public  Blueprint filterByMethod(Blueprint print);
    
    public Set<Blueprint> filterByPrints(Set<Blueprint> blueprinters);
}
