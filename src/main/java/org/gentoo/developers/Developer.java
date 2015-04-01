package org.gentoo.developers;

import lombok.Data;

/**
 * Created by johu on 31.03.15.
 */
@Data
public class Developer {

    private String nick;
    private String name;
    private String joined;
    private double lat;
    private double lon;
    private String roles;
    private String loc;

}
