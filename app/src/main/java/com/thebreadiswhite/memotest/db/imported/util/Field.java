package com.morsela.tel.db.util;

public class Field
{
    private String name;
    private FieldType type;
    private String configuration = null;

    public Field(String name, FieldType type, String configuration)
    {
        this.name = name;
        this.type = type;
        this.configuration = configuration;
    }

    public Field(String name, FieldType type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public FieldType getType()
    {
        return type;
    }

    public String getTypeValue()
    {
        return type.getValue();
    }

    public String getConfiguration()
    {
        return configuration;
    }
}
