package base.data;

import base.data.ScenarioData;

import java.util.ArrayList;
import java.util.List;

public class ScenarioGroupData {
    private final List<ScenarioData> data;

    public ScenarioGroupData() {
        this.data = new ArrayList<>();
    }

    public void addScenarioData(ScenarioData scenarioData) {
        data.add(scenarioData);
    }

    public List<ScenarioData> getData() {
        return data;
    }

    public String getValueBeingComparedText() {
        return data.get(0).getScenario().getValueBeingComparedText();
    }
}
