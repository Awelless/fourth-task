package com.epam.task.fourth.entity;

public enum TarifficationType {
    QUARTER_MINUTE("15 seconds"),
    MINUTE("minute");

    private final String text;

    TarifficationType(String text) {
        this.text = text;
    }

    public static TarifficationType getByString(String type) {
        for (TarifficationType tarifficationType : TarifficationType.values()) {
            if (tarifficationType.text.equals(type)) {
                return tarifficationType;
            }
        }

        throw new EnumConstantNotPresentException(TarifficationType.class, "Invalid type");
    }

    public String getText() {
        return text;
    }
}
