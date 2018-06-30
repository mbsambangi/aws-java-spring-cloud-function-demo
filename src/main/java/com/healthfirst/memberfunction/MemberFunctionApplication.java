package com.healthfirst.memberfunction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Function;

@SpringBootApplication
public class MemberFunctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberFunctionApplication.class, args);
	}

	@Bean
	public Function<HealthFirstMemberRequest, HealthFirstMemberResponse> members() {
		return member -> {
			HealthFirstMemberResponse response = new HealthFirstMemberResponse();
			response.setMemberId(member.getMemberId());
			response.setCoverage(HealthFirstMemberResponse.Coverage.MEDICAL);
		    return response;
        };
	}

}
