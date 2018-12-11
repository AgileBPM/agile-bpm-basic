package com.dstz.base.db.model.query;


import com.dstz.base.api.query.Direction;
import com.dstz.base.api.query.FieldSort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * 字段排序。
 * <pre>
 * </pre>
 */
public class DefaultFieldSort implements FieldSort, Serializable {
    /**
     * serialVersionUID:类的序号
     *
     * @since 1.0.0
     */

    private static final long serialVersionUID = 5742164735225460363L;
    private Direction direction;
    private String field;

    public DefaultFieldSort(String field) {
        this(field, Direction.ASC);
    }

    public DefaultFieldSort(String field, Direction direction) {
        this.direction = direction;
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }


    @Override
    public String getField() {
        return field;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setField(String field) {
        this.field = field;
    }


    private static String INJECTION_REGEX = "[A-Za-z0-9\\_\\-\\+\\.]+";

    public static boolean isSQLInjection(String str) {
        return !Pattern.matches(INJECTION_REGEX, str);
    }

    @Override
    public String toString() {
        if (isSQLInjection(field)) {
            throw new IllegalArgumentException("SQLInjection property: " + field);
        }
        return field + (direction == null ? "" : " " + direction.name());
    }

    /**
     * 将sql片段转化成排序对象。
     *
     * @param orderSegment ex: "id asc,code desc"
     */
    public static List<DefaultFieldSort> formString(String orderSegment) {
        if (orderSegment == null || orderSegment.trim().equals("")) {
            return new ArrayList(0);
        }

        List<DefaultFieldSort> results = new ArrayList();
        String[] orderSegments = orderSegment.trim().split(",");
        for (int i = 0; i < orderSegments.length; i++) {
            String sortSegment = orderSegments[i];
            DefaultFieldSort order = _formString(sortSegment);
            if (order != null) {
                results.add(order);
            }
        }
        return results;
    }


    private static DefaultFieldSort _formString(String orderSegment) {

        if (orderSegment == null || orderSegment.trim().equals("")) {
            return null;
        }

        String[] array = orderSegment.trim().split("\\s+");
        if (array.length != 1 && array.length != 2) {
            throw new IllegalArgumentException("orderSegment pattern must be {property}.{direction}, input is: " + orderSegment);
        }

        return create(array[0], array.length == 2 ? array[1] : "asc");
    }


    /**
     * @param property
     * @param direction
     * @return
     */
    public static DefaultFieldSort create(String property, String direction) {
        return new DefaultFieldSort(property, Direction.fromString(direction));
    }

}
