/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.eci.arsw.blueprints.filter.impl;

import edu.eci.arsw.blueprints.filter.Filter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Santiago
 */
public class RedundanceFilter implements Filter{

    
    @Override
    public Blueprint filterByMethod(Blueprint bp) {
        List<Point> originalPoints = bp.getPoints();
        List<Point> filteredPoints = new ArrayList<>();
        if (!originalPoints.isEmpty() || !(originalPoints.size() == 1)) {
            Point prev = originalPoints.get(0);
            filteredPoints.add(prev);
            for (int i = 1; i < originalPoints.size(); i++) {
                Point current = originalPoints.get(i);
                if (!(current.getX() == prev.getX() && current.getY() == prev.getY())) {
                    filteredPoints.add(current);
                }
                prev = current;
            }
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), 
                filteredPoints.toArray(new Point[0]));
    }

    @Override
    public Set<Blueprint> filterByPrints(Set<Blueprint> blueprinters) {
        Set<Blueprint> newBluePrint = new HashSet<>();
        for(Blueprint i: blueprinters){
            newBluePrint.add(filterByMethod(i));
        }
        return newBluePrint;
        
    }
}
