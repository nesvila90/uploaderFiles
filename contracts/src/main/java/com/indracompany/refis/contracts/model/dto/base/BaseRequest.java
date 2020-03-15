package com.indracompany.refis.contracts.model.dto.base;

import com.indracompany.refis.contracts.model.dto.audit.RefisTracer;
import lombok.Data;

@Data
public class BaseRequest {

    private RefisTracer auditoria;

}
