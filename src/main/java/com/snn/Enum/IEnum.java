package com.snn.Enum;

public interface IEnum<V extends Object>
{
    public String name();

    public int ordinal();

    public V getValue();

    public String getDisplayText();
}
