package com.dstz.base.api.request;

/**
 * @param data<E> 假如参数为某个bean，则使用该对象，比如传递一个订单对象。
 * @说明 用于系统交互请求参数使用
 */
public class RequestDto<E> {
    private RequestHead head;
    /**
     * 参数为某个bean时使用
     */
    private E data;


    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}
