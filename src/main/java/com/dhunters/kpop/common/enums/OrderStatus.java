package com.dhunters.kpop.common.enums;


//주문 관련 상태 Enum 모음
public class OrderStatus {


    // 주문 상태
    public enum Status {
        PENDING,     // 주문 대기
        CONFIRMED,   // 주문 확인
        SHIPPED,     // 배송 중
        DELIVERED,   // 배송 완료
        CANCELLED    // 주문 취소
    }


     //주문 아이템 상태
    public enum ItemStatus {
        NORMAL,      // 정상
        CANCELLED,   // 취소
        RETURNED     // 반품
    }
}