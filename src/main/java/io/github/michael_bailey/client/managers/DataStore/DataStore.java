package io.github.michael_bailey.client.managers.DataStore;

import java.io.Serializable;

@Deprecated
public class DataStore implements Serializable {

    private static final long serialVersionUID = -5813387299140430418L;

    public String dataString;
    public String checkSum;
    public String salt;

}