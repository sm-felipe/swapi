package com.ame.swapi.model.dto;

public class PlanetDTO {

    private String name;

    private String climate;

    private String terrain;

    private Integer appearingCount;

    public PlanetDTO() {
    }

    public PlanetDTO(String name, String climate, String terrain, Integer appearingCount) {
        this.name = name;
        this.climate = climate;
        this.terrain = terrain;
        this.appearingCount = appearingCount;
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

        PlanetDTO planetDTO = (PlanetDTO) o;

        if (name != null ? !name.equals(planetDTO.name) : planetDTO.name != null) return false;
        if (climate != null ? !climate.equals(planetDTO.climate) : planetDTO.climate != null) return false;
        if (terrain != null ? !terrain.equals(planetDTO.terrain) : planetDTO.terrain != null) return false;
        return appearingCount != null ? appearingCount.equals(planetDTO.appearingCount) : planetDTO.appearingCount == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (climate != null ? climate.hashCode() : 0);
        result = 31 * result + (terrain != null ? terrain.hashCode() : 0);
        result = 31 * result + (appearingCount != null ? appearingCount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder()//
                .append("PlanetDTO [")//
                .append("name=\"")//
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
