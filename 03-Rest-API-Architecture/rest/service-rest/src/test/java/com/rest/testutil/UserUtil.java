package com.rest.testutil;

import java.time.LocalDate;

import com.rest.dto.dto.UserRequestDto;
import com.rest.dto.dto.UserResponseDto;
import com.rest.dto.model.User;

public class UserUtil {

    public static final Long ID = 1L;
    public static final String NAME = "name";
    public static final String USER_NAME = "username";
    public static final LocalDate BIRTH_DATE = LocalDate.now();
    public static final String BIRTH_DATE_STRING = String.valueOf(LocalDate.now());


    public static User user() {
        return new User(ID, NAME, USER_NAME, BIRTH_DATE);
    }

    public static UserResponseDto userResponseDto() {
        return new UserResponseDto(ID, NAME, USER_NAME, BIRTH_DATE_STRING);
    }

    public static UserRequestDto userRequestDto() {
        return new UserRequestDto(ID, NAME, USER_NAME, BIRTH_DATE_STRING);
    }
}
