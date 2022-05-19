package common.interaction;

import common.data.StudyGroup;

import java.io.Serializable;
import java.util.NavigableSet;

/**
 * Class for get response value.
 */
public class Response implements Serializable {
    NavigableSet<StudyGroup> studyGroupCollection;
    private ResponseCode responseCode;
    private String responseBody;
    private String[] responseBodyArgs;

    public Response(ResponseCode responseCode, String responseBody, String[] responseBodyArgs,
                    NavigableSet<StudyGroup> studyGroupCollection) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
        this.studyGroupCollection = studyGroupCollection;
        this.responseBodyArgs = responseBodyArgs;
    }

    /**
     * @return Response ñode.
     */
    public ResponseCode getResponseCode() {
        return responseCode;
    }

    /**
     * @return Response body.
     */
    public String getResponseBody() {
        return responseBody;
    }

    public String[] getResponseBodyArgs() {
        return responseBodyArgs;
    }

    /**
     * @return Marines collection last save.
     */
    public NavigableSet<StudyGroup> getStudyGroupCollection() {
        return studyGroupCollection;
    }

    @Override
    public String toString() {
        return "Response[" + responseCode + ", " + responseBody + "]";
    }
}