package common.data;

/**
 * Enumeration with country constants.
 */
public enum Country {
    UNITED_KINGDOM,
    FRANCE,
    SOUTH_KOREA,
    JAPAN;

    /**
     * Generates a beautiful list of enum string values.
     * @return String with all enum values splitted by comma.
     */
    public static String nameList() {
        String nameList = "";
        for (Country country : values()) {
            nameList += country.name() + ", ";
        }
        return nameList.substring(0, nameList.length()-2);
    }
}
