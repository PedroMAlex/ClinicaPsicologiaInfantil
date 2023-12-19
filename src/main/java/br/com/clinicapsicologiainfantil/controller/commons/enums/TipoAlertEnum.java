package br.com.clinicapsicologiainfantil.controller.commons.enums;

public enum TipoAlertEnum {
    INFO(0), WARN(1), ERROR(2);

    private int value;

    TipoAlertEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}