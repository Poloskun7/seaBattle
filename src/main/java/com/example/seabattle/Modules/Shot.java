package com.example.seabattle.Modules;

import java.util.Objects;

public class Shot {
    public int x, y;
    public Shot() {

    }
    public Shot(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shot shot = (Shot) o;
        return x == shot.x &&
                y == shot.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
