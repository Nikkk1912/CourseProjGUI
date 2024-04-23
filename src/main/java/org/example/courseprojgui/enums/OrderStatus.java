package org.example.courseprojgui.enums;

public enum OrderStatus {
    Received, Processing, Payment_received, shipping, delivered;

    public OrderStatus getNextStatus() {
        return ordinal() < values().length - 1 ? values()[ordinal() + 1] : null;
    }
}
