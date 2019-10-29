package top.neospot.cloud.messaging.xa.entity;


import lombok.Data;
import top.neospot.cloud.common.model.BaseModel;
import top.neospot.cloud.messaging.xa.TransState;

import java.util.List;

@Data
public class Transaction extends BaseModel {

    private String xid;

    private TransState state;

    private List<TransParticipant> participants;


}
