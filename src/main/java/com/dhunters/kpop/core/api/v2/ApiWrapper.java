package com.dhunters.kpop.core.api.v2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"isSuccess", "resCode", "resMessage", "result"})
public class ApiWrapper<T> {

	@JsonProperty("isSuccess")
	private boolean isSuccess;

	private int resCode;

	private String resMessage;

	private T result;

	// HTTPS 상태 로직 보강
	public ApiWrapper(boolean isSuccess, int resCode, String resMessage, T data) {
		this.isSuccess = isSuccess;
		this.resCode = resCode;
		this.resMessage = resMessage;
		result = data;
	}

	// api request 성공 했을 때
	// 돌려줄 조회값이 있는 경우
	//      ok 호출한다.
	public static <T> ApiWrapper<T> ok(T data) {
		return new ApiWrapper<>(true, HttpStatus.OK.value(), "success", data);
	}

	// api request 성공 했을 때
	// POST 를 통해 데이터 생성 요청인 경우
	//      created 호출한다.
	public static <T> ApiWrapper<T> created(T data) {
		return new ApiWrapper<>(true, HttpStatus.CREATED.value(), "created", data);
	}

	// api request 성공 했을 때
	// 돌려줄 조회값이 없는 경우
	//      none 호출한다.
	public static ApiWrapper<?> none() {
		return new ApiWrapper<>(true, HttpStatus.NO_CONTENT.value(), "no content", null);
	}

	// api request 실패했을 때
	// http 상태코드를 받고
	// 보편적인 error message 리턴
	public static ApiWrapper<?> error(int resCode) {
		return new ApiWrapper<>(false, resCode, HttpStatus.valueOf(resCode).getReasonPhrase(), null);
	}
}