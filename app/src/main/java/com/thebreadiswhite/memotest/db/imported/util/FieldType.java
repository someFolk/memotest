package com.morsela.tel.db.util;

public enum FieldType
{
    TEXT("TEXT"),
    INT("INTEGER"),
    BOOL("BOOLEAN");

    private String value;

    FieldType(String value)
    {
        this.value = value;
    }

    public String getValue()
    {
        return value;
    }
}
