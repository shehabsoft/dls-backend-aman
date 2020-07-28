package ae.rta.dls.backend.cucumber.stepdefs;

import ae.rta.dls.backend.DlsBackendApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = DlsBackendApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
