package com.camunda.example;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.decisionService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;

@Slf4j
@Deployment(resources = "decideOnProcessIDs.dmn")
public class decisionTest extends AbstractProcessEngineRuleTest {

  @Test
  public void shouldReturnCompleteListOfThree() {

    DmnDecisionTableResult results = decisionService()
        .evaluateDecisionTableByKey("decideOnProcessIDs", withVariables(
            "startParameterList", "['A', 'B', 'C']"));
    assertThat(results).hasSize(3);
    log.info("Results: {}",results);
  }

  @Test
  public void shouldReturnOnlyAAndC() {

    DmnDecisionTableResult results = decisionService()
        .evaluateDecisionTableByKey("decideOnProcessIDs", withVariables(
            "startParameterList", "\"A\", \"C\""));
    assertThat(results).hasSize(2);
    log.info("Results: {}",results);
  }
}
