package org.gentoo.developers;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by johu on 31.03.15.
 */
@Data
@Document
public class Developer {

    @Id
    private String nick;
    private String name;
    private String joined;
    private double latitude;
    private double longitude;
    @GeoSpatialIndexed
    private Point location;
    private String roles;
    private String place;
}
