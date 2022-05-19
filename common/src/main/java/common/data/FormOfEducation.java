package common.data;

/**
 * Enumeration with form_of_education constants.
 */
public enum FormOfEducation {
    DISTANCE_EDUCATION,
    FULL_TIME_EDUCATION,
    EVENING_CLASSES;

    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        String nameList = "";
        for (FormOfEducation formOfEducationType : values()) {
            nameList += formOfEducationType.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
