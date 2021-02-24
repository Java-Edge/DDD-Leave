package ddd.leave.domain.leave.entity;

import ddd.leave.domain.leave.entity.valueobject.ApprovalType;
import ddd.leave.domain.leave.entity.valueobject.Approver;
import lombok.Data;

/**
 * 实体
 *
 * 审批意见
 *
 * @author apple
 */
@Data
public class ApprovalInfo {

    String approvalInfoId;

    /**
     * 审批人
     */
    Approver approver;

    /**
     * 请假类型
     */
    ApprovalType approvalType;

    /**
     *
     */
    String msg;

    /**
     * 请假时长
     */
    long time;

    // 创建、查询审批意见
}
