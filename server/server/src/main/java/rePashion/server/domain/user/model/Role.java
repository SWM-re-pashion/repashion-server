package rePashion.server.domain.user.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Role {

    ROLE_USER, ROLE_ADMIN;

    private static final Map<String,Role> lookup = new HashMap<>();

    static {
        Arrays.stream(Role.values()).forEach((o)->lookup.put(o.name(), o));
    }

    public static Role get(String role) {
        return lookup.get(role);
    }

    public static boolean containsKey(String role) {
        return lookup.containsKey(role);
    }
}
