package agh.edu.pl.tai.lineup.domain.project.entity;

import agh.edu.pl.tai.lineup.domain.valueobject.Technology;

import java.util.Set;

public class ProjectTechnologies {

    private Set<Technology> technologies;

    public ProjectTechnologies(Set<Technology> technologies) {
        this.technologies = technologies;
    }

    public Set<Technology> getTechnologies() {
        return technologies;
    }

}
