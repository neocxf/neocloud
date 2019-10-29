package top.neospot.cloud.messaging.xa.entity;

import lombok.Data;
import top.neospot.cloud.common.model.BaseModel;

@Data
class TransParticipant extends BaseModel {

    /**
     *  unique identifier among all the transactions of XA
     *
     *  can be set as service's name
     */
    private String identifier;

    /**
     *  currently involving transaction
     */
    private Long xid;


}
