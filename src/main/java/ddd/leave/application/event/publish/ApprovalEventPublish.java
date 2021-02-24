package ddd.leave.application.event.publish;

import ddd.leave.application.service.LeaveApplicationService;

import javax.annotation.Resource;

/**
 * 领域事件
 *
 * 请假审批已通过（事件发布）
 *
 * @author JavaEdge
 * @date 2021/1/24
 */
public class ApprovalEventPublish {

    /**
     * 请假审批（审核）应用服务
     */
    @Resource
    private LeaveApplicationService leaveApplicationService;
}
