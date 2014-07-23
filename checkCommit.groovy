import com.google.gerrit.extensions.annotations.Listen
import com.google.gerrit.server.events.CommitReceivedEvent
import com.google.gerrit.server.git.GitRepositoryManager
import com.google.gerrit.server.git.validators.CommitValidationException
import com.google.gerrit.server.git.validators.CommitValidationListener
import com.google.gerrit.server.git.validators.CommitValidationMessage

import com.google.inject.Singleton
import com.google.inject.Inject

import org.eclipse.jgit.diff.DiffFormatter
import org.eclipse.jgit.diff.RawText

@Singleton
@Listen
public class DoNotCommitHook implements CommitValidationListener {

	@Inject
	GitRepositoryManager repoManager

	def regex = /DO_?NOT_?COMMIT/

	/* This class is just to override the writeAddedLine method with
	 * the logic to detect lines having incorrect text.
	*/
	class AddedLineDetector extends DiffFormatter {
		def error

		AddedLineDetector() {
			super(new ByteArrayOutputStream())
		}

		@Override
		public void writeAddedLine(RawText text, int line) {
			super.writeAddedLine(text, line)
			def lineText = text.getString(line)
			if (lineText =~ regex) {
				error = lineText
			}
		}
	}

	@Override
  	public List<CommitValidationMessage> onCommitReceived(CommitReceivedEvent receiveEvent) throws CommitValidationException {
		def projectName = receiveEvent.project.nameKey
		def repo = repoManager.openRepository(projectName)

		def df = new AddedLineDetector()
		df.setRepository(repo)

		// Diff between this commit and the parent (git diff HEAD^..HEAD)
		def commit = receiveEvent.commit
		def parent = commit.getParent(0)

		// This starts the diff and formatting process.
		df.format(parent, commit)

		if (df.error) {
			throw new CommitValidationException("Line: '${df.error}' matches ${regex}")
		}
		return [ new CommitValidationMessage("free from ${regex}", false) ]
	}
}
