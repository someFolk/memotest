package com.morsela.tel.db.util;

import java.util.ArrayList;

public class Table
{
    private String name;
    private ArrayList<Field> fields;

    public Table(String name, ArrayList<Field> fields)
    {
        this.name = name;
        this.fields = fields;
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<Field> getFields()
    {
        return fields;
    }
}
