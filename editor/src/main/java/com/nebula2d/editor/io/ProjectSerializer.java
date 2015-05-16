package com.nebula2d.editor.io;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nebula2d.editor.framework.Project;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ProjectSerializer {
    private Project project;
    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public ProjectSerializer(Project project) {
        this.project = project;
    }

    public void save() throws IOException {
        try (FullBufferedWriter fw = new FullBufferedWriter(new FileWriter(project.getPath()))) {

        }
    }

    public Project load() throws IOException {
        try (FileReader reader = new FileReader(project.getPath())){
            return gson.fromJson(reader, Project.class);
        }
    }
}
