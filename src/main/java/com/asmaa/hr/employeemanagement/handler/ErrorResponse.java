package com.asmaa.hr.employeemanagement.handler;

import java.util.Map;

public record ErrorResponse(Map<String, String> errors) {
}
