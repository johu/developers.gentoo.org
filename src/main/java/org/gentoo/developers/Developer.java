package org.gentoo.developers;

import lombok.Data;
import org.springframework.data.annotation.Id;
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
    private double lat;
    private double lon;
    private String roles;
    private String loc;

}
