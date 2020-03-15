package com.indracompany.refis.contracts.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.indracompany.refis.contracts.model.dto.audit.RefisTracer;
import com.indracompany.refis.contracts.model.dto.base.BaseResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/contracts")
@RestController
public class ContractApi {

    public static void main(String[] args) throws JsonProcessingException {

        RefisTracer refisTracer = new RefisTracer();
        refisTracer.setActivityName("nombreActividad");
        refisTracer.setCaseId("1234");
        refisTracer.setUser("usuario");

        BaseResponse.Status status = new BaseResponse.Status();
        status.setId(1);
        status.setDescription("Description");

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setStatus(status);

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(refisTracer));
        System.out.println(mapper.writeValueAsString(baseResponse));
    }
}
