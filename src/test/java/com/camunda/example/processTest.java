package com.camunda.example;

import com.camunda.example.service.LoggerDelegate;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.spring.boot.starter.test.helper.AbstractProcessEngineRuleTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collection;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.runtimeService;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.withVariables;

@Deployment(resources = {"ParentProcess.bpmn", "decideOnProcessIDs.dmn", "Alpha.bpmn", "Bravo.bpmn", "Charlie.bpmn"})
public class processTest extends AbstractProcessEngineRuleTest {

  private static final String PD_KEY = "ParentProcess";

  @Test
  public void shouldExecuteHappyPath() {

    Mocks.register("logger", new LoggerDelegate());

    var pi = runtimeService().startProcessInstanceByKey(PD_KEY,
        withVariables("startParameterList", "\"A\", \"C\""));
    assertThat(pi).isEnded()
        .hasPassed("ExecuteAProcessFromTheListCallActivity");
  }
}
