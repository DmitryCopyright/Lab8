package common.data;

/**
 * Enumeration with hair color constants.
 */
public enum Color {
    LACK,
    ORANGE,
    WHITE,
    BROWN;

    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        String nameList = "";
        for (Color category : values()) {
            nameList += category.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
