package agh.edu.pl.tai.lineup.api.responses.project;

import java.util.List;

public class ProjectHistoryResponse {

    private List<ProjectResponse> owned;
    private List<ProjectResponse> participated;

    public ProjectHistoryResponse(List<ProjectResponse> owned, List<ProjectResponse> participated) {
        this.owned = owned;
        this.participated = participated;
    }

    public List<ProjectResponse> getOwned() {
        return owned;
    }

    public List<ProjectResponse> getParticipated() {
        return participated;
    }
}
