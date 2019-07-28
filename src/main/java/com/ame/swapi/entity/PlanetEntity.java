package com.ame.swapi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PlanetEntity {

    @Id
    private Long id;

    @Column
    private String name;

    @Column
    private String climate;

    @Column
    private String terrain;

    @Column
    private Integer appearingCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlanetEntity that = (PlanetEntity) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder()//
                .append("PlanetEntity [")//
                .append("id=")//
                .append(id)//
                .append(",name=\"")//
                .append(name + "\"")//
                .append(",climate=\"")//
                .append(climate + "\"")//
                .append(",terrain=\"")//
                .append(terrain + "\"")//
                .append(",appearingCount=")//
                .append(appearingCount)//
                .append("]");
        return builder.toString();
    }
}
