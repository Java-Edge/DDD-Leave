package ddd.leave.interfaces.facade;

import ddd.leave.application.service.LeaveApplicationService;
import ddd.leave.domain.leave.entity.Leave;
import ddd.leave.domain.person.entity.Person;
import ddd.leave.infrastructure.common.api.Response;
import ddd.leave.interfaces.assembler.LeaveAssembler;
import ddd.leave.interfaces.dto.LeaveDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/leave")
@Slf4j
//@Api("Leave Controller")
/**
 * 根据考勤规则，核销请假数据后，对考勤数据进行校验，输出考勤统计。
 */
public class LeaveApi {

    @Resource
    LeaveApplicationService leaveApplicationService;

    // 创建请假信息【命令】
    @PostMapping("/create")
    public Response createLeaveInfo(LeaveDTO leaveDTO){
        Leave leave = LeaveAssembler.toDO(leaveDTO);
        leaveApplicationService.createLeaveInfo(leave);
        return Response.ok();
    }

    @PutMapping("/update")
    public Response updateLeaveInfo(LeaveDTO leaveDTO){
        Leave leave = LeaveAssembler.toDO(leaveDTO);
        leaveApplicationService.updateLeaveInfo(leave);
        return Response.ok();
    }

    /**
     * {
     * "leaveId": "1",
     * "applicantDTO": {
     * "personId": "1",
     * "personName": "1",
     * "leaderId": "1",
     * "applicantType": "1",
     * "roleLevel": "1"
     * },
     * "approverDTO": {
     * "personId": "",
     * "personName": "S"
     * },
     * "leaveType": "",
     * "currentApprovalInfoDTO": {
     * "approvalInfoId": "",
     * "approverDTO": {},
     * "msg": "",
     * "time": ""
     * },
     * "historyApprovalInfoDTOList": [
     * {}
     * ],
     * "startTime": "",
     * "endTime": "",
     * "duration": "",
     * "status": ""
     * }
     * 提交审批 接口
     *
     * 请假人填写请假单提交审批
     * 根据请假人身份、请假类型和请假天数进行校
     * 根据审批规则逐级递交上级审批
     * 逐级核批通过则完成审批
     * 否则审批不通过退回申请人
     * @param leaveDTO
     * @return
     */
    @ApiOperation(value = "提交请假单")
    @PostMapping("/submit")
    public Response submitApproval(@RequestBody LeaveDTO leaveDTO){
        // DTO 转 DO
        Leave leave = LeaveAssembler.toDO(leaveDTO);
        // 定位到应用服务
        leaveApplicationService.submitApproval(leave);
        return Response.ok();
    }

    @PostMapping("/{leaveId}")
    public Response findById(@PathVariable String leaveId){
        Leave leave = leaveApplicationService.getLeaveInfo(leaveId);
        return Response.ok(LeaveAssembler.toDTO(leave));
    }

    /**
     * 根据申请人查询所有请假单
     * @param applicantId
     * @return
     */
    @PostMapping("/query/applicant/{applicantId}")
    public Response queryByApplicant(@PathVariable String applicantId){
        List<Leave> leaveList = leaveApplicationService.queryLeaveInfosByApplicant(applicantId);
        List<LeaveDTO> leaveDTOList = leaveList.stream().map(leave -> LeaveAssembler.toDTO(leave)).collect(Collectors.toList());
        return Response.ok(leaveDTOList);
    }

    /**
     * 根据审批人id查询待审批请假单（待办任务）
     * @param approverId
     * @return
     */
    @PostMapping("/query/approver/{approverId}")
    public Response queryByApprover(@PathVariable String approverId){
        List<Leave> leaveList = leaveApplicationService.queryLeaveInfosByApprover(approverId);
        List<LeaveDTO> leaveDTOList = leaveList.stream().map(leave -> LeaveAssembler.toDTO(leave)).collect(Collectors.toList());
        return Response.ok(leaveDTOList);
    }
}
