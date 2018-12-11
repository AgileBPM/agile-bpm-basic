package com.dstz.form.api.service;


/**
 * 表单权限接口
 */
public interface BpmFormRightsService {
    /**
     * 获取表单权限
     * <pre>
     * {
     * 	field：{"NAME": "w", "SEX": "r"}
     * 	table：{"TABLE1": "r", "TABLE2": "w"}
     * 	opinion：{"领导意见": "w", "部门意见": "r"}
     * }
     * </pre>
     *
     * @param formKey 表单KEY 对应BPM_FROM key字段。
     * @param flowKey 流程KEY
     * @param nodeId  节点ID
     * @return
     */
    String getPermission(String flowKey, String nodeId);

    /**
     * 获取流程实例表单的权限。
     * <pre>
     * {
     * 	field：{"NAME": "w", "SEX": "r"}
     * 	table：{"TABLE1": "r", "TABLE2": "w"}
     * 	opinion：{"领导意见": "w", "部门意见": "r"}
     * }
     * </pre>
     *
     * @param formKey 表单KEY 对应BPM_FROM key字段。
     * @param flowKey
     * @return
     */
    String getInstPermission(String formKey, String flowKey);

}
