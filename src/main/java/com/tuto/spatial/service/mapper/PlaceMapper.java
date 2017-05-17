package com.tuto.spatial.service.mapper;

import com.tuto.spatial.domain.*;
import com.tuto.spatial.service.dto.PlaceDTO;
import com.tuto.spatial.service.dto.custom.Area;
import com.tuto.spatial.service.dto.custom.Point;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import java.util.ArrayList;
import java.util.List;

import org.mapstruct.*;

/**
 * Mapper for the entity Place and its DTO PlaceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PlaceMapper extends EntityMapper <PlaceDTO, Place> {
    
    
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default Place fromId(Long id) {
        if (id == null) {
            return null;
        }
        Place place = new Place();
        place.setId(id);
        return place;
    }
    
    default Area areaToGeometry(Geometry geometry) {
        if(geometry == null) {
            return null;
        }
        List<Point> points = new ArrayList<>();
        for(Coordinate c : geometry.getCoordinates()) {
            points.add(new Point(c.x, c.y));
        }
        return new Area(points);
    }
    
    default Geometry geometryToArea(Area area) {
        if(area == null) {
            return null;
        }
        GeometryFactory factory = new GeometryFactory();
        Coordinate[] coords = new Coordinate[area.getPoints().size()];
        int i = 0;
        for(Point p : area.getPoints()) {
            coords[i] = new Coordinate(p.getLat(), p.getLng());
            i++;
        }
        return factory.createPolygon(coords);
    }
}
