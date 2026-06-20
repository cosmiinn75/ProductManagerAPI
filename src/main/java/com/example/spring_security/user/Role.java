package com.example.spring_security.user;

import java.util.List;

public enum Role {
    CUSTOMER(List.of(Permission.READ_ALL_PRODUCTS)),
    ADMIN(List.of(
            Permission.READ_ALL_PRODUCTS,
            Permission.SAVE_ONE_PRODUCT,
            Permission.UPDATE_ONE_PRODUCT,
            Permission.DELETE_ONE_PRODUCT
    ));

    private final List<Permission> permissions;

    Role(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }
}