package com.dhunters.kpop.auth;
//JWT 임시설정용

public interface  CurrentUserProvider {
    Long getMemberId(); // null이면 미인증
}
