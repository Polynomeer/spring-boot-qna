package com.codesquad.qna.util;

import com.codesquad.qna.domain.User;
import com.codesquad.qna.exception.UnauthorizedUserAccessException;

import javax.servlet.http.HttpSession;

public class HttpSessionUtils {
    public static final String USER_SESSION_KEY = "sessionedUser";

    public static boolean isLoginUser(HttpSession session) {
        return session.getAttribute(USER_SESSION_KEY) != null;
    }

    public static User getUserFromSession(HttpSession session) {
        if (!isLoginUser(session)) {
            throw new UnauthorizedUserAccessException();
        }
        return (User) session.getAttribute(USER_SESSION_KEY);
    }

}
