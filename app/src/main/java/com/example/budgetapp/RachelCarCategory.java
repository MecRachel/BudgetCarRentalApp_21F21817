package com.example.budgetapp;

import java.util.List;

public class RachelCarCategory {
    private String rachelcategoryName;
    private List<RachelCar> cars21F21817;

    public RachelCarCategory(String categoryName, List<RachelCar> cars) {
        this.rachelcategoryName = categoryName;
        this.cars21F21817 = cars;
    }

    public String getCategoryName() {
        return rachelcategoryName;
    }

    public void setCategoryName(String categoryName) {
        this.rachelcategoryName = categoryName;
    }

    public List<RachelCar> getCars() {
        return cars21F21817;
    }

    public void setCars(List<RachelCar> cars) {
        this.cars21F21817 = cars;
    }
}
