package com.takoy3466.manaitapp.core;

import javax.annotation.Nullable;
import java.util.Objects;

public class ManaitaTier {
    private final int multiple;
    private final String name;

    public static final ManaitaTier DEFAULT = ManaitaTier.create(1, "noTitle");

    private ManaitaTier(int multiple, @Nullable String name) {
        this.multiple = multiple;
        this.name = name;
    }

    public static ManaitaTier create(int multiple, @Nullable String name) {
        return new ManaitaTier(multiple, name);
    }

    public int getMultiple() {
        return multiple;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManaitaTier manaitaTier)) return false;
        return getMultiple() == manaitaTier.getMultiple() && Objects.equals(getName(), manaitaTier.getName());
    }
}