import com.google.gerrit.sshd.SshCommand
import com.google.gerrit.sshd.CommandMetaData
import com.google.gerrit.extensions.annotations.Export
import com.google.gerrit.extensions.api.GerritApi
import com.google.gerrit.extensions.api.changes.ReviewInput

import com.google.inject.Inject
import org.kohsuke.args4j.Argument

class Review extends SshCommand {
  @Inject GerritApi gApi
  @Argument(usage = "change id", metaVar = "CHANGE")
  String change
  void run(){}
}

@Export("recommend")
@CommandMetaData(name = "recommend", description = "Vote +1 on Code-Review")
class Recommend extends Review {
  void run() {
    stdout.println("Recommend change: " + change)
    gApi.changes()
        .id(change)
        .current()
        .review(ReviewInput.recommend())
  }
}

@Export("dislike")
@CommandMetaData(name = "dislike", description = "Vote -1 on Code-Review")
class Dislike extends Review {
  void run() {
    stdout.println("Dislike change: " + change)
    gApi.changes()
        .id(change)
        .current()
        .review(ReviewInput.dislike())
  }
}

@Export("approve")
@CommandMetaData(name = "approve", description = "Vote +2 on Code-Review")
class Approve extends Review {
  void run() {
    stdout.println("Approve change: " + change)
    gApi.changes()
        .id(change)
        .current()
        .review(ReviewInput.approve())
  }
}

@Export("reject")
@CommandMetaData(name = "reject", description = "Vote -2 on Code-Review")
class Reject extends Review {
  void run() {
    stdout.println("Reject change: " + change)
    gApi.changes()
        .id(change)
        .current()
        .review(ReviewInput.reject())
  }
}

commands = [ Recommend, Dislike, Approve, Reject ]
