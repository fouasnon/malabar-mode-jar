package com.software_ninja.malabar.project;

import org.gradle.tooling.model.idea.IdeaProject;
import org.gradle.tooling.model.idea.IdeaModule;
import org.gradle.tooling.GradleConnector;
//http://localhost:4429/pi/?pm=gradle&pmfile=c:/Users/lpmsmith/projects/malabar-mode-jar&repo=c:/Users/lpmsmith/.m2/repository


public class GradleProjectsCreator {


  public create( String repo, String pmfile){
    def connection = GradleConnector.newConnector().forProjectDirectory(new File(pmfile)).useInstallation(new File("c:/Users/lpmsmith/.gradle/wrapper/dists/gradle-2.2.1-bin/88n1whbyjvxg3s40jzz5ur27/gradle-2.2.1")).connect();
    println " /// Connected"
    def builder = connection.model(IdeaProject.class);
    println " /// Made builder"
    IdeaProject model = builder.get()
    println " /// got model"

    return model.getModules();
  }

  public resolveDependencies(  model,  _repo, scope) {
    def gradleScope = scope == 'runtime' ? 'COMPILE' : 'TEST';
    println model.getClass().getName();
    def deps0 = model.getDependencies();
    println deps0;
    def deps = deps0.grep({ it.scope == gradleScope });
    println deps;

    [ dependencies : deps.gather{it.file.toString()},

      resources: [] ,

      sources:   "test" == scope ? model.modules.contentRoots.testDirectories.gather({it.directory}) :
                                   model.modules.contentRoots.sourceDirectories.gather({it.directory}),

      elements:  "test" == scope ? model.compilerOutput.outputDir.toString() :
                                   model.compilerOutput.testOutputDir.toString() ]

  }

}
