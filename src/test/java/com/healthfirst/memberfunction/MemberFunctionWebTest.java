package com.healthfirst.memberfunction;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.cloud.contract.wiremock.restdocs.SpringCloudContractRestDocs;
import org.springframework.cloud.contract.wiremock.restdocs.WireMockRestDocs;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
@AutoConfigureJsonTesters
public class MemberFunctionWebTest {

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<HealthFirstMemberRequest> json;

    @Before
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void provideCoverageForGivenMemberId() throws Exception {
        HealthFirstMemberRequest member = new HealthFirstMemberRequest();
        member.setMemberId("1234567890");

        MvcResult result = mockMvc.perform(post("/members")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(json.write(member).getJson())
        ).andReturn();

        mockMvc.perform(asyncDispatch(result))
        .andExpect(status().isOk())
        .andExpect(jsonPath("coverage").value("MEDICAL"))
        .andDo(WireMockRestDocs.verify().jsonPath("$.memberId")
        .contentType(MediaType.APPLICATION_JSON_UTF8).stub("healthfirst-member-check"))
        .andDo(MockMvcRestDocumentation.document("healthfirst-member-check",
                SpringCloudContractRestDocs.dslContract()));
    }
}
