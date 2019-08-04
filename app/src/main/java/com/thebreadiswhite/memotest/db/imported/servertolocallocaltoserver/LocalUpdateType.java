package com.morsela.tel.db.servertolocallocaltoserver;

public enum LocalUpdateType
{
    ADDED(1),
    UPDATE(2),
    DELETE(3);

    public int value;

    LocalUpdateType(int value)
    {
        this.value = value;
    }

    public static LocalUpdateType getTypeByValue(int value)
    {
        LocalUpdateType typeConstant = LocalUpdateType.UPDATE;

        for(LocalUpdateType type : LocalUpdateType.values())
        {
            if(type.value == value)
            {
                typeConstant = type;
                break;
            }
        }
        return typeConstant;
    }
}
