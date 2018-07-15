package com.healthfirst.memberfunction;

import org.springframework.cloud.function.adapter.aws.SpringBootRequestHandler;

public class AwsLambdaHandler extends SpringBootRequestHandler<HealthFirstMemberRequest, HealthFirstMemberResponse> {

}
