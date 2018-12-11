package com.dstz.form.api.model;

public enum FormCategory {
    INNER("inner"),
    FRAME("frame");

    private final String value;

    FormCategory(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormCategory fromValue(String v) {
        for (FormCategory c : FormCategory.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
