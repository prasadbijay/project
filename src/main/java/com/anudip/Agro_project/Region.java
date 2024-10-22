package com.anudip.Agro_project;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_name", nullable = false)
    private String regionName;

    @OneToMany(mappedBy = "region")
    private Set<User> users;

    @OneToMany(mappedBy = "region")
    private Set<WeatherData> weatherData;

    @OneToMany(mappedBy = "region")
    private Set<Crop> crops;

    @OneToMany(mappedBy = "region")
    private Set<Recommendation> recommendations;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<WeatherData> getWeatherData() {
        return weatherData;
    }

    public void setWeatherData(Set<WeatherData> weatherData) {
        this.weatherData = weatherData;
    }

    public Set<Crop> getCrops() {
        return crops;
    }

    public void setCrops(Set<Crop> crops) {
        this.crops = crops;
    }

    public Set<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }
}