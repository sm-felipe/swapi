package com.ame.swapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Planet")
public class PlanetEntity {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String climate;

    @Column
    private String terrain;

    @Column
    private Integer appearingCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Integer getAppearingCount() {
        return appearingCount;
    }

    public void setAppearingCount(Integer appearingCount) {
        this.appearingCount = appearingCount;
    }

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
