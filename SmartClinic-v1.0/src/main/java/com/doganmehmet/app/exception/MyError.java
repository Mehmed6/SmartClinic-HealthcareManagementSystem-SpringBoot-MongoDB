package com.doganmehmet.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(prefix = "m_")
public enum MyError {

    UNAUTHORIZED_ACTION("1000", "Unauthorized action!"),
    USER_NOT_FOUND("1001", "User not found!"),
    USER_ALREADY_EXISTS("1002", "User is already in use!"),
    PASSWORD_INCORRECT("1003", "Password incorrect!"),
    EMAIL_ALREADY_EXISTS("1004", "Email is already in use!"),
    ROLE_NOT_FOUND("1005", "Role not found: %s"),
    REFRESH_TOKEN_NOT_FOUND("1006", "Refresh Token not found!"),
    REFRESH_TOKEN_EXPIRED("1007", "Refresh Token expired!"),
    USER_PROFILE_NOT_FOUND("1008", "User Profile not found!"),
    DOCTOR_NOT_FOUND("1009", "Doctor not found!"),
    PATIENT_NOT_FOUND("1010", "Patient not found!"),
    LEAVE_REQUEST_NOT_FOUND("1011", "Leave Request not found!"),
    INVALID_LEAVE_STATUS("1012", "Invalid Leave Status!"),
    APPOINTMENT_NOT_FOUND("1013", "Appointment not found!"),
    INVALID_APPOINTMENT_STATUS("1014", "Invalid Appointment Status!"),
    APPOINTMENT_ALREADY_COMPLETED_OR_CANCELLED("1015", "Appointment already Completed or Cancelled!"),
    ;
    private final String m_errorCode;
    private final String m_errorMessage;
}
