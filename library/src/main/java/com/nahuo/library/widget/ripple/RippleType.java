package com.nahuo.library.widget.ripple;

/**
 * Created by JorsonWong on 2015/7/13.
 */
public enum RippleType {
    SIMPLE(0),
    DOUBLE(1),
    RECTANGLE(2);

    int type;

    RippleType(int type) {
        this.type = type;
    }
}