package io.github.michael_bailey.client.models;

import java.security.Key;
import java.util.UUID;

public class Account {
    public String displayName;
    public UUID uuid;
    public Key publicKey;
    public Key privateKey;
}
