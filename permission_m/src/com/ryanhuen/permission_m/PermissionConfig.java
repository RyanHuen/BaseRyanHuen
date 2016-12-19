
package com.ryanhuen.permission_m;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ryanhuenwork on 16-10-13.
 */
public class PermissionConfig {
    /**
     * configure your permission here
     */
    private static final Set<String> PERMISSION_LIST = new HashSet<>();

    public static Set<String> getPermissionList() {
        return PERMISSION_LIST;
    }

    public static void removeFromPermissionList(String permissionString) {
        PERMISSION_LIST.remove(permissionString);
    }

    public static void addToPermissionList(String permissionString) {
        PERMISSION_LIST.add(permissionString);
    }

    public static void addToPermissionList(String[] permissionArray) {
        PERMISSION_LIST.addAll(Arrays.asList(permissionArray));
    }

    public static void addToPermissionList(Collection<? extends String> permissionList) {
        PERMISSION_LIST.addAll(permissionList);
    }

    public static void clearAllPermission() {
        PERMISSION_LIST.clear();
    }


}
