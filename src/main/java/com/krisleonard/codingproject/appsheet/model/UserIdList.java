package com.krisleonard.codingproject.appsheet.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

/**
 * The User Id List object
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserIdList {

    /** The list of user ids */
    private List<Integer> result = null;

    /** The paging token */
    private String token = null;

    /**
     * Get the user id result list
     *
     * @return The user id result list
     */
    public List<Integer> getResult() {
        return result;
    }

    /**
     * Set the user id result list
     *
     * @param result The user id result list
     */
    public void setResult(List<Integer> result) {
        this.result = result;
    }

    /**
     * Get the paging token
     *
     * @return The paging token
     */
    public String getToken() {
        return token;
    }

    /**
     * Set the paging token
     *
     * @param token The paging token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserIdList userIdList = (UserIdList) o;
        return Objects.equals(this.result, userIdList.result) &&
                Objects.equals(this.token, userIdList.token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(result, token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UserIdList {\n");

        sb.append("    result: ").append(toIndentedString(result)).append("\n");
        sb.append("    token: ").append(toIndentedString(token)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}


