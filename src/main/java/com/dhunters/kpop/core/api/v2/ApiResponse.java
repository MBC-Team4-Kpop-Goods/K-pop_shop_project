package com.dhunters.kpop.core.api.v2;

import org.springframework.http.ResponseEntity;

public final class ApiResponse {
	/**
	 * api request 성공 했을 때
	 * 돌려줄 조회값이 있는 경우
	 * ok 호출한다.
	 *
	 * @param body
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseEntity<ApiWrapper<T>> ok(T body) {
		return ResponseEntity.ok(ApiWrapper.ok(body));
	}

	/**
	 * api request 성공 했을 때
	 * POST 를 통해 데이터 생성 요청인 경우
	 * created 호출한다.
	 *
	 * @param body
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseEntity<ApiWrapper<T>> created(T body) {
		return ResponseEntity.status(201).body(ApiWrapper.created(body));
	}

	/**
	 * api request 성공 했을 때
	 * 돌려줄 조회값이 없는 경우
	 * noContent 호출한다.
	 *
	 * ex : Delete Row
	 *
	 * @return
	 */
	public static ResponseEntity<ApiWrapper<?>> noContent() {
		return ResponseEntity.status(204).body(ApiWrapper.none());
	}

	/**
	 * api request 실패 케이스
	 *
	 * @return
	 */
	public static ResponseEntity<ApiWrapper<?>> error(int resCode) {
		return ResponseEntity.status(resCode).body(ApiWrapper.error(resCode));
	}
}
