package ddd.leave.interfaces.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * @author apple
 */
@Data
public class LeaveDTO {

//    @ApiModelProperty(value = "客户端Id", example = "1", position = 1)
    String leaveId;

//    @ApiModelProperty(value = "客户端Id", example = "", position = 2)
    ApplicantDTO applicantDTO;

    @ApiModelProperty(value = "客户端Id", example = "", position = 3)
    ApproverDTO approverDTO;

    @ApiModelProperty(value = "客户端Id", example = "", position = 4)
    String leaveType;

    @ApiModelProperty(value = "客户端Id", example = "", position = 5)
    ApprovalInfoDTO currentApprovalInfoDTO;

    @ApiModelProperty(value = "客户端Id", example = "", position = 6)
    List<ApprovalInfoDTO> historyApprovalInfoDTOList;

    @ApiModelProperty(value = "客户端Id", example = "", position = 7)
    String startTime;

    @ApiModelProperty(value = "客户端Id", example = "", position = 8)
    String endTime;

    @ApiModelProperty(value = "客户端Id", example = "", position = 9)
    Long duration;

    @ApiModelProperty(value = "客户端Id", example = "", position = 10)
    String status;
}
