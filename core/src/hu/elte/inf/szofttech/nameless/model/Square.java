package hu.elte.inf.szofttech.nameless.model;

import hu.elte.inf.szofttech.nameless.model.tower.Tower;

import java.util.Optional;

public final class Square {
    private Optional<Tower> tower;

    public Square() {
        this.tower = Optional.empty();
    }

    public Optional<Tower> getTower() {
        return this.tower;
    }

    public void buildTower(Tower tower) {
        this.tower = Optional.of(tower);
    }

    public void destroyTower() {
        this.tower = Optional.empty();
    }
}
