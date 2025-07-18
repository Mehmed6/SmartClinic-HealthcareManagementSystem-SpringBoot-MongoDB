package com.doganmehmet.app.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@AllArgsConstructor
@Accessors(prefix = "m_")
public enum MyError {

    ADMIN_ROLE_NOT_ALLOWED("9999", "Admin role is not allowed for this action!"),
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
    INVALID_LEAVE_STATUS("1012", "Invalid Leave Status: %s"),
    APPOINTMENT_NOT_FOUND("1013", "Appointment not found!"),
    INVALID_APPOINTMENT_STATUS("1014", "Invalid Appointment Status: %s"),
    APPOINTMENT_ALREADY_COMPLETED_OR_CANCELLED("1015", "Appointment already %s"),
    TEST_TYPE_NOT_FOUND("1016", "Test Type not found!"),
    TEST_TYPE_ALREADY_EXISTS("1017", "Test Type already exists!"),
    LABORATORY_TEST_NOT_FOUND("1018", "Laboratory Test not found!"),
    INVALID_TEST_STATUS("1019", "Invalid Test Status: %s"),
    LABORATORY_TEST_RESULT_NOT_FOUND("1020", "Laboratory Test Result not found!"),
    WORK_SCHEDULE_ALREADY_EXISTS("1021", "Work Schedule already exists for this doctor!"),
    WORK_SCHEDULE_NOT_FOUND("1022", "Work Schedule not found!"),
    INVOICE_NOT_FOUND("1023", "Invoice not found!"),
    INVALID_INVOICE_STATUS("1024", "Invalid Invoice Status: %s"),
    INVOICE_ALREADY_EXISTS("1025", "Invoice already exists for this appointment!"),
    MAIL_SEND_FAILED("1026", "Failed to send email notification!"),
    SMS_SEND_FAILED("1027", "Failed to send SMS notification!"),
    INVALID_NOTIFICATION_TYPE("1028", "Invalid notification type!"),
    NOTIFICATION_SEND_FAILED("1029", "Failed to send notification!"),
    INVALID_NOTIFICATION_TIME("1030", "Notification time cannot be in the past!"),
    NOTIFICATION_NOT_FOUND("1031", "Notification not found!"),
    INVALID_NOTIFICATION_STATUS("1032", "Invalid Notification Status: %s"),
    ;
    private final String m_errorCode;
    private final String m_errorMessage;
}
