import com.google.gerrit.sshd.SshCommand
import com.google.gerrit.sshd.CommandMetaData
import com.google.gerrit.extensions.annotations.Export
import com.google.gerrit.extensions.api.GerritApi
import com.google.gerrit.extensions.api.projects.ProjectInput

import com.google.inject.Inject
import org.kohsuke.args4j.Argument

@Export("create")
@CommandMetaData(name = "create", description = "Create project")
class CreateProject extends SshCommand {
  @Inject GerritApi gApi
  @Argument(usage = "project name", metaVar = "NAME")
  String name 
  void run() {
    ProjectInput input = new ProjectInput()
    input.name = name
    gApi.projects().create(input)
    stdout.println("Project created: " + name)
  }
}

commands = [ CreateProject ]
