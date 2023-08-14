package com.faening.model;

public enum DeliveryType {
    IN_PERSON(0),
    DELIVERY(1);

    private final int value;

    DeliveryType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static DeliveryType valueOf(int value) {
        for (DeliveryType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid DeliveryType value: " + value);
    }
}