package com.dstz.form.api.model;

public enum FormType {
    PC("pc"),
    MOBILE("mobile");

    private final String value;

    FormType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FormType fromValue(String v) {
        for (FormType c : FormType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
