package com.example.dylanyung.propellerhealth.objects;

/**
 * Created by Dylan Yung on 3/6/2018.
 */

public enum MedicationType {
    rescue, controller, undefined;
    private static String[] controllerArray = {"symbicort"};
    private static String[] rescueArray = {"proair", "albuterol"};

    public static MedicationType getType(String s) {
        for (String str : controllerArray) {
            if (s.equals(str)) {
                return controller;
            }
        }

        for (String str : rescueArray) {
            if (s.equals(str)) {
                return rescue;
            }
        }

        return undefined;
    }
}
